package ui.scenesHelpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Record;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.DBManager;
import repository.DBTableManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindControllerTest {

    private DBManager dbManager = new DBManager();
    private DBTableManager repository;
    private FindController findController;

    @BeforeEach
    void setUp() {
        repository = mock(DBTableManager.class);
        dbManager.createTable("test_table");
        findController = new FindController(repository, "test_table");
    }

    @Test
    void testFindBy_title() {
        ObservableList<Record> expected = FXCollections.observableArrayList();
        when(repository.findByParameter("test_table", "title", "Test")).thenReturn(expected);

        ObservableList<Record> result = findController.findBy("title", "Test");

        assertSame(expected, result);
        verify(repository).findByParameter("test_table", "title", "Test");
    }

    @Test
    void testFindBy_style() {
        ObservableList<Record> expected = FXCollections.observableArrayList();
        when(repository.findByParameter("test_table", "style", "Rock")).thenReturn(expected);

        ObservableList<Record> result = findController.findBy("style", "Rock");

        assertSame(expected, result);
        verify(repository).findByParameter("test_table", "style", "Rock");
    }

    @Test
    void testFindBy_duration() {
        ObservableList<Record> expected = FXCollections.observableArrayList();
        when(repository.findByDuration("test_table", "00:00:00", "03:30")).thenReturn(expected);

        ObservableList<Record> result = findController.findBy("duration", "03:30");

        assertSame(expected, result);
        verify(repository).findByDuration("test_table", "00:00:00", "03:30");
    }

    @Test
    void testFindBy_unknownParameter() {
        ObservableList<Record> expected = FXCollections.observableArrayList();
        when(repository.findByParameter("test_table", "title", "SomeValue")).thenReturn(expected);

        ObservableList<Record> result = findController.findBy("unknown", "SomeValue");

        assertSame(expected, result);
        verify(repository).findByParameter("test_table", "title", "SomeValue");
    }
}
