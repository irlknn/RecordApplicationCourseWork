package repository;

import javafx.collections.ObservableList;
import models.Record;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class DBTableManagerTest {
    private static final String TABLE_NAME = "test";
    private DBTableManager manager;
    private Connection keepAliveConnection;  // Keep connection open

    @BeforeEach
    public void setup() throws Exception {
        new DatabaseConnector().setTestURL();
        manager = new DBTableManager();

        keepAliveConnection = DatabaseConnector.getConnection(); // Keep alive
        try (Statement stmt = keepAliveConnection.createStatement()) {
            stmt.execute("CREATE TABLE " + TABLE_NAME + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT, style TEXT, duration TEXT)");
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (keepAliveConnection != null && !keepAliveConnection.isClosed()) {
            keepAliveConnection.close();
        }
    }

    @Test
    public void testInsertAndSelect() {
        Record record = new Record();
        record.setTitle("Song 1");
        record.setStyle("Rock");
        record.setDuration(LocalTime.of(0, 5, 0));

        manager.insertIntoTable(record, TABLE_NAME);
        ObservableList<Record> records = manager.selectAllFromTable(TABLE_NAME);

        assertEquals(1, records.size());
        assertEquals("Song 1", records.getFirst().getTitle());
        assertEquals("Rock", records.getFirst().getStyle());
        assertEquals((LocalTime.of(0, 5, 0)), records.getFirst().getDuration());
    }

    @Test
    public void testDeleteById() {
        Record record = new Record();
        record.setTitle("ToDelete");
        record.setStyle("Jazz");
        record.setDuration(LocalTime.of(0, 4, 0));

        manager.insertIntoTable(record, TABLE_NAME);
        int id = manager.selectAllFromTable(TABLE_NAME).get(0).getId();

        manager.deleteFromTableById(id, TABLE_NAME);

        ObservableList<Record> records = manager.selectAllFromTable(TABLE_NAME);
        assertTrue(records.isEmpty());
    }

    @Test
    public void testFindByParameter() {
        Record record = new Record();
        record.setTitle("FindMe");
        record.setStyle("Electronic");
        record.setDuration(LocalTime.of(0, 2, 0));

        manager.insertIntoTable(record, TABLE_NAME);

        ObservableList<Record> results = manager.findByParameter(TABLE_NAME, "title", "Find");
        assertEquals(1, results.size());
        assertEquals("FindMe", results.get(0).getTitle());
    }

    @Test
    public void testFindByDuration() {
        Record r1 = new Record(); r1.setTitle("Short"); r1.setStyle("Pop"); r1.setDuration(LocalTime.of(0, 1, 0));
        Record r2 = new Record(); r2.setTitle("Medium"); r2.setStyle("Pop"); r2.setDuration(LocalTime.of(0, 12, 0));
        Record r3 = new Record(); r3.setTitle("Long"); r3.setStyle("Pop"); r3.setDuration(LocalTime.of(0, 15, 0));

        manager.insertIntoTable(r1, TABLE_NAME);
        manager.insertIntoTable(r2, TABLE_NAME);
        manager.insertIntoTable(r3, TABLE_NAME);

        ObservableList<Record> results = manager.findByDuration(TABLE_NAME, "00:11:00", "00:13:00");
        assertEquals(1, results.size());
        assertEquals("Medium", results.get(0).getTitle());
    }

    @Test
    public void testSortByParameter() {
        Record r1 = new Record(); r1.setTitle("B Title"); r1.setStyle("Style1"); r1.setDuration(LocalTime.of(0, 3, 30));
        Record r2 = new Record(); r2.setTitle("A Title"); r2.setStyle("Style2"); r2.setDuration(LocalTime.of(0, 3, 30));

        manager.insertIntoTable(r1, TABLE_NAME);
        manager.insertIntoTable(r2, TABLE_NAME);

        ObservableList<Record> sorted = manager.sortByParameter(TABLE_NAME, "title");
        assertEquals("A Title", sorted.get(0).getTitle());
        assertEquals("B Title", sorted.get(1).getTitle());
    }
}
