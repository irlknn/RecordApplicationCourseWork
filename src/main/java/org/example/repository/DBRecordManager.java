package org.example.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.models.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.utils.TimeUtils.changeTimeFormat;

public class DBRecordManager {
    private static final Logger logger = LoggerFactory.getLogger(DBRecordManager.class);

    /**
     * Insert record in database
     * @param record - new record to insert in database
     * @return id of created record or -1 in case of error
     */
    protected int insertRecord(Record record) {
        String sql = "INSERT INTO records (title, style, duration, author, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, record.getTitle());
            ps.setString(2, record.getStyle());
            ps.setString(3, changeTimeFormat(record.getDuration()));
            ps.setString(4, record.getAuthor());
            ps.setString(5, record.getDescription());
            ps.executeUpdate();
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to insert new record {}, error: {}", record, e.getMessage());
        }
        return -1;
    }

    /**
     * getting all records from collection
     * @param collectionId - id of record
     * @return records - list with all records in collection
     */
    public ObservableList<Record> getRecordsByCollectionId(int collectionId) {
        ObservableList<Record> records = FXCollections.observableArrayList();
        String sql = """
                SELECT r.id, r.title, r.style, r.duration, r.author, r.description
                FROM records r
                JOIN record_collections rc ON r.id = rc.record_id
                WHERE rc.collection_id = ?
                """;
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, collectionId);
            ResultSet rs = ps.executeQuery();
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
            logger.info("Loaded {} from collection", records.size());
        } catch (SQLException e) {
            logger.error("Failed to get records in collection with id - {}", collectionId);
        }
        return records;
    }

    /**
     * Delete record from database
      * @param recordId - record id
     */
    public void deleteRecord(int recordId) {
        String sql = "DELETE FROM records WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, recordId);
            ps.executeUpdate();
            logger.info("Record {} was deleted", recordId);
        } catch (SQLException e) {
            logger.error("failed to delete record from database {}", e.getMessage());
        }
    }

    // Додати стиль до запису
    public void addStyleToRecord(int recordId, int styleId) {
        String sql = "INSERT OR IGNORE INTO record_styles (record_id, style_id) VALUES (?, ?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, recordId);
            ps.setInt(2, styleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Всі стилі певного запису
//    public List<Style> getStylesForRecord(int recordId) {
//        List<Style> styles = new ArrayList<>();
//        String sql = """
//                SELECT s.id, s.name
//                FROM styles s
//                JOIN record_styles rs ON s.id = rs.style_id
//                WHERE rs.record_id = ?
//                """;
//        try (Connection connection = DatabaseConnector.getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setInt(1, recordId);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Style style = new Style();
//                style.setId(rs.getInt("id"));
//                style.setName(rs.getString("name"));
//                styles.add(style);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return styles;
//    }

    // Видалити стиль із запису
    public void removeStyleFromRecord(int recordId, int styleId) {
        String sql = "DELETE FROM record_styles WHERE record_id = ? AND style_id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, recordId);
            ps.setInt(2, styleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
