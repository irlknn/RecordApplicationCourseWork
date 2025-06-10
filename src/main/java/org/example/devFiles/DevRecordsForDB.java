package org.example.devFiles;

import org.example.models.Record;
import org.example.repository.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

import static org.example.utils.TimeUtils.changeTimeFormat;

public class DevRecordsForDB {

    {
        addRecordsData();
        addCollectionsData();
        addRecordCollectionsData();
    }

    public void addRecordsData() {
        String[] titles = {
                "Star Song", "Night Wind", "Morning Fog", "Autumn Blues", "Summer Rain",
                "Dance of Light", "Leaf Whisper", "Sunny Road", "Seagull Over Sea", "Soul Flight",
                "Harmony", "Peaceful World", "Steppe Legend", "Stone Forest", "Silver Silence",
                "Echoes", "Street Musician", "Wings of Time", "Thought Mosaic", "Shadow of Memories",
                "Inspiration", "Dream Anthem", "Evening Glow", "Life Goes On", "Impulse",
                "Heartbeat", "Twilight Walk", "Freedom Call", "Ocean Depths", "Skyline"
        };

        String[] styles = {"Pop", "Rock", "Jazz", "Classical", "Electronic"};
        String[] authors = {"John Smith", "Emma Johnson", "Liam Brown", "Olivia Davis", "Noah Wilson"};
        String[] descriptions = {
                "A beautiful melody", "Energetic and uplifting", "Smooth and relaxing",
                "Deep and emotional", "Experimental vibes"
        };

        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO records (title, style, duration, author, description) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < titles.length; i++) {
                    stmt.setString(1, titles[i]);
                    stmt.setString(2, styles[i % styles.length]);
                    stmt.setString(3, LocalTime.of(0, (2 + i % 5), (30 + i % 30)).toString());
                    stmt.setString(4, authors[i % authors.length]);
                    stmt.setString(5, descriptions[i % descriptions.length]);
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
            System.out.println("Added records in database");
        } catch (SQLException e) {
            System.out.println("Failed to add records");
            e.printStackTrace();
        }
    }

    public void addCollectionsData() {
        String[] names = {
                "Best of 2023", "Chill Vibes", "Workout Mix", "Morning Playlist", "Evening Relax",
                "Jazz Classics", "Pop Hits", "Rock Legends", "Electronic Essentials", "Acoustic Set"
        };

        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO collections (name) VALUES (?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (String name : names) {
                    stmt.setString(1, name);
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
            System.out.println("Added collections in database");
        } catch (SQLException e) {
            System.out.println("Failed to add collections");
            e.printStackTrace();
        }
    }

    public void addRecordCollectionsData() {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO record_collections (record_id, collection_id) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (int i = 1; i <= 30; i++) {
                    int collectionId = (i % 10) + 1;
                    stmt.setInt(1, i);
                    stmt.setInt(2, collectionId);
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
            System.out.println("Added record_collections in database");
        } catch (SQLException e) {
            System.out.println("Failed to add record_collections");
            e.printStackTrace();
        }
    }


    public void addData() {
        List<Record> records = List.of(
                new Record("Bohemian Rhapsody", "Rock", LocalTime.of(0, 5, 55), "Queen", "An operatic rock epic that defies all conventions."),
                new Record("Billie Jean", "Pop", LocalTime.of(0, 4, 54), "Michael Jackson", "A signature track with a legendary bassline."),
                new Record("Lose Yourself", "Rap", LocalTime.of(0, 5, 26), "Eminem", "A motivational anthem from the film '8 Mile'."),
                new Record("Smells Like Teen Spirit", "Grunge", LocalTime.of(0, 5, 1), "Nirvana", "A rebellious grunge explosion that defined a generation."),
                new Record("Rolling in the Deep", "Soul", LocalTime.of(0, 3, 48), "Adele", "A soulful powerhouse about heartbreak and revenge."),
                new Record("Blinding Lights", "Synthpop", LocalTime.of(0, 3, 22), "The Weeknd", "Retro synth vibes with modern flair."),
                new Record("Imagine", "Softrock", LocalTime.of(0, 3, 3), "John Lennon", "A peaceful vision of a united world."),
                new Record("Sweet Child O' Mine", "Hardrock", LocalTime.of(0, 5, 56), "Guns N' Roses", "Iconic guitar riffs meet heartfelt lyrics."),
                new Record("One Dance", "Afrobeats", LocalTime.of(0, 2, 54), "Drake", "A global dance floor hit with smooth rhythms."),
                new Record("Shape of You", "Pop", LocalTime.of(0, 3, 53), "Ed Sheeran", "A catchy love story with rhythmic charm."),
                new Record("Back in Black", "Rock", LocalTime.of(0, 4, 15), "AC/DC", "A comeback anthem with powerful guitar."),
                new Record("God's Plan", "HipHop", LocalTime.of(0, 3, 19), "Drake", "An uplifting track about faith and destiny."),
                new Record("Hotel California", "Rock", LocalTime.of(0, 6, 30), "Eagles", "A haunting tale set to melodic guitar."),
                new Record("Halo", "Rnb", LocalTime.of(0, 3, 44), "Beyoncé", "A powerful ballad about love and protection."),
                new Record("Take Five", "Jazz", LocalTime.of(0, 5, 24), "Dave Brubeck", "A jazz classic in 5/4 time."),
                new Record("Uptown Funk", "Funk", LocalTime.of(0, 4, 30), "Mark Ronson ft. Bruno Mars", "A funky party anthem full of swagger."),
                new Record("Thinking Out Loud", "Pop", LocalTime.of(0, 4, 41), "Ed Sheeran", "A romantic ballad for the ages."),
                new Record("Numb", "Alternative", LocalTime.of(0, 3, 7), "Linkin Park", "A cry for understanding in a disconnected world."),
                new Record("Hey Ya!", "HipHop", LocalTime.of(0, 3, 55), "OutKast", "A funky and unconventional love song."),
                new Record("Yesterday", "Ballad", LocalTime.of(0, 2, 5), "The Beatles", "A melancholic melody of lost love."),
                new Record("Stayin' Alive", "Disco", LocalTime.of(0, 4, 45), "Bee Gees", "A disco anthem of survival and rhythm."),
                new Record("Bad Guy", "Electropop", LocalTime.of(0, 3, 14), "Billie Eilish", "A quirky, dark, and catchy tune."),
                new Record("Thunderstruck", "Hardrock", LocalTime.of(0, 4, 52), "AC/DC", "Electrifying guitar and explosive energy."),
                new Record("All of Me", "Soul", LocalTime.of(0, 4, 29), "John Legend", "A piano ballad about unconditional love."),
                new Record("Dance Monkey", "Pop", LocalTime.of(0, 3, 29), "Tones and I", "A unique voice over a catchy beat."),
                new Record("Can't Stop", "Funkrock", LocalTime.of(0, 4, 29), "Red Hot Chili Peppers", "An energetic blend of funk and rock."),
                new Record("No Woman No Cry", "Reggae", LocalTime.of(0, 4, 6), "Bob Marley", "A soothing message of resilience."),
                new Record("Zombie", "Alternative", LocalTime.of(0, 5, 6), "The Cranberries", "A protest song with haunting vocals."),
                new Record("Sunflower", "HipHop", LocalTime.of(0, 2, 38), "Post Malone & Swae Lee", "люблю джаву"),
                new Record("Paint It Black", "Rock", LocalTime.of(0, 3, 47), "The Rolling Stones", "A dark, sitar-infused classic.")
        );

        String sql = "INSERT INTO records(title, style, duration, author, description) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Record record : records) {
                statement.setString(1, record.getTitle());
                statement.setString(2, record.getStyle());
                statement.setString(3, changeTimeFormat(record.getDuration()));
                statement.setString(4, record.getAuthor());
                statement.setString(5, record.getDescription());
                statement.addBatch();
            }
            statement.executeBatch();
            System.out.println("Added pre-prepared records to DB " + records.size() + records.getFirst());
        } catch (SQLException e) {
            System.out.println("Failed to insert pre-prepared records " + e.getMessage());
        }
    }

}

