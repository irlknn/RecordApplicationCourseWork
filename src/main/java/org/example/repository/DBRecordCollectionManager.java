package org.example.repository;

import javafx.collections.ObservableList;
import org.example.models.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        return null;
    }

    public ObservableList<Record> sortByParameter(int collectionId, String parameter){
        return null;
    }


    public ObservableList<Record> findByDuration(int collectionId, String parameter, String input){
        return null;
    }

}
