package org.example.ui.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.collections.ObservableList;
import org.example.models.Record;
import org.example.repository.DBTableManager;
import org.example.ui.scenesHelpers.FindController;
import org.example.ui.scenesHelpers.SceneController;
import org.example.ui.scenesHelpers.SortController;
import org.example.ui.scenesHelpers.TableController;
import org.example.utils.CollectionService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CollectionSceneController implements Initializable {
    private final String[] filterParameters = {"title", "style", "duration"};

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
    private Button deleteButton;
    @FXML
    private Button exitButton;

    private String tableName;
    private DBTableManager tableManager;
    private TableController tableController;
    private CollectionService collectionService;
    private Record selectedRecord;

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

        if (records.isEmpty()) {
            showEmptyState();
            return;
        }

        for (Record record : records) {
            VBox recordCard = createRecordCard(record);
            recordsContainer.getChildren().add(recordCard);
        }
    }

    private void showEmptyState() {
        VBox emptyState = new VBox(20);
        emptyState.setAlignment(Pos.CENTER);
        emptyState.setPadding(new Insets(60));
        emptyState.getStyleClass().add("empty-state-container");

        Label emptyIcon = new Label("📀");
        emptyIcon.getStyleClass().add("empty-state-icon");

        Label emptyTitle = new Label("No Records Found");
        emptyTitle.getStyleClass().add("empty-state-title");

        Label emptyDescription = new Label("Start by adding your first record to this collection");
        emptyDescription.getStyleClass().add("empty-state-description");
        emptyDescription.setWrapText(true);

        emptyState.getChildren().addAll(emptyIcon, emptyTitle, emptyDescription);
        recordsContainer.getChildren().add(emptyState);
    }

    private VBox createRecordCard(Record record) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.getStyleClass().add("record-card");

        // Add hover and selection effects
        card.setOnMouseEntered(e -> {
            if (selectedRecord != record) {
                card.getStyleClass().remove("record-card");
                card.getStyleClass().add("record-card-hover");
            }
        });

        card.setOnMouseExited(e -> {
            if (selectedRecord != record) {
                card.getStyleClass().remove("record-card-hover");
                card.getStyleClass().add("record-card");
            }
        });

        // Click handler for selection
        card.setOnMouseClicked(e -> {
            selectRecord(record, card);
        });

        // Header with ID and duration
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("record-card-header");

        Label idBadge = new Label("#" + record.getId());
        idBadge.getStyleClass().add("record-id-badge");

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        Label durationLabel = new Label("⏱ " + record.getDuration());
        durationLabel.getStyleClass().add("record-duration-label");

        header.getChildren().addAll(idBadge, spacer, durationLabel);

        // Title
        Label titleLabel = new Label(record.getTitle());
        titleLabel.getStyleClass().add("record-title-label");
        titleLabel.setWrapText(true);

        // Style with icon
        HBox styleBox = new HBox(8);
        styleBox.setAlignment(Pos.CENTER_LEFT);
        styleBox.getStyleClass().add("record-style-box");

        Label styleIcon = new Label("🎵");
        styleIcon.getStyleClass().add("record-style-icon");

        Label styleLabel = new Label(record.getStyle());
        styleLabel.getStyleClass().add("record-style-label");

        styleBox.getChildren().addAll(styleIcon, styleLabel);

        card.getChildren().addAll(header, titleLabel, styleBox);

        return card;
    }

    private void selectRecord(Record record, VBox card) {
        // Clear previous selection
        if (selectedRecord != null) {
            recordsContainer.getChildren().forEach(node -> {
                if (node instanceof VBox) {
                    node.getStyleClass().clear();
                    node.getStyleClass().add("record-card");
                }
            });
        }

        // Set new selection
        selectedRecord = record;
        card.getStyleClass().clear();
        card.getStyleClass().add("record-card-selected");

        System.out.println("Selected record: " + record.getTitle());
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
}