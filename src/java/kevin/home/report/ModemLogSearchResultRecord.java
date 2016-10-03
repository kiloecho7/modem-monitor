package kevin.home.report;

import kevin.home.Constants;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

class ModemLogSearchResultRecord {

  private String ip;
  private String ipOwner;
  private int id;
  private String url;
  private String appName;
  private String requestTime;

  void setId(int id) {
    this.id = id;
  }

  void setIp(String ip) {
    this.ip = ip;
    this.calcIpOwner();
  }

  private void calcIpOwner() {
    if (Constants.EMILYS_IP.equals(ip)) {
      this.ipOwner = "Emily";
    }
    else {
      this.ipOwner = "Unknown";
    }
  }


  public void setUrl(String url) {
    if (url == null) {
      throw new IllegalArgumentException("url cannot be null.");
    }
    this.url = url;
    this.setAppName();
  }

  private void setAppName() {
    if (this.url == null) {
      throw new IllegalStateException("The url cannot be null. Was the url set?");
    }
    Pattern p = Pattern.compile(".+?\\.fbcdn\\.net");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.FACEBOOK_APP_NAME;
      return;
    }
    p = Pattern.compile(".*facebook\\.com");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.FACEBOOK_APP_NAME;
      return;
    }
    
    
    p = Pattern.compile(".*fastlylb.net");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.FASTLY_APP_NAME;
      return;
    }


    p = Pattern.compile(".*afterschool.*");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.AFTERSCHOOL_APP_NAME;
      return;
    }

    
    p = Pattern.compile(".*googlevideo.com");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.YOUTUBE_APP_NAME;
      return;
    }
    p = Pattern.compile(".*video-stats.l.google.com");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.YOUTUBE_APP_NAME;
      return;
    }
    p = Pattern.compile("ytimg.l.google.com");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.YOUTUBE_APP_NAME;
      return;
    }
    p = Pattern.compile(".+\\.ytimg\\.com");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.YOUTUBE_APP_NAME;
      return;
    }
    p = Pattern.compile("youtube-ui.l.google.com");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.YOUTUBE_APP_NAME;
      return;
    }
    p = Pattern.compile(".+\\.youtube\\.com");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.YOUTUBE_APP_NAME;
      return;
    }


    p = Pattern.compile(".*twitter.*");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.TWITTER_APP_NAME;
      return;
    }
    p = Pattern.compile(".+\\.twimg\\.com");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.TWITTER_APP_NAME;
      return;
    }
    

    p = Pattern.compile(".*snapchat.*");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.SNAPCHAT_APP_NAME;
      return;
    }

    
    p = Pattern.compile(".+\\.vine\\.co");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.VINE_APP_NAME;
      return;
    }
    p = Pattern.compile(".+\\.vineapp\\.com");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.VINE_APP_NAME;
      return;
    }
    
    
    p = Pattern.compile(".*icloud.apple-dns.net");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.ICLOUD_APP_NAME;
      return;
    }
    p = Pattern.compile(".+?-keyvalueservice-.+?apple-dns\\.net");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.ICLOUD_APP_NAME;
      return;
    }
    p = Pattern.compile(".*.blobstore.+apple.com.*");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.APPLE_STORE_APP_NAME;
      return;
    }
    p = Pattern.compile(".+\\.aaplimg\\.com");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.APPLE_APP_NAME;
      return;
    }
    p = Pattern.compile(".*apple.com");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.APPLE_APP_NAME;
      return;
    }

    
    p = Pattern.compile(".*\\.apple\\.com\\.edgesuite\\.net");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.APPLE_SOMETHING_APP_NAME;
      return;
    }
    p = Pattern.compile("pancake.apple.com.edgekey.net");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.APPLE_SOMETHING_APP_NAME;
      return;
    }


    p = Pattern.compile(".*guzzoni.*");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.GUZZONI_APP_NAME;
      return;
    }

    
    p = Pattern.compile(".+?\\.(e9|a|dscc|g)\\.akamaiedge\\.net");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.AKAMAI_APP_NAME;
      return;
    }
    p = Pattern.compile(".+?\\.(b|da1)\\.akamai\\.net");
    if (p.matcher(this.url).matches()) {
      this.appName = Constants.AKAMAI_APP_NAME;
      return;
    }


//    p = Pattern.compile("");
//    if (p.matcher(this.url).matches()) {
//      this.appName = Constants.;
//    "apple icloud", "%-content-%apple-dns.net'                             
        //https://gist.github.com/elFua/09cdc5e60b0014e32d6b4c6f23142ea9  --  a ton of urls to block all OSX icloud traffic 

//      p = Pattern.compile("");
//      if (p.matcher(this.url).matches()) {
//        this.appName = Constants.;
//    "apple not sure yet" "store-009-failover2.blobstore-apple.com.akadns.net'
//    "apple not sure yet" "store-009-lb.blobstore-apple.com.akadns.net'       
//    "apple not sure yet" "store-009.blobstore-apple.com.akadns.net'          

    //blobstore is mentioned on the internet but it's not on the https://gist.github.com/elFua/09cdc5e60b0014e32d6b4c6f23142ea9 list 

    //interesting. it's always store-009. is that something specific like itunes or a geographic region?


  }
  
  String toStringLong() {
    return 
        "id: "                + this.getId() + 
        "     ip_owner: "     + this.getIpOwner() + 
        "     ip: "           + this.getIp() + 
        "     request_time: " + this.getRequestTime() + 
        "     url: "          + this.getUrl() + 
        "     app name: "     + this.getAppName();
  }

  String toStringShort() {
    String paddedAppName = this.getAppName();
    if (paddedAppName == null) {
      paddedAppName = "---null---";
    }
    paddedAppName = StringUtils.rightPad(paddedAppName, 15, ' ');
    
    return
             "time: "         + this.getRequestTime() + 
        "     app name: "     + paddedAppName + 
        "     url: "          + this.getUrl();
  }

  int getId() {
    return id;
  }

  public String getIp() {
    return ip;
  }

  public String getIpOwner() {
    return ipOwner;
  }

  public String getUrl() {
    return url;
  }

  public String getAppName() {
    return appName;
  }

  public String getRequestTime() {
    return requestTime;
  }

  public void setRequestTime(String requestTime) {
    this.requestTime = requestTime;
  }
}
