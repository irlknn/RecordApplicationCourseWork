package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DBManagerTest {

    private DBManager dbManager;

    @BeforeEach
    void setUp() {
        dbManager = new DBManager();
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
}
