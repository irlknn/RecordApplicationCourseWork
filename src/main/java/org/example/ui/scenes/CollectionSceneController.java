package org.example.ui.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import org.example.models.Record;
import org.example.repository.DBCollectionManager;
import org.example.repository.DBRecordCollectionManager;
import org.example.repository.DBRecordManager;
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

    private DBRecordCollectionManager recordCollectionManager = new DBRecordCollectionManager();
    private DBCollectionManager collectionManager = new DBCollectionManager();
    private DBRecordManager recordManager = new DBRecordManager();
    private CollectionService collectionService;
    Record selectedRecord;
    private int collectionId;

    public CollectionSceneController() {
    }

    public void initialize(int collectionId) {
        this.collectionId = collectionId;
        this.collectionService = new CollectionService(recordManager.getRecordsByCollectionId(collectionId));

        tableNameLabel.setText(collectionManager.getCollectionNameById(collectionId));
        collectionDurationLabel.setText(collectionService.collectionDuration());

        displayRecords(recordManager.getRecordsByCollectionId(collectionId));
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
            VBox recordCard = card.createRecordCard(record);

            if (selectedRecord != null && selectedRecord.getId() == record.getId()) {
                recordCard.getStyleClass().clear();
                recordCard.getStyleClass().add("record-card-selected");
            } else {
                recordCard.getStyleClass().clear();
                recordCard.getStyleClass().add("record-card");
            }

            recordCard.setOnMouseClicked(event -> {
                selectedRecord = record;
                refreshDisplay();
            } );

            recordsContainer.getChildren().add(recordCard);
        }
    }

    public void clickOnAddButton(ActionEvent e) {
        SceneController sceneController = new SceneController();
        sceneController.goToCreateRecordPane(e, collectionId);
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
                recordManager.deleteRecord(selectedRecord.getId());
                selectedRecord = null;
                refreshDisplay();
                refreshCollectionDuration();
            }
        });
    }

    public void selectSortBy(ActionEvent e) {
        SortController sortController = new SortController(recordCollectionManager, collectionId);
        String parameter = sortChoiceBox.getValue();
        if (parameter != null) {
            ObservableList<Record> sortedRecords = sortController.sortBy(parameter);
            displayRecords(sortedRecords);
        }
    }

    public void selectFindBy(ActionEvent e) {
        FindController findController = new FindController(recordCollectionManager, collectionId);
        String parameter = findChoiceBox.getValue();
        String searchText = enterField.getText();

        if (parameter != null && !searchText.isEmpty()) {
            ObservableList<Record> foundRecords = findController.findBy(parameter, searchText);
            displayRecords(foundRecords);
        }
    }

    public void clickOnSearchButton(ActionEvent e) {
        FindController findController = new FindController(recordCollectionManager, collectionId);
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
        this.collectionService = new CollectionService(recordManager.getRecordsByCollectionId(collectionId));
        collectionDurationLabel.setText(collectionService.collectionDuration());
    }

    private void refreshDisplay() {
        List<Record> allRecords = recordManager.getRecordsByCollectionId(collectionId);
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
        authorLabel.setText("Author:");
        descriptionLabel.setText("Description:");
        moreInfoBanner.setVisible(true);
        moreInfoBanner.setManaged(true);
    }

    public void handleCloseMoreInfo(ActionEvent event) {
        moreInfoBanner.setVisible(false);
        moreInfoBanner.setManaged(false);
    }


}
