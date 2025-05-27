package org.example.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.models.Record;
import org.example.models.Style;

import static org.example.utils.TimeUtils.changeTimeFormat;

public class DBRecordManager {
    public int insertRecord(Record record) {
        String sql = "INSERT INTO records (title, duration) VALUES (?, ?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, record.getTitle());
            ps.setString(2, changeTimeFormat(record.getDuration()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // повертає id нового запису
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public void addRecordToCollection(int recordId, int collectionId) {
        String sql = "INSERT OR IGNORE INTO record_collections (record_id, collection_id) VALUES (?, ?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, recordId);
            ps.setInt(2, collectionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Витягнути всі записи певної колекції
    public ObservableList<Record> getRecordsByCollection(int collectionId) {
        ObservableList<Record> records = FXCollections.observableArrayList();
        String sql = """
                SELECT r.id, r.title, r.duration
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
                record.setDuration(changeTimeFormat(rs.getString("duration")));
                records.add(record);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return records;
    }

    // Видалити запис з колекції
    public void removeRecordFromCollection(int recordId, int collectionId) {
        String sql = "DELETE FROM record_collections WHERE record_id = ? AND collection_id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, recordId);
            ps.setInt(2, collectionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Видалити запис повністю (і з усіх колекцій і стилів)
    public void deleteRecord(int recordId) {
        String sql = "DELETE FROM records WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, recordId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    public List<Style> getStylesForRecord(int recordId) {
        List<Style> styles = new ArrayList<>();
        String sql = """
                SELECT s.id, s.name
                FROM styles s
                JOIN record_styles rs ON s.id = rs.style_id
                WHERE rs.record_id = ?
                """;
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, recordId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Style style = new Style();
                style.setId(rs.getInt("id"));
                style.setName(rs.getString("name"));
                styles.add(style);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return styles;
    }

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
