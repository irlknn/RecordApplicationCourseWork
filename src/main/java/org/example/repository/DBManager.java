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
        initializeRecords();
        initializeCollections();
        initializeRecordCollection();
    }

    private void initializeCollections() {
        String sql = """
                CREATE TABLE IF NOT EXISTS collections(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL
                );
                """;

        try (Connection connection = DatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sql);
            logger.info("Connected to DB and created initial table collections");
        } catch (SQLException e) {
            logger.error("Error in connecting to DB and creating initial collection table {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to initialize collection table in DB {}", e.getMessage());
        }
    }

    private void initializeRecords() {
        String sql = """
                CREATE TABLE IF NOT EXISTS records(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    style TEXT,
                    duration TEXT,
                    author TEXT,
                    description TEXT
                );
                """;

        try (Connection connection = DatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sql);
            logger.info("Connected to DB and created initial table records");
        } catch (SQLException e) {
            logger.error("Error in connecting to DB and creating initial record table {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to initialize record table in DB {}", e.getMessage());
        }
    }

    private void initializeRecordCollection() {
        String sql = """
                CREATE TABLE IF NOT EXISTS record_collections(
                    collection_id INTEGER,
                    record_id INTEGER
                );
                """;

        try (Connection connection = DatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sql);
            logger.info("Connected to DB and created initial table record_collections");
        } catch (SQLException e) {
            logger.error("Error in connecting to DB and creating initial record_collections table {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to initialize record_collections table in DB {}", e.getMessage());
        }
    }
}