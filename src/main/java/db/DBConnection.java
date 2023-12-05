package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection conn=null;
    static{
        try {
            System.out.println("dbconnection try start");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/teaExchange","root","root");
            //conn= DriverManager.getConnection("jdbc:sqlserver://micros.database.windows.net:1433;database=teaexchange;user=boya;password=lumala123!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static Connection getConnection(){
        return conn;
    }

    public static void closeConnection(){
        if(conn!=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
