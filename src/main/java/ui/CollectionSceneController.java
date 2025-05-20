package ui;

import commands.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import repository.DBTableManager;
import models.Record;
import ui.serviceController.FindController;
import ui.serviceController.SceneController;
import ui.serviceController.SortController;
import ui.serviceController.TableController;
import utils.CollectionService;

import java.net.URL;
import java.util.ResourceBundle;


public class CollectionSceneController implements Initializable {
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button exitButton;

    @FXML private TextField enterField;
    @FXML private Button searchButton;
    @FXML private Label tableNameLabel;
    @FXML private Label collectionDurationLabel;

    @FXML private ChoiceBox<String> findChoiceBox;
    @FXML private ChoiceBox<String> sortChoiceBox;
    private final String[] filterParameters = {"title", "style", "duration"};

    @FXML private AnchorPane scenePane;
    @FXML private TableView<Record> tableView;
    @FXML private TableColumn<Record, Integer> idColumn;
    @FXML private TableColumn<Record, String> titleColumn;
    @FXML private TableColumn<Record, String> styleColumn;
    @FXML private TableColumn<Record, String> durationColumn;

    private String tableName;
    private DBTableManager tableManager;
    private TableController tableController;
    private CollectionService collectionService;

    public CollectionSceneController(String tableName){
        this.tableName = tableName;
    }

    public CollectionSceneController(){}

    public void setTableName(String tableName) {
        this.tableName = tableName;
        this.tableManager = new DBTableManager();
        this.tableController = new TableController(tableManager, tableName);
        this.collectionService = new CollectionService(tableManager.selectAllFromTable(tableName));
        tableNameLabel.setText(tableName);
        collectionDurationLabel.setText(collectionService.collectionDuration());

        collectionDurationLabel.setText(collectionService.collectionDuration());
        tableController.initialize(tableView, idColumn, titleColumn, styleColumn, durationColumn);

        loadTableData();
    }

    private void loadTableData() {
        System.out.println("Завантаження таблиці: " + tableName);
        // ТУТ – завантаження даних з цієї таблиці в TableView або VBox
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sortChoiceBox.getItems().addAll(filterParameters);
        sortChoiceBox.setOnAction(this::selectSortBy);

        findChoiceBox.getItems().addAll(filterParameters);
        findChoiceBox.setOnAction(this::selectFindBy);
    }

    public void clickOnAddButton(ActionEvent e){
        SceneController sceneController = new SceneController();
        sceneController.goToCreateRecordPane(e, tableManager, tableName);
        refreshCollectionDuration();
    }

    public void clickOnDeleteButton(ActionEvent e){
        Record selected = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(selected);

        Command delete = new DeleteItem(tableManager, selected);
        delete.execute();

        refreshCollectionDuration();
    }

    public void selectSortBy(ActionEvent e){
        SortController sortController = new SortController(tableManager, tableName);
        String parameter = sortChoiceBox.getValue();
        tableView.setItems(sortController.sortBy(parameter));
    }

    public void selectFindBy(ActionEvent e){
        FindController findController = new FindController(tableManager, tableName);
        String parameter = findChoiceBox.getValue();
        tableView.setItems(findController.findBy(parameter, enterField.getText()));
//        RecordFinder recordFinder = new RecordFinder(recordRepository.selectAllFromDB("records"));
//        tableView.setItems(findBy(findChoiceBox.getValue(), enterField.getText()));
    }

    public void clickOnSearchButton(ActionEvent e){
        FindController findController = new FindController(tableManager, tableName);
        tableView.setItems(findController.findBy("title", enterField.getText()));
    }

    public void clearFilters(){
        tableController.showAll(tableView);
    }

    public void back(ActionEvent e){
        SceneController sceneController = new SceneController();
        sceneController.goBackToMainScene(e);
    }

    private void refreshCollectionDuration() {
        this.collectionService = new CollectionService(tableManager.selectAllFromTable(tableName));
        collectionDurationLabel.setText(collectionService.collectionDuration());
    }

}