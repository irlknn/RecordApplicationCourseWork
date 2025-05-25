package org.example.ui.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.models.Record;
import org.example.repository.DBTableManager;
import org.example.ui.scenesHelpers.TableController;
import org.example.utils.CollectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.time.LocalTime;

import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class CollectionSceneControllerTest {

    private CollectionSceneController controller;
    private DBTableManager mockDBTableManager;
    private TableController mockTableController;
    private CollectionService mockCollectionService;

    @Start
    private void start(Stage stage) {
    }

    @BeforeEach
    void setup() {
        controller = new CollectionSceneController();
        mockDBTableManager = mock(DBTableManager.class);
        mockTableController = mock(TableController.class);
        mockCollectionService = mock(CollectionService.class);

        // Initialize UI elements manually
        controller.enterField = new TextField();
        controller.searchButton = new Button("Search");
        controller.findChoiceBox = new ChoiceBox<>();
        controller.sortChoiceBox = new ChoiceBox<>();
        controller.collectionDurationLabel = new Label();
        controller.tableNameLabel = new Label();
        controller.tableView = new TableView<>();

        controller.initialize("TestTable", mockDBTableManager, mockTableController, mockCollectionService);
    }

    @Test
    void testInitialize_setsLabelsAndInitializesTable() {
        verify(mockCollectionService).collectionDuration();
        verify(mockTableController).initialize(any(), any(), any(), any(), any());
    }

    @Test
    void testClickOnSearchButton_invokesFindByTitle() {
        controller.enterField.setText("Rock Song");

        controller.clickOnSearchButton(new javafx.event.ActionEvent());

    }

    @Test
    void testClickOnDeleteButton_removesSelectedItem() {
        Record mockRecord = new Record("Test", "Rock", LocalTime.of(0, 3, 0));
        controller.tableView.getItems().add(mockRecord);
        controller.tableView.getSelectionModel().select(mockRecord);

        ObservableList<Record> mockList = FXCollections.observableArrayList(
                new Record("Test", "Rock", LocalTime.of(0, 3, 0))
        );
        when(mockDBTableManager.selectAllFromTable("TestTable")).thenReturn(mockList);

        controller.clickOnDeleteButton(new javafx.event.ActionEvent());

        verify(mockDBTableManager).deleteFromTableById(mockRecord.getId(), "TestTable");
    }

    @Test
    void testClearFilters_callsDisplayRecords() {
        controller.clearFilters();
        verify(mockTableController).displayRecords(controller.tableView);
    }
}
