package commands;

import models.Record;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.DBTableManager;

import java.time.LocalTime;

import static org.mockito.Mockito.*;

class AddItemTest {

    private DBTableManager repository;
    private Record record;
    private AddItem addItemCommand;

    private String tableName = "test";

    @BeforeEach
    public void setUp() {
        repository = mock(DBTableManager.class);  // створюємо мок репозиторію
        record = new Record();  // створюємо простий запис
        record.setTitle("Test Title");
        record.setStyle("Rock");
        record.setDuration(LocalTime.of(0, 3, 30));

        addItemCommand = new AddItem(repository, record, tableName);
    }

    @Test
    public void testExecuteCallsSaveToDB() {
        // Виконуємо команду
        addItemCommand.execute();

        // Перевіряємо, що метод saveToDB викликався з потрібним записом і ім'ям таблиці
        verify(repository, times(1)).insertIntoTable(record, tableName);
    }
}
