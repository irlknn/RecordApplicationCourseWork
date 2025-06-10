//package org.example.repository;
//
//import javafx.collections.ObservableList;
//import org.example.models.Record;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockedStatic;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.time.LocalTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//public class DBTableManagerTest {
//    private static final String TABLE_NAME = "test";
//    private DBTableManager manager;
//    private Connection keepAliveConnection;  // Keep connection open
//
//    @BeforeEach
//    public void setup() throws Exception {
//        new DatabaseConnector().setTestURL();
//        manager = new DBTableManager();
//
//        keepAliveConnection = DatabaseConnector.getConnection(); // Keep alive
//        try (Statement stmt = keepAliveConnection.createStatement()) {
//            stmt.execute("CREATE TABLE " + TABLE_NAME + " (" +
//                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                    "title TEXT, style TEXT, duration TEXT, author TEXT, description TEXT)");
//        }
//    }
//
//    @AfterEach
//    public void tearDown() throws Exception {
//        if (keepAliveConnection != null && !keepAliveConnection.isClosed()) {
//            keepAliveConnection.close();
//        }
//    }
//
//    @Test
//    public void testInsertAndSelect() {
//        Record record = new Record();
//        record.setTitle("Song 1");
//        record.setStyle("Rock");
//        record.setDuration(LocalTime.of(0, 5, 0));
//
//        manager.insertIntoTable(record, TABLE_NAME);
//        ObservableList<Record> records = manager.selectAllFromTable(TABLE_NAME);
//
//        assertEquals(1, records.size());
//        assertEquals("Song 1", records.getFirst().getTitle());
//        assertEquals("Rock", records.getFirst().getStyle());
//        assertEquals((LocalTime.of(0, 5, 0)), records.getFirst().getDuration());
//    }
//
//    @Test
//    public void testDeleteById() {
//        Record record = new Record();
//        record.setTitle("ToDelete");
//        record.setStyle("Jazz");
//        record.setDuration(LocalTime.of(0, 4, 0));
//        record.setAuthor("author");
//        record.setDescription("description");
//
//        manager.insertIntoTable(record, TABLE_NAME);
//        int id = manager.selectAllFromTable(TABLE_NAME).getFirst().getId();
//
//        manager.deleteFromTableById(id, TABLE_NAME);
//
//        ObservableList<Record> records = manager.selectAllFromTable(TABLE_NAME);
//        assertTrue(records.isEmpty());
//    }
//
//    @Test
//    public void testFindByParameter() {
//        Record record = new Record();
//        record.setTitle("FindMe");
//        record.setStyle("Electronic");
//        record.setDuration(LocalTime.of(0, 2, 0));
//        record.setAuthor("author");
//        record.setDescription("description");
//
//        manager.insertIntoTable(record, TABLE_NAME);
//
//        ObservableList<Record> results = manager.findByParameter(TABLE_NAME, "title", "Find");
//        assertEquals(1, results.size());
//        assertEquals("FindMe", results.getFirst().getTitle());
//    }
//
//    @Test
//    public void testFindByDuration() {
//        Record r1 = new Record();
//        r1.setTitle("Short");
//        r1.setStyle("Pop");
//        r1.setDuration(LocalTime.of(0, 1, 0));
//        r1.setAuthor("author");
//        r1.setDescription("description");
//
//        Record r2 = new Record();
//        r2.setTitle("Medium");
//        r2.setStyle("Pop");
//        r2.setDuration(LocalTime.of(0, 12, 0));
//        r2.setAuthor("author");
//        r2.setDescription("description");
//
//        Record r3 = new Record();
//        r3.setTitle("Long");
//        r3.setStyle("Pop");
//        r3.setDuration(LocalTime.of(0, 15, 0));
//        r3.setAuthor("author");
//        r3.setDescription("description");
//
//        manager.insertIntoTable(r1, TABLE_NAME);
//        manager.insertIntoTable(r2, TABLE_NAME);
//        manager.insertIntoTable(r3, TABLE_NAME);
//
//        ObservableList<Record> results = manager.findByDuration(TABLE_NAME, "00:11:00", "00:13:00");
//        assertEquals(1, results.size());
//        assertEquals("Medium", results.getFirst().getTitle());
//    }
//
//    @Test
//    public void testSortByParameter() {
//        Record r1 = new Record();
//        r1.setTitle("B Title");
//        r1.setStyle("Style1");
//        r1.setDuration(LocalTime.of(0, 3, 30));
//        r1.setAuthor("author");
//        r1.setDescription("description");
//
//        Record r2 = new Record();
//        r2.setTitle("A Title");
//        r2.setStyle("Style2");
//        r2.setDuration(LocalTime.of(0, 3, 30));
//        r2.setAuthor("author");
//        r2.setDescription("description");
//
//        manager.insertIntoTable(r1, TABLE_NAME);
//        manager.insertIntoTable(r2, TABLE_NAME);
//
//        ObservableList<Record> sorted = manager.sortByParameter(TABLE_NAME, "title");
//        assertEquals("A Title", sorted.get(0).getTitle());
//        assertEquals("B Title", sorted.get(1).getTitle());
//    }
//
//    @Test
//    void testSelectAllFromTable_SQLException() throws Exception {
//        String tableName = "test_table";
//
//        try (MockedStatic<DatabaseConnector> mocked = mockStatic(DatabaseConnector.class)) {
//            Connection mockConnection = mock(Connection.class);
//            Statement mockStatement = mock(Statement.class);
//
//            when(mockConnection.createStatement()).thenReturn(mockStatement);
//            when(mockStatement.executeQuery(anyString())).thenThrow(new SQLException("DB error"));
//            mocked.when(DatabaseConnector::getConnection).thenReturn(mockConnection);
//
//            DBTableManager manager = new DBTableManager();
//
//            assertThrows(RuntimeException.class, () -> manager.selectAllFromTable(tableName));
//        }
//    }
//
//    @Test
//    void testSelectAllFromTable_OtherException() throws Exception {
//        String tableName = "test_table";
//
//        try (MockedStatic<DatabaseConnector> mocked = mockStatic(DatabaseConnector.class)) {
//            mocked.when(DatabaseConnector::getConnection).thenThrow(new RuntimeException("Unexpected"));
//
//            DBTableManager manager = new DBTableManager();
//
//            manager.selectAllFromTable(tableName);
//
//        }
//    }
//
//}
