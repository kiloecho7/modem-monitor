package kevin.home;

import org.apache.commons.lang.StringUtils;

public class Device {
  private final String type;
  private final String name;
  public final String ip;
  private final String mac;
  private final String connect;
  private final String dunno;

  public Device(String deviceDataString) {
    String[] parts = deviceDataString.split("/");
    this.type = parts[0];
    this.name = parts[1];
    this.ip = parts[2];
    this.mac = parts[3];
    this.connect = parts[4];
    this.dunno = parts[5];
  }

  @Override
  public String toString() {
    return StringUtils.leftPad(type, 10) +
        StringUtils.leftPad(name, 29) +
        StringUtils.leftPad(ip, 15) +
        StringUtils.leftPad(mac, 20) + ' ' +
        StringUtils.leftPad(connect, 9) + ' ' +
        StringUtils.leftPad(dunno, 3);
  }
}
