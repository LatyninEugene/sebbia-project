package controlers;


import model.MyConnection;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class JDBCUtil {
    private static String URL = "jdbc:postgresql://ec2-54-247-117-145.eu-west-1.compute.amazonaws.com:5432/ddbvlt6treu35e?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
    private static String USER = "sapfwipdxbakhf";
    private static String PASSWORD = "5ee6ce59fcd1ea10672183dd1c2f9a5c268380a26f637cb25a6e482198febba6";
    private static int poolSize = 5;

    private static BlockingQueue<Connection> connections;

    static {
        File f = new File("config");
        try(Scanner br = new Scanner(f)) {
            URL = br.nextLine();
            USER = br.nextLine();
            PASSWORD = br.nextLine();
            poolSize = Integer.parseInt(br.nextLine());
            connections = new ArrayBlockingQueue<Connection>(poolSize);
            System.out.println(connections.size());
            Class.forName("org.postgresql.Driver");
            for (int i = 0; i < poolSize; i++) {
                connections.add(new MyConnection(DriverManager.getConnection(URL,USER,PASSWORD)));
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }

    }
    public synchronized static Connection getConnection() throws ClassNotFoundException, SQLException {
        System.out.println(Thread.currentThread().getName()+" get Connection");
        try {
            Connection connection = connections.poll(2,TimeUnit.SECONDS);
            return connection;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void putConnection(Connection con){
        System.out.println(Thread.currentThread().getName()+" put Connection");
        connections.add(con);
    }
}
