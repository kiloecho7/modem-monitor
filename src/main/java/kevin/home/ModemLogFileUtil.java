package kevin.home;

import java.util.ArrayList;
import java.util.List;

public class ModemLogFileUtil {
  public static List<WebLogEntry> getNewEntries( List<WebLogEntry> previousEntries, List<WebLogEntry> nextEntries ) {
    List<WebLogEntry> newEntries = new ArrayList<>();
    for( WebLogEntry nextEntry : nextEntries ) {
      if ( !previousEntries.contains( nextEntry ) ) {
        newEntries.add( nextEntry );
      }
    }
    return newEntries;
  }
}
