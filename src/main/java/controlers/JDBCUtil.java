package controlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    private static final String URL = "jdbc:postgresql://ec2-54-247-117-145.eu-west-1.compute.amazonaws.com:5432/ddbvlt6treu35e?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
    private static final String USER = "sapfwipdxbakhf";
    private static final String PASSWORD = "5ee6ce59fcd1ea10672183dd1c2f9a5c268380a26f637cb25a6e482198febba6";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
