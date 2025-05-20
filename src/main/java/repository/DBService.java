package repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBService {
    private static final Logger logger = LogManager.getLogger(DBService.class);

    public static void validateTableName(String tableName) {
        if (tableName == null || tableName.trim().isEmpty()) {
            logger.error("Table name cannot be null or empty");
            throw new IllegalArgumentException();
        }

        if (!tableName.matches("^[a-zA-Z_][a-zA-Z0-9_]*$")) {
            logger.error("Invalid table name: {}", tableName);
            throw new IllegalArgumentException();
        }
        // Prevent SQL injection
        String[] forbiddenNames = {"SELECT", "INSERT", "DELETE", "DROP", "CREATE", "ALTER", "TABLE"};
        for (String forbidden : forbiddenNames) {
            if (tableName.toUpperCase().equals(forbidden)) {
                logger.error("Table name cannot be a SQL keyword: {}", tableName);
                throw new IllegalArgumentException();
            }
        }
    }
}
