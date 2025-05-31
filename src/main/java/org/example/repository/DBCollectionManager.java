package org.example.repository;

import org.example.models.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBCollectionManager {
    private static final Logger logger = LoggerFactory.getLogger(DBCollectionManager.class);

    /**
     * Insert new collection in database
     * @param name - collection name
     */
    public int insertCollection(String name) {
        String sql = "INSERT INTO collections (name) VALUES (?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
            logger.info("{} was inserted in database", name);
        } catch (SQLException e) {
            logger.error("Failed to insert {} in database", name);
        } catch (Exception e){
            logger.error("Problem in inserting new collection {}", e.getMessage());
        }
        return -1;
    }

    /**
    * Get all collection from database
     */
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
            logger.info("Get {} collections from db", list.size());
        } catch (SQLException e) {
            logger.error("Failed to get {} collection from db, list size - {}", list.size());
        } catch (Exception e){
        logger.error("Problem in getting collections ");
        }
        return list;
    }

    public String getCollectionNameById(int id)
    {
        String sql = "SELECT name FROM collections WHERE id = ?";
        String collectionName = "";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    collectionName = rs.getString("name");
                } else {
                    logger.error("Failed to find name");
                    return "";
                }
            }
        }
        catch (SQLException e)
        {
            logger.error("Failed to get name from db");
        }
        return collectionName;
    }

    /**
     * Delete collection from database with all records in it
     * @param id - id of collection
     */
    public void deleteCollection(int id) {
        String sql = "DELETE FROM collections WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            logger.info("Deleted collection with id - {}", id);
        } catch (SQLException e) {
            logger.error("Failed to delete collection {}", e.getMessage());
        }
    }
}