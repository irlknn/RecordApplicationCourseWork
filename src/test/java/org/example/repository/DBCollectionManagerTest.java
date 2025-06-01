package org.example.repository;

import org.example.models.Collection;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DBCollectionManagerTest {

    private DBCollectionManager manager;

    @BeforeEach
    void setUp() {
        manager = new DBCollectionManager();
    }

    @Test
    void insertCollection_success() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(conn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(ps);
        when(ps.getGeneratedKeys()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(42);

        try (MockedStatic<DatabaseConnector> mocked = mockStatic(DatabaseConnector.class)) {
            mocked.when(DatabaseConnector::getConnection).thenReturn(conn);

            int id = manager.insertCollection("Test Collection");
            assertEquals(42, id);
            verify(ps).setString(1, "Test Collection");
        }
    }

    @Test
    void insertCollection_sqlException() throws Exception {
        try (MockedStatic<DatabaseConnector> mocked = mockStatic(DatabaseConnector.class)) {
            mocked.when(DatabaseConnector::getConnection).thenThrow(new SQLException("DB error"));

            int id = manager.insertCollection("Fail Collection");
            assertEquals(-1, id);
        }
    }

    @Test
    void getAllCollections_success() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getString("name")).thenReturn("Collection A");

        try (MockedStatic<DatabaseConnector> mocked = mockStatic(DatabaseConnector.class)) {
            mocked.when(DatabaseConnector::getConnection).thenReturn(conn);

            List<Collection> list = manager.getAllCollections();
            assertEquals(1, list.size());
            assertEquals("Collection A", list.get(0).getName());
        }
    }

    @Test
    void getCollectionNameById_found() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getString("name")).thenReturn("TestName");

        try (MockedStatic<DatabaseConnector> mocked = mockStatic(DatabaseConnector.class)) {
            mocked.when(DatabaseConnector::getConnection).thenReturn(conn);

            String name = manager.getCollectionNameById(10);
            assertEquals("TestName", name);
        }
    }

    @Test
    void getCollectionNameById_notFound() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        try (MockedStatic<DatabaseConnector> mocked = mockStatic(DatabaseConnector.class)) {
            mocked.when(DatabaseConnector::getConnection).thenReturn(conn);

            String name = manager.getCollectionNameById(99);
            assertEquals("", name);
        }
    }

    @Test
    void deleteCollection_success() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        when(conn.prepareStatement(anyString())).thenReturn(ps);

        try (MockedStatic<DatabaseConnector> mocked = mockStatic(DatabaseConnector.class)) {
            mocked.when(DatabaseConnector::getConnection).thenReturn(conn);

            assertDoesNotThrow(() -> manager.deleteCollection(5));
            verify(ps).setInt(1, 5);
        }
    }
}
