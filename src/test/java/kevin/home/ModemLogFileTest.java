package kevin.home;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import kevin.home.ModemLogFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ModemLogFileTest {

  @Test
  public void newEntries() throws Exception {
    ModemLogFile modemLogFile = new ModemLogFile();
    ConnectInfo connectInfo = new ConnectInfo();
    CloseableHttpClient httpclient = new DefaultHttpClient();
    String indexPageSource = "";
    List<String> ips = new ArrayList<>();
    List<WebLogEntry> entries = new ArrayList<>();
//    WebLogEntry webLogEntry = new WebLogEntry( "01/02/2003", "11:59:58 pm", "1.1.1.1", "a.b.com" );
//    entries.add( webLogEntry );
//    WebLogEntry webLogEntry2 = new WebLogEntry( "01/02/2003", "11:59:58 pm", "1.1.1.1", "a.b.com2" );
//    entries.add( webLogEntry2 );
//    
    List<WebLogEntry> nextEntries = new ArrayList<>();
//    nextEntries.add( webLogEntry );
//    nextEntries.add( webLogEntry2 );
//    WebLogEntry webLogEntry3 = new WebLogEntry( "01/02/2003", "11:59:58 pm", "1.1.1.1", "a.b.com3" );
//    nextEntries.add( webLogEntry3 );
//    WebLogEntry webLogEntry4 = new WebLogEntry( "01/02/2003", "11:59:59 pm", "1.1.1.1", "a.b.com" );
//    nextEntries.add( webLogEntry4 );
//
//
//    List<WebLogEntry> newEntries = ModemLogFileUtil.getNewEntries( entries, nextEntries );
//    
//    assertEquals( newEntries.size(), 2 );
//    assertTrue( newEntries.contains( webLogEntry3 )  );
//    assertTrue( newEntries.contains( webLogEntry4 )  );
//    
  }
}