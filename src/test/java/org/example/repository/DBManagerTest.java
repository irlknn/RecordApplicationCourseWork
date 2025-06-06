package org.example.repository;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.*;

public class DBManagerTest {

    @Test
    void testInitializationCreatesTables() throws Exception {
        Connection mockConn = mock(Connection.class);
        Statement mockStmt = mock(Statement.class);

        when(mockConn.createStatement()).thenReturn(mockStmt);

        try (MockedStatic<DatabaseConnector> dbMock = mockStatic(DatabaseConnector.class)) {
            dbMock.when(DatabaseConnector::getConnection).thenReturn(mockConn);

            new DBManager();

            // Очікуємо, що execute() викликається 3 рази — по 1 на кожну таблицю
            verify(mockStmt, times(3)).execute(anyString());
        }
    }

    @Test
    void testInitializeCollectionsSQLException() throws Exception {
        Connection mockConn = mock(Connection.class);
        when(mockConn.createStatement()).thenThrow(new SQLException("DB error"));

        try (MockedStatic<DatabaseConnector> dbMock = mockStatic(DatabaseConnector.class)) {
            dbMock.when(DatabaseConnector::getConnection).thenReturn(mockConn);
            new DBManager(); // блоку initialization достатньо
        }
    }

    @Test
    void testInitializeRecordsGenericException() throws Exception {
        Connection mockConn = mock(Connection.class);
        when(mockConn.createStatement()).thenThrow(new RuntimeException("Unexpected"));

        try (MockedStatic<DatabaseConnector> dbMock = mockStatic(DatabaseConnector.class)) {
            dbMock.when(DatabaseConnector::getConnection).thenReturn(mockConn);
            new DBManager();
        }
    }

    @Test
    void testInitializeRecordCollectionsSQLException() throws Exception {
        Connection mockConn = mock(Connection.class);
        when(mockConn.createStatement()).thenThrow(new SQLException("RC error"));

        try (MockedStatic<DatabaseConnector> dbMock = mockStatic(DatabaseConnector.class)) {
            dbMock.when(DatabaseConnector::getConnection).thenReturn(mockConn);
            new DBManager();
        }
    }

}
