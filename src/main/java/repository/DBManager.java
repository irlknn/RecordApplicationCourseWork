package repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static repository.DBService.validateTableName;

public class DBManager {
    private static final Logger logger = LogManager.getLogger(DBManager.class);

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
