package org.example.repository;

import javafx.collections.ObservableList;
import org.example.models.Record;
import org.example.utils.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DBRecordCollectionManagerTest {

    private DBRecordCollectionManager manager;
    private Connection mockConn;
    private PreparedStatement mockStmt;
    private ResultSet mockRs;

    @BeforeEach
    void setup() throws Exception {
        manager = new DBRecordCollectionManager();
        mockConn = mock(Connection.class);
        mockStmt = mock(PreparedStatement.class);
        mockRs = mock(ResultSet.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockStmt.executeUpdate()).thenReturn(1);
    }

    @Test
    void testAddRecordToCollection() throws Exception {
        try (
                MockedStatic<DatabaseConnector> dbMock = mockStatic(DatabaseConnector.class);
                MockedStatic<TimeUtils> timeMock = mockStatic(TimeUtils.class);
        ) {
            dbMock.when(DatabaseConnector::getConnection).thenReturn(mockConn);

            DBRecordManager mockRM = mock(DBRecordManager.class);
            manager.recordManager = mockRM;
            when(mockRM.insertRecord(any())).thenReturn(123);

            Record r = new Record();
            manager.addRecordToCollection(r, 1);
            verify(mockStmt).executeUpdate();
        }
    }

    @Test
    void testRemoveRecordFromCollection() throws Exception {
        try (MockedStatic<DatabaseConnector> dbMock = mockStatic(DatabaseConnector.class)) {
            dbMock.when(DatabaseConnector::getConnection).thenReturn(mockConn);
            manager.removeRecordFromCollection(10, 5);
            verify(mockStmt).executeUpdate();
        }
    }

    @Test
    void testFindByParameter() throws Exception {
        try (
                MockedStatic<DatabaseConnector> dbMock = mockStatic(DatabaseConnector.class);
                MockedStatic<TimeUtils> timeMock = mockStatic(TimeUtils.class)
        ) {
            dbMock.when(DatabaseConnector::getConnection).thenReturn(mockConn);
            when(mockRs.next()).thenReturn(true).thenReturn(false);
            when(mockRs.getInt("id")).thenReturn(1);
            when(mockRs.getString("title")).thenReturn("Test");
            when(mockRs.getString("style")).thenReturn("Rock");
            when(mockRs.getString("duration")).thenReturn("00:02:30");
            when(mockRs.getString("author")).thenReturn("Author");
            when(mockRs.getString("description")).thenReturn("Desc");
            timeMock.when(() -> TimeUtils.changeTimeFormat("00:02:30")).thenReturn(LocalTime.parse("00:02:30"));

            ObservableList<Record> records = manager.findByParameter(1, "title", "Test");
            assertEquals(1, records.size());
            assertEquals("Test", records.get(0).getTitle());
        }
    }

    @Test
    void testFindByDuration() throws Exception {
        try (
                MockedStatic<DatabaseConnector> dbMock = mockStatic(DatabaseConnector.class);
                MockedStatic<TimeUtils> timeMock = mockStatic(TimeUtils.class)
        ) {
            dbMock.when(DatabaseConnector::getConnection).thenReturn(mockConn);
            when(mockRs.next()).thenReturn(true).thenReturn(false);
            when(mockRs.getInt("id")).thenReturn(2);
            when(mockRs.getString("title")).thenReturn("DurTest");
            when(mockRs.getString("style")).thenReturn("Jazz");
            when(mockRs.getString("duration")).thenReturn("03:00");
            when(mockRs.getString("author")).thenReturn("Duran");
            when(mockRs.getString("description")).thenReturn("Desc2");
            timeMock.when(() -> TimeUtils.changeTimeFormat("00:03:00")).thenReturn(LocalTime.parse("00:03:00"));

            ObservableList<Record> records = manager.findByDuration(1, "00:01:00", "00:05:00");
            assertEquals(1, records.size());
            assertEquals("DurTest", records.get(0).getTitle());
        }
    }

    @Test
    void testSortByParameter() throws Exception {
        try (
                MockedStatic<DatabaseConnector> dbMock = mockStatic(DatabaseConnector.class);
                MockedStatic<TimeUtils> timeMock = mockStatic(TimeUtils.class)
        ) {
            dbMock.when(DatabaseConnector::getConnection).thenReturn(mockConn);
            when(mockRs.next()).thenReturn(true).thenReturn(false);
            when(mockRs.getInt("id")).thenReturn(3);
            when(mockRs.getString("title")).thenReturn("SortTest");
            when(mockRs.getString("style")).thenReturn("Pop");
            when(mockRs.getString("duration")).thenReturn("00:04:00");
            when(mockRs.getString("author")).thenReturn("Sorter");
            when(mockRs.getString("description")).thenReturn("Sorted");
            timeMock.when(() -> TimeUtils.changeTimeFormat("00:04:00")).thenReturn(LocalTime.parse("00:04:00"));

            ObservableList<Record> records = manager.sortByParameter(1, "title");
            assertEquals(1, records.size());
            assertEquals("SortTest", records.get(0).getTitle());
        }
    }
}
