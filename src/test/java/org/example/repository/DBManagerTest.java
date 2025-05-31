package org.example.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DBManagerTest {

    private DBManager dbManager;

    @BeforeEach
    void setUp() {
        try (MockedStatic<DatabaseConnector> mocked = mockStatic(DatabaseConnector.class)) {
            Connection mockConnection = mock(Connection.class);
            Statement mockStatement = mock(Statement.class);
            try {
                when(mockConnection.createStatement()).thenReturn(mockStatement);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            mocked.when(DatabaseConnector::getConnection).thenReturn(mockConnection);

            dbManager = new DBManager(); // Тестує initializeDB()
        }
    }

    @Test
    void testCreateTable_Success() throws Exception {
        String tableName = "valid_table";

        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);

        when(mockConnection.createStatement()).thenReturn(mockStatement);

        try (MockedStatic<DatabaseConnector> mocked = mockStatic(DatabaseConnector.class)) {
            mocked.when(DatabaseConnector::getConnection).thenReturn(mockConnection);

            dbManager.createTable(tableName);

            verify(mockStatement).execute(anyString());
        }
    }

    @Test
    void testCreateTable_InvalidTableName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> dbManager.createTable("123invalid"));
    }

    @Test
    void testCreateTable_SQLException() throws Exception {
        String tableName = "valid_table";

        Connection mockConnection = mock(Connection.class);
        when(mockConnection.createStatement()).thenThrow(new SQLException("DB error"));

        try (MockedStatic<DatabaseConnector> mocked = mockStatic(DatabaseConnector.class)) {
            mocked.when(DatabaseConnector::getConnection).thenReturn(mockConnection);

            dbManager.createTable(tableName); // має впасти в catch-блок
        }
    }

    @Test
    void testDeleteTable_Success() throws Exception {
        String tableName = "test_table";

        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);

        when(mockConnection.createStatement()).thenReturn(mockStatement);

        try (MockedStatic<DatabaseConnector> mocked = mockStatic(DatabaseConnector.class)) {
            mocked.when(DatabaseConnector::getConnection).thenReturn(mockConnection);

            dbManager.deleteTable(tableName);

            verify(mockStatement).execute("DROP TABLE IF EXISTS " + tableName);
        }
    }

    @Test
    void testDeleteTable_SQLException() throws Exception {
        String tableName = "test_table";

        Connection mockConnection = mock(Connection.class);
        when(mockConnection.createStatement()).thenThrow(new SQLException("Drop error"));

        try (MockedStatic<DatabaseConnector> mocked = mockStatic(DatabaseConnector.class)) {
            mocked.when(DatabaseConnector::getConnection).thenReturn(mockConnection);

            dbManager.deleteTable(tableName); // має впасти в catch-блок
        }
    }

    @Test
    void testInitializeDB_SQLException() throws Exception {
        try (MockedStatic<DatabaseConnector> mocked = mockStatic(DatabaseConnector.class)) {
            Connection mockConnection = mock(Connection.class);
            when(mockConnection.createStatement()).thenThrow(new SQLException("Init error"));

            mocked.when(DatabaseConnector::getConnection).thenReturn(mockConnection);

            new DBManager(); // повинен логувати помилку ініціалізації
        }
    }

    @Test
    void testInitializeDB_GenericException() throws Exception {
        try (MockedStatic<DatabaseConnector> mocked = mockStatic(DatabaseConnector.class)) {
            mocked.when(DatabaseConnector::getConnection).thenThrow(new RuntimeException("Generic error"));

            new DBManager(); // ловиться загальний Exception
        }
    }
}
