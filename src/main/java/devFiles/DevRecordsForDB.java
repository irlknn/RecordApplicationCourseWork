package devFiles;

import models.Record;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

import static utils.TimeUtils.changeTimeFormat;

public class DevRecordsForDB {
    private static final Logger logger = LogManager.getLogger(DevRecordsForDB.class);

    public static void addData() {
        List<Record> records = List.of(
                new Record("Bohemian Rhapsody", "Rock", LocalTime.of(0, 5, 55)),
                new Record("Billie Jean", "Pop", LocalTime.of(0, 4, 54)),
                new Record("Lose Yourself", "Rap", LocalTime.of(0, 5, 26)),
                new Record("Smells Like Teen Spirit", "Grunge", LocalTime.of(0, 5, 1)),
                new Record("Rolling in the Deep", "Soul", LocalTime.of(0, 3, 48)),
                new Record("Blinding Lights", "Synthpop", LocalTime.of(0, 3, 22)),
                new Record("Imagine", "Softrock", LocalTime.of(0, 3, 3)),
                new Record("Sweet Child O' Mine", "Hardrock", LocalTime.of(0, 5, 56)),
                new Record("One Dance", "Afrobeats", LocalTime.of(0, 2, 54)),
                new Record("Shape of You", "Pop", LocalTime.of(0, 3, 53)),
                new Record("Back in Black", "Rock", LocalTime.of(0, 4, 15)),
                new Record("God's Plan", "HipHop", LocalTime.of(0, 3, 19)),
                new Record("Hotel California", "Rock", LocalTime.of(0, 6, 30)),
                new Record("Halo", "Rnb", LocalTime.of(0, 3, 44)),
                new Record("Take Five", "Jazz", LocalTime.of(0, 5, 24)),
                new Record("Uptown Funk", "Funk", LocalTime.of(0, 4, 30)),
                new Record("Thinking Out Loud", "Pop", LocalTime.of(0, 4, 41)),
                new Record("Numb", "Alternative", LocalTime.of(0, 3, 7)),
                new Record("Hey Ya!", "HipHop", LocalTime.of(0, 3, 55)),
                new Record("Yesterday", "Ballad", LocalTime.of(0, 2, 5)),
                new Record("Stayin' Alive", "Disco", LocalTime.of(0, 4, 45)),
                new Record("Bad Guy", "Electropop", LocalTime.of(0, 3, 14)),
                new Record("Thunderstruck", "Hardrock", LocalTime.of(0, 4, 52)),
                new Record("All of Me", "Soul", LocalTime.of(0, 4, 29)),
                new Record("Dance Monkey", "Pop", LocalTime.of(0, 3, 29)),
                new Record("Can't Stop", "Funkrock", LocalTime.of(0, 4, 29)),
                new Record("No Woman No Cry", "Reggae", LocalTime.of(0, 4, 6)),
                new Record("Zombie", "Alternative", LocalTime.of(0, 5, 6)),
                new Record("Sunflower", "HipHop", LocalTime.of(0, 2, 38)),
                new Record("Paint It Black", "Rock", LocalTime.of(0, 3, 47))
        );


        String sql = "INSERT INTO records(title, style, duration) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Record record : records) {
                statement.setString(1, record.getTitle());
                statement.setString(2, record.getStyle());
                statement.setString(3, changeTimeFormat(record.getDuration()));
                statement.addBatch();
            }
            statement.executeBatch();
            logger.info("Added {} pre-prepared records to DB", records.size());
        } catch (SQLException e) {
            logger.error("Failed to insert pre-prepared records");
        }
    }

}

