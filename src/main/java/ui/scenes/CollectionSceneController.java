package ui.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import models.Record;
import repository.DBTableManager;
import ui.scenesHelpers.FindController;
import ui.scenesHelpers.SceneController;
import ui.scenesHelpers.SortController;
import ui.scenesHelpers.TableController;
import utils.CollectionService;

import java.net.URL;
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
    TableView<Record> tableView;
    @FXML
    TableColumn<Record, Integer> idColumn;
    @FXML
    TableColumn<Record, String> titleColumn;
    @FXML
    TableColumn<Record, String> styleColumn;
    @FXML
    TableColumn<Record, String> durationColumn;
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

    public CollectionSceneController() {
    }

    public void initialize(String tableName, DBTableManager tableManager, TableController tableController, CollectionService collectionService) {
        this.tableName = tableName;
        this.tableManager = tableManager;
        this.tableController = tableController;
        this.collectionService = collectionService;

        tableNameLabel.setText(tableName);
        collectionDurationLabel.setText(collectionService.collectionDuration());
        tableController.initialize(tableView, idColumn, titleColumn, styleColumn, durationColumn);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        this is called by fxml automatically
        sortChoiceBox.getItems().addAll(filterParameters);
        sortChoiceBox.setOnAction(this::selectSortBy);

        findChoiceBox.getItems().addAll(filterParameters);
        findChoiceBox.setOnAction(this::selectFindBy);
    }


    public void clickOnAddButton(ActionEvent e) {
        SceneController sceneController = new SceneController();
        sceneController.goToCreateRecordPane(e, tableManager, tableName);
        refreshCollectionDuration();
    }

    public void clickOnDeleteButton(ActionEvent e) {
        Record selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return; // або покажи попередження користувачу
        }
        tableView.getItems().remove(selected);
        tableManager.deleteFromTableById(selected.getId(), tableName);
        refreshCollectionDuration();
    }

    public void selectSortBy(ActionEvent e) {
        SortController sortController = new SortController(tableManager, tableName);
        String parameter = sortChoiceBox.getValue();
        tableView.setItems(sortController.sortBy(parameter));
    }

    public void selectFindBy(ActionEvent e) {
        FindController findController = new FindController(tableManager, tableName);
        String parameter = findChoiceBox.getValue();
        tableView.setItems(findController.findBy(parameter, enterField.getText()));
    }

    public void clickOnSearchButton(ActionEvent e) {
        FindController findController = new FindController(tableManager, tableName);
        tableView.setItems(findController.findBy("title", enterField.getText()));
    }

    public void clearFilters() {
        tableController.displayRecords(tableView);
    }

    public void back(ActionEvent e) {
        SceneController sceneController = new SceneController();
        sceneController.goBackToMainScene(e);
    }

    private void refreshCollectionDuration() {
        this.collectionService = new CollectionService(tableManager.selectAllFromTable(tableName));
        collectionDurationLabel.setText(collectionService.collectionDuration());
    }

    public void setTableName(String testTable) {
        this.tableName = testTable;
    }
}