package commands;

import models.Record;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repository.DBTableManager;

import static org.mockito.Mockito.*;

public class DeleteItemTest {

    private DBTableManager repository;
    private Record record;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(DBTableManager.class);
        record = new Record();
        record.setId(42); // або будь-який інший setter чи конструктор
    }

    @Test
    public void testExecute_DeletesRecord_WhenRecordIsNotNull() {
        DeleteItem deleteItem = new DeleteItem(repository, record);
        deleteItem.execute();

        // перевіряємо, що deleteById викликається з правильними параметрами
        verify(repository, times(1)).deleteFromTableById(42, "records");
    }

    @Test
    public void testExecute_DoesNothing_WhenRecordIsNull() {
        DeleteItem deleteItem = new DeleteItem(repository, null);
        deleteItem.execute();

        // перевіряємо, що метод deleteById не викликається взагалі
        verify(repository, never()).deleteFromTableById(anyInt(), anyString());
    }
}
