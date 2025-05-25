package org.example.ui.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.repository.DBTableManager;
import org.example.ui.scenesHelpers.TableController;
import org.example.utils.CollectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.mockito.Mockito.*;

public class CollectionSceneControllerFXTest extends ApplicationTest {

    private CollectionSceneController controller;
    private DBTableManager mockDBTableManager;
    private CollectionService mockCollectionService;
    private TableController mockTableController;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ui/CollectionScene.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        mockDBTableManager = mock(DBTableManager.class);
        mockCollectionService = mock(CollectionService.class);
        mockTableController = mock(TableController.class);

        controller.initialize("testTable", mockDBTableManager, mockTableController, mockCollectionService);

        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    void setUpMocks() {
        when(mockCollectionService.collectionDuration()).thenReturn("1h 20m");
    }

    @Test
    void testClickSearchButton() {
        clickOn("#enterField").write("test");
        clickOn("#findChoiceBox").clickOn("title");
        clickOn("#searchButton");
    }

    @Test
    void testClickDeleteButtonWithoutSelection() {
        clickOn("#deleteButton");
        verify(mockDBTableManager, never()).deleteFromTableById(anyInt(), anyString());
    }

    @Test
    void testExitButton() {
        clickOn("#exitButton");
    }
}


//package org.example.ui.scenes;
//
//import javafx.collections.FXCollections;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.stage.Stage;
//import models.Record;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.testfx.api.FxAssert;
//import org.testfx.api.FxRobot;
//import org.testfx.framework.junit5.ApplicationExtension;
//import org.testfx.framework.junit5.Start;
//import org.testfx.matcher.control.LabeledMatchers;
//import org.example.repository.DBTableManager;
//import org.example.ui.scenesHelpers.TableController;
//import org.example.utils.CollectionService;
//
//import java.io.IOException;
//import java.time.LocalTime;
//import java.util.Arrays;
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(ApplicationExtension.class)
//public class CollectionSceneControllerFXTest {
//
//    @Mock
//    private DBTableManager mockTableManager;
//    @Mock
//    private TableController mockTableController;
//    @Mock
//    private CollectionService mockCollectionService;
//
//    private CollectionSceneController controller;
//
//    private Stage primaryStage;
//    private String  tableName = "testTable";
//
//    @Start
//    public void start(Stage stage) throws IOException {
//        MockitoAnnotations.openMocks(this);
//
//        // Debugging line:
//        java.net.URL fxmlLocation = getClass().getResource("/org.example.ui/scenes/CollectionScene.fxml");
//        if (fxmlLocation == null) {
//            System.err.println("ERROR: FXML file not found! Check path: /org.example.ui/scenes/CollectionScene.fxml");
//            // You can even throw a more specific exception here if needed
//        } else {
//            System.out.println("FXML file found at: " + fxmlLocation);
//        }
//
//        FXMLLoader loader = new FXMLLoader(fxmlLocation); // Use the URL directly
//        Parent root = loader.load();
//        controller = loader.getController();
//
//        controller.initialize(tableName, mockTableManager, mockTableController, mockCollectionService);
//
//        stage.setScene(new Scene(root));
//        stage.show();
//        stage.toFront();
//        this.primaryStage = stage;
//    }
//
//    @BeforeEach
//    public void setUp() {
//        reset(mockTableManager, mockTableController, mockCollectionService);
//
//        when(mockCollectionService.collectionDuration()).thenReturn("00:00:00");
//        when(mockTableManager.selectAllFromTable(anyString())).thenReturn(FXCollections.observableArrayList());
//    }
//
//    @Test
//    void testInitialState(FxRobot robot) {
//        FxAssert.verifyThat("#tableNameLabel", LabeledMatchers.hasText(tableName));
//        FxAssert.verifyThat("#collectionDurationLabel", LabeledMatchers.hasText("00:00:00"));
//
//        verify(mockTableController).initialize(any(TableView.class), any(TableColumn.class), any(TableColumn.class), any(TableColumn.class), any(TableColumn.class));
//
//        ChoiceBox<String> findChoiceBox = robot.lookup("#findChoiceBox").queryAs(ChoiceBox.class);
//        assertEquals(Arrays.asList("title", "style", "duration"), findChoiceBox.getItems());
//
//        ChoiceBox<String> sortChoiceBox = robot.lookup("#sortChoiceBox").queryAs(ChoiceBox.class);
//        assertEquals(Arrays.asList("title", "style", "duration"), sortChoiceBox.getItems());
//    }
//
//    @Test
//    void testClickOnAddButton(FxRobot robot) {
//
//        robot.clickOn("#addButton");
//
//        verify(mockCollectionService, atLeastOnce()).collectionDuration();
//    }
//
//    @Test
//    void testClickOnDeleteButton(FxRobot robot) {
//        Record recordToDelete = new Record("Song Title", "Pop", LocalTime.of(0, 3, 0));
//        TableView<Record> tableView = robot.lookup("#tableView").queryAs(TableView.class);
//        tableView.getItems().add(recordToDelete); // Add a record to select
//
//        // Select the record
//        robot.interact(() -> tableView.getSelectionModel().select(recordToDelete));
//
//        // Click the delete button
//        robot.clickOn("#deleteButton");
//
//        // Verify that the record is removed from the TableView
//        assertEquals(0, tableView.getItems().size());
//
//        // Verify that deleteFromTableById was called on the mock DBTableManager
//        verify(mockTableManager).deleteFromTableById(recordToDelete.getId(), tableName);
//
//        // Verify that refreshCollectionDuration was called
//        verify(mockCollectionService, atLeastOnce()).collectionDuration();
//    }
//
//    @Test
//    void testSelectSortBy(FxRobot robot) {
//        TableView<Record> tableView = robot.lookup("#tableView").queryAs(TableView.class);
//
//        // Mock the behavior of sortBy
//        when(mockTableManager.selectAllFromTable(anyString())).thenReturn(FXCollections.observableArrayList(
//                new Record("B", "Pop", LocalTime.of(0, 3, 0)),
//                new Record("A", "Rock", LocalTime.of(0, 4, 0))
//        ));
//        when(mockTableManager.selectAllFromTable(tableName))
//                .thenReturn(FXCollections.observableArrayList(
//                        new Record("A", "Rock", LocalTime.of(0, 4, 0)),
//                        new Record("B", "Pop", LocalTime.of(0, 3, 0))
//                ));
//
//        // Select "title" from the sort ChoiceBox
//        robot.clickOn("#sortChoiceBox");
//        robot.clickOn("title");
//
//        verify(mockTableManager).selectAllFromTable(tableName);
//        assertEquals("A", tableView.getItems().get(0).getTitle());
//        assertEquals("B", tableView.getItems().get(1).getTitle());
//    }
//
//    @Test
//    void testSelectFindBy(FxRobot robot) {
//        TableView<Record> tableView = robot.lookup("#tableView").queryAs(TableView.class);
//
//        // Mock the behavior of findBy
//        when(mockTableManager.selectAllFromTable(tableName))
//                .thenReturn(FXCollections.observableArrayList(new Record("Found Song", "Pop", LocalTime.of(0, 3, 0))));
//
//        // Enter text into the search field
//        robot.clickOn("#enterField"); // Click to focus the TextField
//        robot.write("Found Song");
//
//        // Select "title" from the find ChoiceBox
//        robot.clickOn("#findChoiceBox");
//        robot.clickOn("title");
//
//        // Verify that the TableView items are updated based on the found data
//        verify(mockTableManager).selectAllFromTable(tableName);
//        assertEquals(1, tableView.getItems().size());
//        assertEquals("Found Song", tableView.getItems().get(0).getTitle());
//    }
//
//    @Test
//    void testClickOnSearchButton(FxRobot robot) {
//        TableView<Record> tableView = robot.lookup("#tableView").queryAs(TableView.class);
//
//        when(mockTableManager.selectAllFromTable(tableName)) // This is the method invocation
//                .thenReturn(FXCollections.observableArrayList());
//
//        // Enter text into the search field
//        robot.clickOn("#enterField"); // Click to focus the TextField
//        robot.write("Searched Song");
//
//        // Click the search button
//        robot.clickOn("#searchButton");
//
//        // Verify that the TableView items are updated
//        verify(mockTableManager).selectAllFromTable(tableName);
//        assertEquals(1, tableView.getItems().size());
//        assertEquals("Searched Song", tableView.getItems().get(0).getTitle());
//    }
//
//    @Test
//    void testClearFilters(FxRobot robot) {
//        // Populate the table with some initial filtered data
//        TableView<Record> tableView = robot.lookup("#tableView").queryAs(TableView.class);
//        tableView.getItems().add(new Record("Filtered", "Jazz", LocalTime.of(0, 2, 0)));
//
//        // Mock displayRecords to clear the table
//        doNothing().when(mockTableController).displayRecords(any(TableView.class));
//
//        // Call clearFilters directly
//        robot.interact(() -> controller.clearFilters());
//
//        // Verify that displayRecords was called, which should clear the table
//        verify(mockTableController).displayRecords(tableView);
//    }
//
//    @Test
//    void testBack(FxRobot robot) {
//        robot.clickOn("#exitButton"); // Assuming exitButton calls back()
//    }
//
//    @Test
//    void testRefreshCollectionDuration(FxRobot robot) {
//        FxAssert.verifyThat("#collectionDurationLabel", LabeledMatchers.hasText("00:00:00"));
//
//        when(mockTableManager.selectAllFromTable(anyString()))
//                .thenReturn(FXCollections.observableArrayList(new Record("A", "B", LocalTime.of(0, 1, 0))));
//        when(mockCollectionService.collectionDuration()).thenReturn("00:01:00"); // For the refresh
//
//        robot.interact(() -> {
//            try {
//                java.lang.reflect.Method method = CollectionSceneController.class.getDeclaredMethod("refreshCollectionDuration");
//                method.setAccessible(true);
//                method.invoke(controller);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        // Verify the label is updated
//        FxAssert.verifyThat("#collectionDurationLabel", LabeledMatchers.hasText("00:01:00"));
//        verify(mockCollectionService, atLeast(2)).collectionDuration(); // Initial + refresh
//    }
//}