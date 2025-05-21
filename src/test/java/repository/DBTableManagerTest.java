package repository;

import javafx.collections.ObservableList;
import models.Record;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DBTableManagerTest {

    private DBTableManager manager;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private Statement mockStatement;
    private ResultSet mockResultSet;
    private MockedStatic<DatabaseConnector> dbMock;

    @BeforeEach
    void setUp() throws Exception {
        manager = new DBTableManager();
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);

        dbMock = mockStatic(DatabaseConnector.class);
        dbMock.when(DatabaseConnector::getConnection).thenReturn(mockConnection);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @AfterEach
    void tearDown() {
        dbMock.close();
    }

    @Test
    void testSelectAllFromTable_ReturnsRecords() throws Exception {
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("title")).thenReturn("TestTitle");
        when(mockResultSet.getString("style")).thenReturn("Jazz");
        when(mockResultSet.getString("duration")).thenReturn("01:20");

        ObservableList<Record> result = manager.selectAllFromTable("my_table");

        assertEquals(1, result.size());
        assertEquals("TestTitle", result.get(0).getTitle());
    }

    @Test
    void testInsertIntoTable_CallsExecuteUpdate() throws Exception {
        Record record = new Record();
        record.setTitle("Test");
        record.setStyle("Rock");
        record.setDuration(LocalTime.of(0, 1, 10));

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> manager.insertIntoTable(record, "my_table"));

        verify(mockPreparedStatement).setString(1, "Test");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testDeleteFromTableById_CallsExecuteUpdate() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> manager.deleteFromTableById(1, "my_table"));

        verify(mockPreparedStatement).setInt(1, 1);
    }

//    @Test
//    void testFindByParameter_ReturnsRecords() throws Exception {
//        when(mockResultSet.next()).thenReturn(true, false);
//        when(mockResultSet.getInt("id")).thenReturn(2);
//        when(mockResultSet.getString("title")).thenReturn("SearchTitle");
//        when(mockResultSet.getString("style")).thenReturn("Pop");
//        when(mockResultSet.getString("duration")).thenReturn("00:45");
//
//        ObservableList<Record> result = manager.findByParameter("my_table", "title", "Search");
//
//        assertEquals(1, result.size());
//        assertEquals("SearchTitle", result.get(0).getTitle());
//    }

//    @Test
//    void testFindByDuration_ReturnsRecords() throws Exception {
//        when(mockResultSet.next()).thenReturn(true, false);
//        when(mockResultSet.getInt("id")).thenReturn(3);
//        when(mockResultSet.getString("title")).thenReturn("TimeTest");
//        when(mockResultSet.getString("style")).thenReturn("Rap");
//        when(mockResultSet.getString("duration")).thenReturn("00:30");
//
//        ObservableList<Record> result = manager.findByDuration("my_table", "00:00", "01:00");
//
//        assertEquals(1, result.size());
//        assertEquals("TimeTest", result.get(0).getTitle());
//    }

//    @Test
//    void testSortByParameter_ReturnsSortedRecords() throws Exception {
//        when(mockResultSet.next()).thenReturn(true, false);
//        when(mockResultSet.getInt("id")).thenReturn(5);
//        when(mockResultSet.getString("title")).thenReturn("Sorted");
//        when(mockResultSet.getString("style")).thenReturn("Techno");
//        when(mockResultSet.getString("duration")).thenReturn("00:55");
//
//        ObservableList<Record> result = manager.sortByParameter("my_table", "title");
//
//        assertEquals(1, result.size());
//        assertEquals("Sorted", result.get(0).getTitle());
//    }

    @Test
    void testSelectAllFromTable_InvalidTable_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> manager.selectAllFromTable("DROP"));
    }
}
