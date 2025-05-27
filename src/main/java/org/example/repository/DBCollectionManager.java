package org.example.repository;

import org.example.models.Collection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBCollectionManager {

    // Додати нову колекцію
    public int insertCollection(String name) {
        String sql = "INSERT INTO collections VALUES (?)";
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

    // Всі колекції
    public List<Collection> getAllCollections() {
        List<Collection> list = new ArrayList<>();
        String sql = "SELECT id, name FROM collections";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Collection c = new Collection();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                list.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // Видалити колекцію (та всі її записи з record_collections)
    public void deleteCollection(int id) {
        String sql = "DELETE FROM collections WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}