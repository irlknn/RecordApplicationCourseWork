package commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.DBTableManager;

import static org.mockito.Mockito.*;

class ShowItemTest {
    private DBTableManager repository;

    @BeforeEach
    public void setUp() {
        repository = mock(DBTableManager.class);
    }

    @Test
    public void testExecute_CallsSelectAllFromDB() {
        ShowItem showItem = new ShowItem(repository);
        showItem.execute();

        // Перевіряємо, що метод selectAllFromDB був викликаний з аргументом "records"
        verify(repository, times(1)).selectAllFromTable("records");
    }
}