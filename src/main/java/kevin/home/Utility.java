package kevin.home;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utility {

  public static Connection makeDatabaseConnection() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
    Class.forName("org.postgresql.Driver").newInstance();
    try {
      String host = "localhost";
      int port = 5432;
      String database = "kevin.edwards";
      return DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + database);
    }
    finally {
      int i = 1;
    }
    
  }

}
