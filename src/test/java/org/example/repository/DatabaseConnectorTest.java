package org.example.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectorTest {

    private DatabaseConnector connector;

    @BeforeEach
    void setUp() {
        connector = new DatabaseConnector();
        connector.setTestURL();
    }

    @Test
    void testConnectionNotNullAndOpen() throws SQLException {
        try (Connection conn = connector.getConnection()) {
            assertNotNull(conn, "Connection should not be null");
            assertFalse(conn.isClosed(), "Connection should be open");
        }
    }

    @Test
    void testCreateAndQueryTable() throws SQLException {
        try (Connection conn = connector.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE test (id INTEGER PRIMARY KEY, name TEXT)");
            stmt.execute("INSERT INTO test (name) VALUES ('TestUser')");

            ResultSet rs = stmt.executeQuery("SELECT name FROM test");
            assertTrue(rs.next());
            assertEquals("TestUser", rs.getString("name"));
        }
    }
}
