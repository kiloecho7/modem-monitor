package kevin.home;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebLogEntry implements Comparable<WebLogEntry> {

  private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

  private String dateString;
  private String timeString;
  String ip;
  String url;
  private Date date;
  private boolean recorded;

  public WebLogEntry(String dateString, String timeString, String ip, String url) {
    this.setDate(dateString, timeString);
    this.ip = ip;
    this.url = url;
  }

  public WebLogEntry() {
    int i = 1;
  }

  private void setDate() {
    this.setDate(this.dateString, this.timeString);
  }
  
  private void setDate(String dateString, String timeString) {
    try {
      this.date = sdf.parse(dateString +" "+ timeString);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public void setDateString(String dateString) {
    this.dateString = dateString;
    if (this.timeString != null) {
      setDate();
    }
  }

  public void setTimeString(String timeString) {
    this.timeString = timeString;
    if (this.dateString != null) {
      setDate();
    }
  }
  
  public Date getDate() {
    return date;
  }

  public String toString() {
    return sdf.format(date) + "\t" + ip + "\t" + url;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof WebLogEntry)) return false;

    WebLogEntry that = (WebLogEntry) o;

    if (!date.equals(that.date)) return false;
    if (!ip.equals(that.ip)) return false;
    if (!url.equals(that.url)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = date.hashCode();
    result = 31 * result + ip.hashCode();
    result = 31 * result + url.hashCode();
    return result;
  }

  @Override
  public int compareTo(WebLogEntry that) {
    if (that == null) {
      throw new IllegalArgumentException();
    }
    if (!this.ip.equals(that.ip)) {
      return this.ip.compareTo(that.ip);
    }
    if (!this.date.equals(that.date)) {
      return this.date.compareTo(that.date);
    }
    return this.url.compareTo(that.url);
  }
  
  public String asSqlSelect() {
    return "select count(0) from public.modem_log where request_time = '" + sdf.format(date) + "' and ip = '" + ip + "' and url = '" + url + "'";
  }

  public String asSqlInsert() {
    return "insert into public.modem_log (request_time,ip,url) values ('" + sdf.format(date) + "', '" + ip + "', '" + url + "')";
  }

  public void setRecorded(boolean recorded) {
    this.recorded = recorded;
  }

  public boolean isRecorded() {
    return recorded;
  }
}
