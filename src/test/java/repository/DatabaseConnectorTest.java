package repository;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectorTest {

    @Test
    void testGetConnection() {
        try (Connection conn = DatabaseConnector.getConnection()) {
            assertNotNull(conn);
            assertFalse(conn.isClosed());
        } catch (Exception e) {
            fail("Failed to get DB connection: " + e.getMessage());
        }
    }
}
