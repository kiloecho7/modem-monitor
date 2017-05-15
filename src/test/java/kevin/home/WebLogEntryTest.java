package kevin.home;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class WebLogEntryTest {

  WebLogEntry entry0  = new WebLogEntry("10/24/2015", "10:00:00 AM", "192.168.0.1",  "findjar.com");
  WebLogEntry entry1  = new WebLogEntry("10/21/2015", "10:00:00 AM", "192.168.0.15", "findjar.com");
  WebLogEntry entry2  = new WebLogEntry("10/22/2015",  "9:00:00 AM", "192.168.0.15", "findjar.com");
  WebLogEntry entry3  = new WebLogEntry("10/22/2015", "10:00:00 AM", "192.168.0.15", "findjar.com");
  WebLogEntry entry4  = new WebLogEntry("10/23/2015",  "8:00:00 AM", "192.168.0.15", "findjar.com");
  WebLogEntry entry5  = new WebLogEntry("10/21/2015", "10:00:00 AM", "192.168.0.20", "findjar.com");
  
  @Test
  public void dateTimeOrdering() {
    Set<WebLogEntry> set = new TreeSet<>();
    //Scramble them during insert so they're out of order.
    set.add(entry2);
    set.add(entry1);
    set.add(entry5);
    set.add(entry0);
    set.add(entry4);
    set.add(entry3);
    
    List<WebLogEntry> asList = new ArrayList<>();
    asList.addAll(set);
    assertEquals("Item 0 has the lowest ip. The ip is the first field evaluated in the sorting.", entry0, asList.get(0));
    assertEquals("Item 1 is next. Of the x.x.x.15 ip's it has the earliest date.", entry1, asList.get(1));
    assertEquals("Item 2 is next. Its hour is before item 1's but its date is after.", entry2, asList.get(2));
    assertEquals("Item 3 is next. It's one hour after item 2.", entry3, asList.get(3));
    assertEquals("Item 4 is next. Of the x.x.x.15 ip's it has the latest date -- the hour doesn't matter.", entry4, asList.get(4));
    assertEquals("Item 5 is last. It has the highest ip.", entry5, asList.get(5));
  }   
  
  String input = "10/22/2015\t9:38:36 PM\t192.168.0.15\tfindjar.com\n" +
      "10/22/2015\t9:38:35 PM\t192.168.0.7\tdsx.weather.com\n" +
      "10/22/2015\t9:38:21 PM\t192.168.0.18\tnrdp.nccp.netflix.com\n" +
      "10/22/2015\t9:38:21 PM\t192.168.0.18\tnrdp.nccp.netflix.com\n" +
      "10/22/2015\t9:38:08 PM\t192.168.0.12\timg.ifcdn.com\n" +
      "10/22/2015\t9:38:02 PM\t192.168.0.12\timg.ifcdn.com\n" +
      "10/22/2015\t9:37:58 PM\t192.168.0.15\tssl.gstatic.com\n" +
      "10/22/2015\t9:37:56 PM\t192.168.0.12\timg.ifcdn.com\n" +
      "10/22/2015\t9:37:48 PM\t192.168.0.7\tgoogleads.g.doubleclick.net\n" +
      "10/22/2015\t9:37:46 PM\t192.168.0.12\timg.ifcdn.com\n" +
      "10/22/2015\t9:37:41 PM\t192.168.0.7\toutlook.office365.com\n" +
      "10/22/2015\t9:37:30 PM\t192.168.0.4\te4805.a.akamaiedge.net\n" +
      "10/22/2015\t9:37:30 PM\t192.168.0.4\tiadsdk.apple.com\n" +
      "10/22/2015\t9:37:30 PM\t192.168.0.4\tiadsdk.apple.com\n" +
      "10/22/2015\t9:37:30 PM\t192.168.0.15\tsettings-win.data.microsoft.com\n" +
      "10/22/2015\t9:37:29 PM\t192.168.0.4\tpartiality.itunes-apple.com.akadns.net\n" +
      "10/22/2015\t9:37:29 PM\t192.168.0.4\tmzuserxp.itunes-apple.com.akadns.net\n" +
      "10/22/2015\t9:37:29 PM\t192.168.0.4\tpartiality.itunes.apple.com\n" +
      "10/22/2015\t9:37:29 PM\t192.168.0.4\tpartiality.itunes.apple.com\n" +
      "10/22/2015\t9:37:29 PM\t192.168.0.4\txp.apple.com\n" +
      "10/22/2015\t9:37:29 PM\t192.168.0.4\txp.apple.com\n" +
      "10/22/2015\t9:37:28 PM\t192.168.0.4\te673.e9.akamaiedge.net\n";

  String expectedOutput = "10/22/2015\t9:37:46 PM\t192.168.0.12\timg.ifcdn.com\n" +
      "10/22/2015\t9:37:56 PM\t192.168.0.12\timg.ifcdn.com\n" +
      "10/22/2015\t9:38:02 PM\t192.168.0.12\timg.ifcdn.com\n" +
      "10/22/2015\t9:38:08 PM\t192.168.0.12\timg.ifcdn.com\n" +
      "10/22/2015\t9:37:30 PM\t192.168.0.15\tsettings-win.data.microsoft.com\n" +
      "10/22/2015\t9:37:58 PM\t192.168.0.15\tssl.gstatic.com\n" +
      "10/22/2015\t9:38:36 PM\t192.168.0.15\tfindjar.com\n" +
      "10/22/2015\t9:38:21 PM\t192.168.0.18\tnrdp.nccp.netflix.com\n" +
      "10/22/2015\t9:38:21 PM\t192.168.0.18\tnrdp.nccp.netflix.com\n" +
      "10/22/2015\t9:37:28 PM\t192.168.0.4\te673.e9.akamaiedge.net\n" +
      "10/22/2015\t9:37:29 PM\t192.168.0.4\tmzuserxp.itunes-apple.com.akadns.net\n" +
      "10/22/2015\t9:37:29 PM\t192.168.0.4\tpartiality.itunes.apple.com\n" +
      "10/22/2015\t9:37:29 PM\t192.168.0.4\tpartiality.itunes.apple.com\n" +
      "10/22/2015\t9:37:29 PM\t192.168.0.4\tpartiality.itunes-apple.com.akadns.net\n" +
      "10/22/2015\t9:37:29 PM\t192.168.0.4\txp.apple.com\n" +
      "10/22/2015\t9:37:29 PM\t192.168.0.4\txp.apple.com\n" +
      "10/22/2015\t9:37:30 PM\t192.168.0.4\te4805.a.akamaiedge.net\n" +
      "10/22/2015\t9:37:30 PM\t192.168.0.4\tiadsdk.apple.com\n" +
      "10/22/2015\t9:37:30 PM\t192.168.0.4\tiadsdk.apple.com\n" +
      "10/22/2015\t9:37:41 PM\t192.168.0.7\toutlook.office365.com\n" +
      "10/22/2015\t9:37:48 PM\t192.168.0.7\tgoogleads.g.doubleclick.net\n" +
      "10/22/2015\t9:38:35 PM\t192.168.0.7\tdsx.weather.com\n";
  
}