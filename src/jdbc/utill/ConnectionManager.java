package S3.jdbc.utill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    private static final String url = "jdbc:mysql://localhost:3306/student_case";
    private static final String user = "root";
    private static final String password = "12345";

    public static Connection get(){
        try {
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
