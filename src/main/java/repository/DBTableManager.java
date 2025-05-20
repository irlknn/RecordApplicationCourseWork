package repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Record;

import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static utils.TimeUtils.changeTimeFormat;

public class DBTableManager {
    private static final Logger logger = LogManager.getLogger(DBTableManager.class);

    public ObservableList<Record> selectAllFromTable(String tableName){
        ObservableList<Record> records = FXCollections.observableArrayList();
        System.out.println("tableName = " + tableName);

        String sql = "SELECT * FROM " + tableName;

        try(Connection connection = DatabaseConnector.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                Record record = new Record();
                record.setId(rs.getInt("id"));
                record.setTitle(rs.getString("title"));
                record.setStyle(rs.getString("style"));
                record.setDuration(changeTimeFormat(rs.getString("duration")));
                records.add(record);
            }
            logger.info("Loaded {} records from db", records.size());
        } catch (SQLException e) {
            logger.error("SQL error while selecting all records from {}", tableName);
            throw new RuntimeException(e);
        }catch (Exception e) {
            logger.error("Fail to process selecting all records from {}", tableName);
        }
        return records;
    }

    public void insertIntoTable(Record record, String tableName){
        String sql = "INSERT INTO " + tableName +"(title, style, duration) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setString(1, record.getTitle());
            statement.setString(2, record.getStyle());
            statement.setString(3, changeTimeFormat(record.getDuration()));
            statement.executeUpdate();

            logger.info("Record saved to db {}", record.getTitle());
        }catch (SQLException e){
            logger.error("Failed to save record {}: {}", record.getTitle(), e.getMessage());
        }
    }

    public void deleteFromTableById(int id, String tableName){
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.info("Deleted record with id {}", id);
        }catch (SQLException e){
            logger.error("Failed to delete record with id {}", id);
            throw new RuntimeException("Failed to delete record: ", e);
        }
    }

    public ObservableList<Record> findByParameter(String tableName, String parameter, String input){
        ObservableList<Record> records = FXCollections.observableArrayList();
        String sql = "SELECT * FROM " + tableName + " WHERE " + parameter + " LIKE ?";

        try(Connection connection = DatabaseConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, "%" + input + "%");
            try(ResultSet rs = statement.executeQuery()) {
                while (rs.next()){
                    Record record = new Record();
                    record.setId(rs.getInt("id"));
                    record.setTitle(rs.getString("title"));
                    record.setStyle(rs.getString("style"));
                    record.setDuration(changeTimeFormat(rs.getString("duration")));
                    records.add(record);
                }
            }
            logger.info("Found {} records {} like {}", records.size(), parameter, input);
        } catch (SQLException e) {
            logger.error("DB query for find by parameter - {}, input - {} failed", parameter, input);
            throw new RuntimeException("DB query failed ", e);
        }
        return records;
    }

    public ObservableList<Record> findByDuration(String tableName, String start, String end){
        ObservableList<Record> records = FXCollections.observableArrayList();
        String sql = "SELECT * FROM " + tableName + " WHERE duration BETWEEN ? AND ? ";

        try(Connection connection = DatabaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, start);
            statement.setString(2, end);
            getRecordsFromTable(records, statement);
        } catch (SQLException e) {
            logger.error("DB query failed for find by duration");
            throw new RuntimeException("DB query failed ", e);
        }
        return records;
    }

    public ObservableList<Record> sortByParameter(String tableName, String parameter){
        ObservableList<Record> records = FXCollections.observableArrayList();
        String sql = "Select * FROM " + tableName + " ORDER BY " + parameter;

        try(Connection connection = DatabaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            getRecordsFromTable(records, statement);
            logger.info("Sorted {} records by {}", records.size(), parameter);
        } catch (SQLException e) {
            logger.error("DB query failed for sort by parameter");
            throw new RuntimeException("DB query failed ", e);
        }
        return records;
    }

    private void getRecordsFromTable(ObservableList<Record> records, PreparedStatement statement){
        try(ResultSet rs = statement.executeQuery()) {
            while (rs.next()){
                Record record = new Record();
                record.setId(rs.getInt("id"));
                record.setTitle(rs.getString("title"));
                record.setStyle(rs.getString("style"));
                record.setDuration(changeTimeFormat(rs.getString("duration")));
                records.add(record);
            }
        } catch (SQLException e) {
            logger.error("Failed fetching data from DB (statement - {})", statement);
            throw new RuntimeException(e);
        }
    }


}
