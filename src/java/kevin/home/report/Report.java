package kevin.home.report;

import kevin.home.Constants;
import kevin.home.Utility;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class Report {
  
  private static final Set<String> commonOvernightUrls = new HashSet<>();
  static {
    initCommonOvernightUrls();
  }

  private static void initCommonOvernightUrls() {

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    others that might be common background host names but had 3 or less hits during the night. there might be host names that are alreayd in commonOvernightUrls that would match with regex
//    "mesu.apple.com";1
//    "gsp-ssl.ls.apple.com";1
//    "gsp10-ssl.ls-apple.com.akadns.net";1
//    "p47-content-current.edge.icloud.apple-dns.net";1
//    "www.apple.com.edgekey.net.globalredir.akadns.net";1
//    "init.ess.apple.com";1
//    "configuration.apple.com.edgekey.net";1
//    "iphonesubmissions.apple.com";1
//    "gcs-us-00002.content-storage-upload.googleapis.com";1
//    "cl5.apple.com";1
//    "su.itunes.apple.com";1
//    "init.itunes.apple.com";1
//    "p44-buy.itunes.apple.com";1
//    "init-p01md.apple.com.edgesuite.net";1
//    "xp.apple.com";1
//    "gsp10-ssl.ls.apple.com";1
//    "iosapps.itunes.g.aaplimg.com";2
//    "p04-ckdatabase-current.edge.icloud.apple-dns.net";2
//    "googleapis.l.google.com";3
        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    commonOvernightUrls.add("edge-mqtt.facebook.com");
    commonOvernightUrls.add("graph.facebook.com");
    commonOvernightUrls.add("www.icloud.com");
    commonOvernightUrls.add("cl3.apple.com");
    commonOvernightUrls.add("time-ios.apple.com");
    commonOvernightUrls.add("guzzoni.apple.com");
    commonOvernightUrls.add("apple.com");
    commonOvernightUrls.add("safebrowsing.googleapis.com");
    commonOvernightUrls.add("time-ios.g.aaplimg.com");
    commonOvernightUrls.add("init-p01st.push.apple.com");
    commonOvernightUrls.add("www.apple.com");
    commonOvernightUrls.add("star.c10r.facebook.com");
    commonOvernightUrls.add("e4478.a.akamaiedge.net");
    commonOvernightUrls.add("mqtt.c10r.facebook.com");
    commonOvernightUrls.add("origin.guzzoni-apple.com.akadns.net");
    commonOvernightUrls.add("www-cdn.icloud.com.akadns.net");
    commonOvernightUrls.add("e6858.dscc.akamaiedge.net");
    commonOvernightUrls.add("e2842.e9.akamaiedge.net");
  }
  
  private Map<String, SearchResult> findUrlsThatShowUpWith(Connection conn, String ip, String rootUrl) throws SQLException, ClassNotFoundException {
    Map<String, SearchResult> results = new HashMap<>();
    this.getInstancesOfUrlRequestWithinLast30Days(conn, ip, rootUrl);
    return results;
  }

  private List<ModemLogSearchResultRecord> getInstancesOfUrlRequestWithinLast30Days(Connection conn, String ip, String url) throws SQLException, ClassNotFoundException {
    Date _30DaysAgo = calc30DaysAgo();
    String query =
      "SELECT * FROM public.modem_log\n" +
      "WHERE request_time > '" + Constants.POSTGRESS_DATE_FORMAT.format(_30DaysAgo) + "'\n" +
          "   and ip = '" + ip + "'\n" +
          "   and url = '" + url + "'\n" +
      "ORDER BY id";
    List<ModemLogSearchResultRecord> record = this.runQuery(query, conn);
    return record;
  }

  private Date calc30DaysAgo() {
    Calendar cal = new GregorianCalendar();
    cal.add(Calendar.DATE, -30);
    return cal.getTime();
  }

  private static Date calc30MinutesAgo() {
    Calendar cal = new GregorianCalendar();
    cal.add(Calendar.MINUTE, -30);
    return cal.getTime();
  }

  private static Date calc2HoursAgo() {
    Calendar cal = new GregorianCalendar();
    cal.add(Calendar.HOUR_OF_DAY, -2);
    return cal.getTime();
  }

  private List<ModemLogSearchResultRecord> runQuery(String query, Connection connection) throws ClassNotFoundException, SQLException {
    Statement statement = connection.createStatement();
    List<ModemLogSearchResultRecord> records = new ArrayList<>();
    try {
      ResultSet resultSet = statement.executeQuery(query);
      while (resultSet.next()) {
        ModemLogSearchResultRecord record = getFieldsOfCurrentRow(resultSet);
        records.add(record);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    statement.close();
    return records;
  }


  private void getSomething(Date start, Date end, String ipFilter, Connection connection) throws ClassNotFoundException, SQLException {
    String query = buildQuery(start, end, ipFilter);
    try {
      List<ModemLogSearchResultRecord> records = runQuery(query, connection);
      for(ModemLogSearchResultRecord record : records) {
        if (isCommonOvernightUrl(record.getUrl())) {
          continue;
        }
        System.out.println(record.toStringShort());
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private boolean isCommonOvernightUrl(String url) {
    return commonOvernightUrls.contains(url);
  }

  private String calcIpOwner(String ip) {
    return "not imp";
  }

  private String buildQuery(Date start, Date end, String ip) {
    String query = 
        "SELECT * FROM public.modem_log\n" +
        "WHERE request_time between '" + Constants.POSTGRESS_DATE_FORMAT.format(start) + "' and '" + Constants.POSTGRESS_DATE_FORMAT.format(end) + "'\n" +
        "   and ip = '" + ip + "'\n" +
        "ORDER BY id";
    return query;
  }

  private ModemLogSearchResultRecord getFieldsOfCurrentRow(ResultSet resultSet) throws SQLException {
    ModemLogSearchResultRecord record = new ModemLogSearchResultRecord();
    record.setId(resultSet.getInt("id"));
    record.setIp(resultSet.getString("ip"));
    record.setRequestTime(resultSet.getString("request_time"));
    record.setUrl(resultSet.getString("url"));
    return record;
  }

  private static void main__first_JustDoSomething_test() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
    Report report = new Report();
    Date start = calc2HoursAgo();
    Date end   = new Date();
    Connection conn = Utility.makeDatabaseConnection();
    report.getSomething(start, end, "192.168.0.16", conn);
    conn.close();
  }

  private static Date makeDate(int year, int month, int date, int hour, int min, int sec) {
    return new Date(year - 1900, month - 1, date, hour, min, sec);
  }

  public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
    main__first_JustDoSomething_test();
//    main_findUrlsThatShowUpWith();
  }

  private static void main_findUrlsThatShowUpWith() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
    Report report = new Report();
    Connection conn = Utility.makeDatabaseConnection();
    String url = "e11100.g.akamaiedge.net";
    //String url = "e2842.e9.akamaiedge.net";
    report.findUrlsThatShowUpWith(conn, Constants.EMILYS_IP, url);
    conn.close();
  }

}
