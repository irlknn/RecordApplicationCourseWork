package ui.scenesHelpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Record;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.DBTableManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SortControllerTest {

    private DBTableManager tableManager;
    private SortController sortController;

    @BeforeEach
    void setUp() {
        tableManager = mock(DBTableManager.class);
        sortController = new SortController(tableManager, "dummy_table");
    }

    @Test
    void testSortBy_title() {
        ObservableList<Record> expected = FXCollections.observableArrayList();
        when(tableManager.sortByParameter("dummy_table", "title")).thenReturn(expected);

        ObservableList<Record> result = sortController.sortBy("title");

        assertSame(expected, result);
        verify(tableManager).sortByParameter("dummy_table", "title");
    }

    @Test
    void testSortBy_duration() {
        ObservableList<Record> expected = FXCollections.observableArrayList();
        when(tableManager.sortByParameter("dummy_table", "duration")).thenReturn(expected);

        ObservableList<Record> result = sortController.sortBy("duration");

        assertSame(expected, result);
        verify(tableManager).sortByParameter("dummy_table", "duration");
    }

    @Test
    void testSortBy_style() {
        ObservableList<Record> expected = FXCollections.observableArrayList();
        when(tableManager.sortByParameter("dummy_table", "style")).thenReturn(expected);

        ObservableList<Record> result = sortController.sortBy("style");

        assertSame(expected, result);
        verify(tableManager).sortByParameter("dummy_table", "style");
    }
}
