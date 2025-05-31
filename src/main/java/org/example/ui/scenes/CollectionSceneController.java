package org.example.ui.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import org.example.models.Record;
import org.example.repository.DBTableManager;
import org.example.ui.scenesHelpers.*;
import org.example.utils.CollectionService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CollectionSceneController implements Initializable {
    private final String[] filterParameters = {"title", "style", "duration"};
    public TableView<Record> tableView;

    @FXML
    TextField enterField;
    @FXML
    Button searchButton;
    @FXML
    Label tableNameLabel;
    @FXML
    Label collectionDurationLabel;
    @FXML
    ChoiceBox<String> findChoiceBox;
    @FXML
    ChoiceBox<String> sortChoiceBox;
    @FXML
    AnchorPane scenePane;
    @FXML
    VBox recordsContainer;
    @FXML
    ScrollPane recordsScrollPane;
    @FXML
    private Button addButton;
    @FXML
    Button deleteButton;
    @FXML
    private Button exitButton;

    String tableName;
    private DBTableManager tableManager;
    private TableController tableController;
    private CollectionService collectionService;
    Record selectedRecord;

    public CollectionSceneController() {
    }

    public void initialize(String tableName, DBTableManager tableManager,
                           TableController tableController, CollectionService collectionService) {
        this.tableName = tableName;
        this.tableManager = tableManager;
        this.tableController = tableController;
        this.collectionService = collectionService;

        tableNameLabel.setText(tableName);
        collectionDurationLabel.setText(collectionService.collectionDuration());

        // Load and display records
        displayRecords(tableManager.selectAllFromTable(tableName));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize choice boxes
        sortChoiceBox.getItems().addAll(filterParameters);
        sortChoiceBox.setOnAction(this::selectSortBy);

        findChoiceBox.getItems().addAll(filterParameters);
        findChoiceBox.setOnAction(this::selectFindBy);

        // Configure scroll pane
        recordsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        recordsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        recordsScrollPane.setFitToWidth(true);
    }

    private void displayRecords(List<Record> records) {
        recordsContainer.getChildren().clear();
        RecordCard card = new RecordCard();
        card.setRecordContainer(recordsContainer);

        if (records.isEmpty()) {
            card.showEmptyState(recordsContainer);
            return;
        }

        for (Record record : records) {
            VBox recordCard = card.createRecordCard(record, selectedRecord);
            recordsContainer.getChildren().add(recordCard);
        }
    }

    public void clickOnAddButton(ActionEvent e) {
        SceneController sceneController = new SceneController();
        sceneController.goToCreateRecordPane(e, tableManager, tableName);
        refreshCollectionDuration();
    }

    public void clickOnDeleteButton(ActionEvent e) {
        if (selectedRecord == null) {
            showAlert("No Selection", "Please select a record to delete.");
            return;
        }

        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Record");
        alert.setHeaderText("Delete \"" + selectedRecord.getTitle() + "\"?");
        alert.setContentText("This action cannot be undone.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                tableManager.deleteFromTableById(selectedRecord.getId(), tableName);
                selectedRecord = null;
                refreshDisplay();
                refreshCollectionDuration();
            }
        });
    }

    public void selectSortBy(ActionEvent e) {
        SortController sortController = new SortController(tableManager, tableName);
        String parameter = sortChoiceBox.getValue();
        if (parameter != null) {
            ObservableList<Record> sortedRecords = sortController.sortBy(parameter);
            displayRecords(sortedRecords);
        }
    }

    public void selectFindBy(ActionEvent e) {
        FindController findController = new FindController(tableManager, tableName);
        String parameter = findChoiceBox.getValue();
        String searchText = enterField.getText();

        if (parameter != null && !searchText.isEmpty()) {
            ObservableList<Record> foundRecords = findController.findBy(parameter, searchText);
            displayRecords(foundRecords);
        }
    }

    public void clickOnSearchButton(ActionEvent e) {
        FindController findController = new FindController(tableManager, tableName);
        String searchText = enterField.getText();

        if (!searchText.isEmpty()) {
            ObservableList<Record> foundRecords = findController.findBy("title", searchText);
            displayRecords(foundRecords);
        } else {
            clearFilters();
        }
    }

    public void clearFilters() {
        selectedRecord = null;
        refreshDisplay();
        enterField.clear();
        sortChoiceBox.setValue(null);
        findChoiceBox.setValue(null);
    }

    public void back(ActionEvent e) {
        SceneController sceneController = new SceneController();
        sceneController.goBackToMainScene(e);
    }

    private void refreshCollectionDuration() {
        this.collectionService = new CollectionService(tableManager.selectAllFromTable(tableName));
        collectionDurationLabel.setText(collectionService.collectionDuration());
    }

    private void refreshDisplay() {
        List<Record> allRecords = tableManager.selectAllFromTable(tableName);
        displayRecords(allRecords);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private AnchorPane moreInfoBanner;
    @FXML
    private Label authorLabel;
    @FXML
    private Label descriptionLabel;

    public void handleMoreButtonClick(ActionEvent event) {
        // Example: populate and show the banner
        authorLabel.setText("Author: John Doe");
        descriptionLabel.setText("Description: A beautiful instrumental...");
        moreInfoBanner.setVisible(true);
        moreInfoBanner.setManaged(true); // ensures it takes layout space
    }

    public void handleCloseMoreInfo(ActionEvent event) {
        moreInfoBanner.setVisible(false);
        moreInfoBanner.setManaged(false); // collapses it in the layout
    }


}
