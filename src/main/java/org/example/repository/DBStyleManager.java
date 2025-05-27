package org.example.repository;

import org.example.models.Style;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBStyleManager {
    // Додати новий стиль
    public int insertStyle(String name) {
        String sql = "INSERT INTO styles (name) VALUES (?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    // Всі стилі
    public List<Style> getAllStyles() {
        List<Style> list = new ArrayList<>();
        String sql = "SELECT id, name FROM styles";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Style s = new Style();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                list.add(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // Видалити стиль (та всі його зв'язки з записами)
    public void deleteStyle(int id) {
        String sql = "DELETE FROM styles WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}