package org.example.ui.scenesHelpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.models.Record;
import org.example.repository.DBTableManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TableControllerTest {

    private DBTableManager tableManager;
    private TableController tableController;

    // JavaFX компоненти
    private TableView<Record> tableView;
    private TableColumn<Record, Integer> idColumn;
    private TableColumn<Record, String> titleColumn;
    private TableColumn<Record, String> styleColumn;
    private TableColumn<Record, String> durationColumn;

    @BeforeEach
    void setUp() {
        // Ініціалізація JavaFX середовища
        new JFXPanel();

        tableManager = mock(DBTableManager.class);
        tableController = new TableController(tableManager, "dummy_table");

        tableView = new TableView<>();
        idColumn = new TableColumn<>();
        titleColumn = new TableColumn<>();
        styleColumn = new TableColumn<>();
        durationColumn = new TableColumn<>();
    }

    @Test
    void testInitialize_setsCellValueFactoriesAndLoadsData() {
        ObservableList<Record> mockData = FXCollections.observableArrayList();
        when(tableManager.selectAllFromTable("dummy_table")).thenReturn(mockData);

        tableController.initialize(tableView, idColumn, titleColumn, styleColumn, durationColumn);

        assertEquals(mockData, tableView.getItems());
    }

    @Test
    void testDisplayRecords_setsItemsToTableView() {
        ObservableList<Record> expected = FXCollections.observableArrayList();
        when(tableManager.selectAllFromTable("dummy_table")).thenReturn(expected);

        tableController.displayRecords(tableView);

        assertEquals(expected, tableView.getItems());
    }
}
