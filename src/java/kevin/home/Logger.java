package kevin.home;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Logger {

  private File file;
  FileWriter fw;

  public Logger(String fileName) throws IOException {
    this.file = new File(fileName);
    this.fw = new FileWriter(this.file, true);
  }
  
  public void log(Throwable th) {
    log(throwableToString(th));
  }
  
  public void log(String entry) {
    try {
      fw.write(entry);
      fw.flush();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static StackTraceElement[] filterElements(StackTraceElement[] elements, String filter) {
    List<StackTraceElement> filtered = new ArrayList<>();
    for(int i = 0; i < elements.length; i++) {
      StackTraceElement element = elements[i];
      if (element.getClassName().contains("kevin.home.ModemLogFile")) {
        filtered.add(element);
      }
    }
    StackTraceElement[] arrayType = new StackTraceElement[0];
    return filtered.toArray(arrayType);
  }

  public static String throwableToString( Throwable th )
  {
    StackTraceElement[] stackTrace = th.getStackTrace();
    StringBuilder sb = new StringBuilder( );
    for ( StackTraceElement stackTraceElement : stackTrace ) {
      if (sb.length() > 0) {
        sb.append( "\n" );
      }
      sb.append( stackTraceElement.getClassName() ).append( " " );
      sb.append( stackTraceElement.getFileName() ).append( " " );
      sb.append( stackTraceElement.getMethodName() ).append( " " );
      sb.append( stackTraceElement.getLineNumber() ).append( " " );
      sb.append( stackTraceElement.toString() ).append( " " );
    }
    return sb.toString();
  }  
  
}
