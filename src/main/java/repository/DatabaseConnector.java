package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static String URL = "jdbc:sqlite:records.db";
    void setTestURL() {
        URL = "jdbc:sqlite:file:test?mode=memory&cache=shared"; // Shared in-memory DB
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
