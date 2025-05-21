package repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DBServiceTest {

    @Test
    void testValidateTableName_Valid() {
        assertDoesNotThrow(() -> DBService.validateTableName("my_table"));
    }

    @Test
    void testValidateTableName_Null_Throws() {
        assertThrows(IllegalArgumentException.class, () -> DBService.validateTableName(null));
    }

    @Test
    void testValidateTableName_Empty_Throws() {
        assertThrows(IllegalArgumentException.class, () -> DBService.validateTableName("   "));
    }

    @Test
    void testValidateTableName_InvalidChars_Throws() {
        assertThrows(IllegalArgumentException.class, () -> DBService.validateTableName("my-table!"));
    }

    @Test
    void testValidateTableName_SQLKeyword_Throws() {
        assertThrows(IllegalArgumentException.class, () -> DBService.validateTableName("DROP"));
    }
}
