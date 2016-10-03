package kevin.home;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utility {

  public static Connection makeDatabaseConnection() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
    Class.forName("org.postgresql.Driver").newInstance();
    return DriverManager.getConnection("jdbc:postgresql:host-name", "user", "password");
  }

}
