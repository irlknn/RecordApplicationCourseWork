package org.example.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.repository.DBService.validateTableName;

public class DBManager {
    private static final Logger logger = LoggerFactory.getLogger(DBManager.class);

    {
        initializeDB();
    }

    private void initializeDB() {
        String sql = """
                CREATE TABLE IF NOT EXISTS records(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    style TEXT,
                    duration TEXT NOT NULL
                );
                """;

        try (Connection connection = DatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sql);
            logger.info("Connected to DB and created initial table records");
        } catch (SQLException e) {
            logger.error("Error in connecting to DB and creating initial table {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to initialize table in DB {}", e.getMessage());
        }
    }

    public void createTable(String tableName) {
        validateTableName(tableName);
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "style TEXT," +
                "duration TEXT NOT NULL" +
                ");";

        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
            logger.info("Table {} was created or already existed", tableName);
        } catch (SQLException e) {
            logger.error("Failed to create table {}: {}", tableName, e.getMessage());
        }
    }

    public void deleteTable(String tableName) {
        String sql = "DROP TABLE IF EXISTS " + tableName;

        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            logger.info("Table {} was deleted", tableName);
        } catch (SQLException e) {
            logger.error("Failed to delete table {}: {}", tableName, e.getMessage());
        }
    }

}
