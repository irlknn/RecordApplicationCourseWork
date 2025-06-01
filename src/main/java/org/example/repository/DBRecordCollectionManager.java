package org.example.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.models.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.utils.TimeUtils.changeTimeFormat;

public class DBRecordCollectionManager {
    private static final Logger logger = LoggerFactory.getLogger(DBRecordCollectionManager.class);
    DBRecordManager recordManager = new DBRecordManager();

    /**
     * Making connection between record and collection
     * @param record - record to insert
     * @param collectionId - id of collection in which is insertion
     */
    public void addRecordToCollection(Record record, int collectionId) {
        int recordId = recordManager.insertRecord(record);
        String sql = "INSERT OR IGNORE INTO record_collections (record_id, collection_id) VALUES (?, ?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, recordId);
            ps.setInt(2, collectionId);
            ps.executeUpdate();
            logger.info("Added record with id {} to collection with id {}", recordId, collectionId);
        } catch (SQLException e) {
            logger.error("Failed to add record in record_collection table {}", e.getMessage());
        }
    }

    /**
     * Delete record from collection
     * @param recordId  - record id
     * @param collectionId - collection id
     */
    public void removeRecordFromCollection(int recordId, int collectionId) {
        String sql = "DELETE FROM record_collections WHERE record_id = ? AND collection_id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, recordId);
            ps.setInt(2, collectionId);
            ps.executeUpdate();
            logger.info("Record with id {} was deleted from collection with id {}", recordId, collectionId);
        } catch (SQLException e) {
            logger.error("Failed to delete record from collection");
        }
    }

    public ObservableList<Record> findByParameter(int collectionId, String parameter, String input){
        ObservableList<Record> records = FXCollections.observableArrayList();

        String sql = """
        SELECT r.*
        FROM records r
        JOIN record_collections rc ON r.id = rc.record_id
        WHERE rc.collection_id = ? AND LOWER(r.%s) LIKE LOWER(?)
    """.formatted(parameter);

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, collectionId);
            stmt.setString(2, "%" + input + "%");

            getRecordsFromDB(records, stmt);
            logger.info("Found {} records {} like {}", records.size(), parameter, input);
        } catch (SQLException e) {
            logger.error("DB query for find by parameter - {}, input - {} failed. Error message: {}", parameter, input, e.getMessage());
        }
        return records;
    }

    public ObservableList<Record> findByDuration(int collectionId, String start, String end) {
        ObservableList<Record> records = FXCollections.observableArrayList();

        String sql = """
        SELECT r.*
        FROM records r
        JOIN record_collections rc ON r.id = rc.record_id
        WHERE rc.collection_id = ?
          AND r.duration BETWEEN ? AND ?
    """;

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, collectionId);
            statement.setString(2, start);
            statement.setString(3, end);

            getRecordsFromDB(records, statement);

        } catch (SQLException e) {
            logger.error("DB query failed for find by duration. Error message: {}", e.getMessage());
        }
        return records;
    }

    public ObservableList<Record> sortByParameter(int collectionId, String parameter){
        ObservableList<Record> records = FXCollections.observableArrayList();

        String sql = """
        SELECT r.*
        FROM records r
        JOIN record_collections rc ON r.id = rc.record_id
        WHERE rc.collection_id = ?
        ORDER BY r.""" + parameter;
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, collectionId);
            getRecordsFromDB(records, statement);
            logger.info("Sorted {} records in collection {} by {}", records.size(), collectionId, parameter);
        } catch (SQLException e) {
            logger.error("DB query failed for sort by parameter. Error message: {}", e.getMessage());
        }

        return records;
    }

    private void getRecordsFromDB(ObservableList<Record> records, PreparedStatement statement) {
        try (ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Record record = new Record();
                record.setId(rs.getInt("id"));
                record.setTitle(rs.getString("title"));
                record.setStyle(rs.getString("style"));
                record.setDuration(changeTimeFormat(rs.getString("duration")));
                record.setAuthor(rs.getString("author"));
                record.setDescription(rs.getString("description"));
                records.add(record);
            }
        } catch (SQLException e) {
            logger.error("Failed fetching data from DB (statement - {}) Error message: {}", statement , e.getMessage());
        }
    }



}
