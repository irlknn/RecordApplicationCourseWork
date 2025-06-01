package org.example.repository;

import org.example.models.Record;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DBRecordManagerTest {

    @Test
    void testInsertRecordReturnsGeneratedId() throws Exception {
        // Arrange
        Record record = new Record();
        record.setTitle("Test");
        record.setStyle("Jazz");
        record.setDuration(LocalTime.of(0, 3,15));
        record.setAuthor("Author");
        record.setDescription("Description");

        Connection mockConn = mock(Connection.class);
        PreparedStatement mockPs = mock(PreparedStatement.class);
        Statement mockStmt = mock(Statement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
        when(mockConn.createStatement()).thenReturn(mockStmt);
        when(mockStmt.executeQuery("SELECT last_insert_rowid()")).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt(1)).thenReturn(123);

        try (MockedStatic<DatabaseConnector> mockedDb = mockStatic(DatabaseConnector.class)) {
            mockedDb.when(DatabaseConnector::getConnection).thenReturn(mockConn);

            DBRecordManager manager = new DBRecordManager();
            int id = manager.insertRecord(record);

            // Assert
            assertEquals(123, id);
            verify(mockPs).executeUpdate();
            verify(mockStmt).executeQuery("SELECT last_insert_rowid()");
        }
    }

    @Test
    void testGetRecordsByCollectionIdReturnsRecords() throws Exception {
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockPs = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
        when(mockPs.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true).thenReturn(false);
        when(mockRs.getInt("id")).thenReturn(1);
        when(mockRs.getString("title")).thenReturn("Title");
        when(mockRs.getString("style")).thenReturn("Style");
        when(mockRs.getString("duration")).thenReturn("00:03:00");
        when(mockRs.getString("author")).thenReturn("Author");
        when(mockRs.getString("description")).thenReturn("Desc");

        try (
                MockedStatic<DatabaseConnector> dbMock = mockStatic(DatabaseConnector.class);
                MockedStatic<org.example.utils.TimeUtils> timeMock = mockStatic(org.example.utils.TimeUtils.class)
        ) {
            dbMock.when(DatabaseConnector::getConnection).thenReturn(mockConn);
            timeMock.when(() -> org.example.utils.TimeUtils.changeTimeFormat("00:03:00"))
                    .thenReturn(LocalTime.parse("00:03:00"));

            DBRecordManager manager = new DBRecordManager();
            var records = manager.getRecordsByCollectionId(1);

            assertEquals(1, records.size());
            assertEquals("Title", records.get(0).getTitle());
        }
    }

    @Test
    void testDeleteRecordExecutesDelete() throws Exception {
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockPs = mock(PreparedStatement.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);

        try (MockedStatic<DatabaseConnector> dbMock = mockStatic(DatabaseConnector.class)) {
            dbMock.when(DatabaseConnector::getConnection).thenReturn(mockConn);

            DBRecordManager manager = new DBRecordManager();
            manager.deleteRecord(42);

            verify(mockPs).setInt(1, 42);
            verify(mockPs).executeUpdate();
        }
    }

}
