package org.example.ui.scenes;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.repository.DBCollectionManager;
import org.example.ui.scenesHelpers.TablesNameListController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(MainSceneController.class);

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

    private DBCollectionManager dbcollectioManager = new DBCollectionManager();
    private TablesNameListController tablesNameListController = new TablesNameListController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tablesNameListController.loadTablesName(tablesNameContainer);
    }

    @FXML
    private void createCollection() {
        String name = newTableName.getText();
        if (name != null) {
            dbcollectioManager.insertCollection(name);
            tablesNameListController.loadTablesName(tablesNameContainer);
        }
    }

    @FXML
    private void clickOnSearchButton() {
        tablesNameContainer.getChildren().clear();
        tablesNameListController.searchAndShowTables(enterField.getText(), tablesNameContainer);
    }

    @FXML
    private void clickOnClearFilters() {
        tablesNameContainer.getChildren().clear();
        tablesNameListController.loadTablesName(tablesNameContainer);
    }

    @FXML
    private void exit() {
        Stage stage = (Stage) scenePane.getScene().getWindow();
        logger.info("You successfully exit");
        stage.close();
    }

}
