package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private Connection connection;
    private static DbConnection dbconnection;

    private DbConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos_mvc", "root", "Navindu@12");
    }

    public Connection getConnection(){
        return connection;
    }

    public static DbConnection getInstance() throws ClassNotFoundException, SQLException{
        return (dbconnection == null) ? new DbConnection() : dbconnection;
    }
}
