package kevin.home;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.NoRouteToHostException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class ModemLogFile {

  private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd hh:mm:ss a");
  private static boolean printedLogFilePath;
//  private static Logger staticLogger = makeStaticLogger();
  private Connection connection;
//  private Logger logger;

//  private static Logger makeStaticLogger() {
//    Logger logger = null;
//    try {
//      logger = new Logger(  "ModemLogger.log");
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    return logger;
//  }
//
  ModemLogFile() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException {
//    this.logger = new Logger(  "ModemLogger.log");
    connection = Utility.makeDatabaseConnection();
  }

  public static void main(String[] args) throws InterruptedException {
    while(true) {
      try {
        ModemLogFile modemLogFile = new ModemLogFile();
        ConnectInfo connectInfo = modemLogFile.connect();
        modemLogFile.getStuff(connectInfo);
      }
      catch(ConnectTimeoutException e) {
        System.err.println(new Date() + " Exception caught " + e.getClass().getName() + " " + e.getMessage());
//        staticLog(e);
        Thread.sleep(1000 * 10);
      }
      catch(NoRouteToHostException e) {
        System.err.println(new Date() + " Exception caught " + e.getClass().getName() + " " + e.getMessage());
//        staticLog(e);
        Thread.sleep(1000 * 60);
      }
      catch( Exception e) {
        System.err.println(new Date() + " Exception caught " + e.getClass().getName() + " " + e.getMessage());
//        staticLog(e);
        Thread.sleep(1000 * 10);
      }
    }
  }

  private ConnectInfo connect() throws IOException, URISyntaxException {
    BasicCookieStore cookieStore = new BasicCookieStore();
    ConnectInfo connectInfo = new ConnectInfo();
    RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000).build();
    HttpClientBuilder httpClientBuilder = HttpClients.custom()
            .setDefaultCookieStore(cookieStore)
            .setConnectionManagerShared(true)
            .setDefaultRequestConfig(requestConfig);
    try ( CloseableHttpClient httpclient = httpClientBuilder.build() ) {
      connectInfo.httpclient = httpclient;
      HttpGet httpget = new HttpGet("http://192.168.0.1/");
      initializeConnectionResources(httpclient, httpget);
      String loginResponseBody = logIn(httpclient);
      String indexPageSource = goToIndexPage(httpclient, loginResponseBody);
      connectInfo.indexPageSource = indexPageSource;
    }
    return connectInfo;
  }

  private void getStuff(ConnectInfo connectInfo) throws Exception {
      Set<WebLogEntry> allEntries = new TreeSet<>();
      Set<WebLogEntry> allEntriesFiltered = new TreeSet<>();
      Date previousQueryTime = new Date(0);
      while(true) {
        List<WebLogEntry> currentEntries = getLogData( connectInfo.httpclient, connectInfo.indexPageSource, previousQueryTime);
        Date maxDatetime = getMaxDatetime(currentEntries);
        writeToDb(currentEntries);
        allEntries.addAll(currentEntries);
        previousQueryTime = maxDatetime;
        long sleepSeconds = 8;
        Thread.sleep(sleepSeconds * 1000);
      }
  }

  private Date getMaxDatetime(List<WebLogEntry> entries) {
    Date maxDate = new Date(0);
    for(WebLogEntry webLogEntry : entries) {
      if (webLogEntry.getDate().after(maxDate)) {
        maxDate = webLogEntry.getDate();
      }
    }
    return maxDate;
  }

  private void writeToDb(List<WebLogEntry> currentEntriesNoDups) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
    if (connection == null) {
      System.err.println("There's no database connection. I can't write to the database.");
      return;
    }
    Statement statement = connection.createStatement();
    int count = 0;
    for(WebLogEntry entry : currentEntriesNoDups) {
      if (entry.isRecorded()) {
        //already recorded on a previous iteration
      }
      try {
        ResultSet resultSet = statement.executeQuery(entry.asSqlSelect());
        if (resultSet.next() && resultSet.getInt(1) == 1 ) {
          //It got recorded on a previous run of the app
          entry.setRecorded(true);
          continue;
        }
        statement.executeUpdate(entry.asSqlInsert());
        count++;
        entry.setRecorded(true);
      }
      catch (Exception e) {
        if (e.getMessage() != null && e.getMessage().startsWith("ERROR: duplicate key value violates unique constraint \"blah_blah_some_constraint\"")) {
          //ignore
          entry.setRecorded(true);
        }
        else {
          System.err.println("Insert: " + entry.asSqlInsert());
          e.printStackTrace();
        }
      }
    }
    System.out.println(sdf.format(new Date()) + " count = " + count);
    statement.close();
  }

  private String goToIndexPage(CloseableHttpClient httpclient, String loginResponseBody) throws IOException {
    HttpGet httpget;
    String indexPageSource = null;
    if (loginResponseBody != null && loginResponseBody.contains("top.location='/index.html';")) {
      httpget = new HttpGet("http://192.168.0.1/index.html");
      try (CloseableHttpResponse response3 = httpclient.execute(httpget)) {
        HttpEntity entity = response3.getEntity();
        indexPageSource = EntityUtils.toString(entity);
      }
    }
    return indexPageSource;
  }

  private String logIn(CloseableHttpClient httpclient) throws URISyntaxException, IOException {
    String loginResponseBody = null;
    HttpUriRequest login = RequestBuilder.post()
        .setUri(new URI("http://192.168.0.1/login.cgi"))  // ----------------------- This was https -- so it'll be easy to do that too
        .addParameter("admin_username", "admin")
        .addParameter("admin_password", "48fAfac4")
        .build();
    try (CloseableHttpResponse response2 = httpclient.execute(login)) {
      HttpEntity entity = response2.getEntity();
      loginResponseBody = EntityUtils.toString(entity);
    }
    return loginResponseBody;
  }

  private void initializeConnectionResources(CloseableHttpClient httpclient, HttpGet httpget) throws IOException {
    try (CloseableHttpResponse response1 = httpclient.execute(httpget)) {
      HttpEntity entity = response1.getEntity();
      EntityUtils.consume(entity);
    }
  }

  
  
  private Map<String, Device> getDeviceList(CloseableHttpClient httpclient) throws IOException {
    HttpGet httpget;
    String htmlSource = null;
    httpget = new HttpGet("http://192.168.0.1/modemstatus_landevicelist.html");
    try (CloseableHttpResponse response = httpclient.execute(httpget)) {
      HttpEntity entity = response.getEntity();
      htmlSource = EntityUtils.toString(entity);
    }
    String startPhrase = "var activeusers = '";
    int start = htmlSource.indexOf(startPhrase) + startPhrase.length();
    int end = htmlSource.indexOf("';", start);
    String deviceListing = htmlSource.substring(start, end);
    String[] devicesText = deviceListing.split("\\|");
    Map<String, Device> devices = new HashMap<>();
    for(String deviceDataString : devicesText) {
      Device device = new Device(deviceDataString);
      devices.put(device.ip, device);
    }
    return devices;
  }
  
  private List<WebLogEntry> getLogData(CloseableHttpClient httpclient, String indexPageSource, Date previousQueryTime) throws IOException {
    HttpGet httpget;
    String webLogPageSource = null;
    List<WebLogEntry> sortedWebLogEntries = new ArrayList<>();
    webLogPageSource = getWebPage( httpclient, indexPageSource, webLogPageSource );
    List<WebLogEntry> webLogEntries = parseLogSource(webLogPageSource);
//    System.out.println("webLogEntries.size() = " + webLogEntries.size());
    List<WebLogEntry> filteredEntries = filterNewEntries(webLogEntries, previousQueryTime);
//    System.out.println("filteredEntries.size() = " + filteredEntries.size());
    sortedWebLogEntries = makeSortedInstance(filteredEntries);
    return sortedWebLogEntries;
  }

  private List<WebLogEntry> filterNewEntries(List<WebLogEntry> webLogEntries, Date previousQueryTime) {
    List<WebLogEntry> filteredEntries = new ArrayList<>();
    for(WebLogEntry webLogEntry : webLogEntries) {
      if (webLogEntry.getDate().after(previousQueryTime)) {
        filteredEntries.add(webLogEntry);
      }
    }
    return filteredEntries;
  }

  private String getWebPage( CloseableHttpClient httpclient, String indexPageSource, String webLogPageSource ) throws IOException {
    HttpGet httpget;
    if (indexPageSource != null) {
      httpget = new HttpGet("http://192.168.0.1//utilities_webactivitylog.html");
      try (CloseableHttpResponse response = httpclient.execute(httpget)) {
        HttpEntity entity = response.getEntity();
        webLogPageSource = EntityUtils.toString(entity);
      }
    }
    return webLogPageSource;
  }

  private List<WebLogEntry> makeSortedInstance(List<WebLogEntry> webLogEntries) {
    List<WebLogEntry> sortedWebLogEntries = new ArrayList<>();
    sortedWebLogEntries.addAll(webLogEntries);
    Collections.sort(sortedWebLogEntries);
    return sortedWebLogEntries;
  }

  private List<WebLogEntry> parseLogSource(String webLogPageSource) {
    List<WebLogEntry> webLogEntries = new ArrayList<>();
    String startPhrase = "<tbody id='webLogEntry'>";
    int start = webLogPageSource.indexOf(startPhrase) + startPhrase.length();
    int end = webLogPageSource.indexOf("</tbody>", start);
    String tableSubSection = webLogPageSource.substring(start, end);
    String[] rows = tableSubSection.split("</tr>");
    for(String row : rows) {
      WebLogEntry entry = convertHtmlRowToWebLogEntry(row);
      if (entry != null ) {
        webLogEntries.add(entry);
      }
    }
    
    return webLogEntries;
  }

  private WebLogEntry convertHtmlRowToWebLogEntry(String row) {
    if (!row.contains("<tr ")) {
      return null;
    }
    row = row.replaceAll("<tr align=\"center\">", "");
    String[] elements = row.split("</td>");
    WebLogEntry webLogEntry = new WebLogEntry();
    webLogEntry.setDateString(elements[0].replaceAll("\n *\n *<td>", ""));
    webLogEntry.setTimeString(elements[1].replaceAll("\n *<td>", "").replaceFirst("&nbsp;", " "));
    webLogEntry.ip = elements[2].replaceAll("\n *<td>", "");
    webLogEntry.url = elements[3].replaceAll("\n *<td style='word-wrap:break-word; word-break:break-all;'>", "");
    return webLogEntry;
  }

//  private void log(Throwable th) {
//    this.logger.log(th);
//  }

//  private static void staticLog(Throwable th) {
//    staticLogger.log(th);
//  }

//  private void log(List<WebLogEntry> currentEntries) {
//    this.logger.log(toString(currentEntries));
//  }
  
//  private void log(Set<WebLogEntry> currentEntries) {
//    this.logger.log(toString(currentEntries));
//  }

  private String toString(Collection<WebLogEntry> entries) {
    String s = "";
    for(WebLogEntry entry : entries) {
      s += entry + "\n";
    }
    return s;
  }



}


