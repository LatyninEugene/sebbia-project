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
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static int poolSize;

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
