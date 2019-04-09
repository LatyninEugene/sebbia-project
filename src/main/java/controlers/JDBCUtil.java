package controlers;


import model.MyConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class JDBCUtil {
    private static final String URL = "jdbc:postgresql://ec2-54-247-117-145.eu-west-1.compute.amazonaws.com:5432/ddbvlt6treu35e?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
    private static final String USER = "sapfwipdxbakhf";
    private static final String PASSWORD = "5ee6ce59fcd1ea10672183dd1c2f9a5c268380a26f637cb25a6e482198febba6";

    private static final BlockingQueue<Connection> connections = new ArrayBlockingQueue<Connection>(18);

    static {
        try {
            System.out.println(connections.size());
            Class.forName("org.postgresql.Driver");
            for (int i = 0; i < 18; i++) {
                connections.add(new MyConnection(DriverManager.getConnection(URL,USER,PASSWORD)));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public synchronized static Connection getConnection() throws ClassNotFoundException, SQLException {
        try {
            Connection connection = connections.poll(2,TimeUnit.SECONDS);
            return connection;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void putConnection(Connection con){
        connections.add(con);
    }



}
