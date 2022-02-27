package S3.jdbc.utill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
          try(Connection connection = ConnectionManager.get()){
              System.out.println(connection.getTransactionIsolation());
          }
    }
}
