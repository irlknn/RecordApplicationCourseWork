package ui.scenes;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import repository.DBManager;
import repository.DBTableManager;
import ui.scenesHelpers.TablesNameListController;

import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    @FXML
    VBox tablesNameContainer;
    @FXML
    private Button crateButton;
    @FXML
    private Button exitButton;
    @FXML
    private TextField enterField;
    @FXML
    private Button searchButton;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private TextField newTableName;

    private DBTableManager tableManager = new DBTableManager();
    private DBManager dbManager = new DBManager();
    private TablesNameListController tablesNameListController = new TablesNameListController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tablesNameListController.loadTablesName(tablesNameContainer);
    }

    @FXML
    private void createTable() {
        String name = newTableName.getText();
        if (name != null) {
            dbManager.createTable(name);
            tablesNameListController.loadTablesName(tablesNameContainer);
        }
    }

    @FXML
    private void clickOnSearchButton() {
        tablesNameContainer.getChildren().clear(); // очистити попередні результати
        tablesNameListController.searchAndShowTables(enterField.getText(), tablesNameContainer);
    }

    @FXML
    private void clickOnClearFilters() {
        tablesNameContainer.getChildren().clear(); // очистити попередні результати
        tablesNameListController.loadTablesName(tablesNameContainer);
    }

    @FXML
    private void exit() {
        Stage stage = (Stage) scenePane.getScene().getWindow();
        System.out.println("You successfully exit");
        stage.close();
    }

}
