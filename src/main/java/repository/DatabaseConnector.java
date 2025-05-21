package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:sqlite:records.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}

//    private static Connection testConnection = null;
//
//    public static void setTestConnection(Connection conn) {
//        testConnection = conn;
//    }
//
//    public static Connection getConnection() throws SQLException {
//        if (testConnection != null && !testConnection.isClosed()) {
//            return testConnection;
//        }
//        return DriverManager.getConnection(URL);
//    }

