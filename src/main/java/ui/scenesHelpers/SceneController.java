package ui.scenesHelpers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.DBTableManager;
import ui.scenes.CollectionSceneController;
import ui.scenes.CreateRecordSceneController;
import utils.CollectionService;

import java.io.IOException;
import java.util.Objects;

import static ui.UIConstants.*;

public class SceneController {
    private static final Logger logger = LogManager.getLogger(SceneController.class);

    public void goToRecordCollectionScene(ActionEvent event, String tableName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(RECORD_COLLECTION_SCENE_FXML));
            Parent root = loader.load();

            CollectionSceneController controller = loader.getController();

            DBTableManager tableManager = new DBTableManager();
            TableController tableController = new TableController(tableManager, tableName);
            CollectionService collectionService = new CollectionService(tableManager.selectAllFromTable(tableName));

            controller.initialize(tableName, tableManager, tableController, collectionService);

            logger.info("Opened {} collection", tableName);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(STYLES_CSS)).toExternalForm());
            stage.show();
        } catch (IOException e) {
            logger.error("problem with creating and loading record collection scene for {}", tableName);
            throw new RuntimeException(e);
        }
    }

    public void goBackToMainScene(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(MAIN_SCENE_FXML)));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(STYLES_CSS)).toExternalForm());
            stage.show();
        } catch (IOException e) {
            logger.error("problem with switching to main scene");
            throw new RuntimeException(e);
        }
    }


    public void goToCreateRecordPane(ActionEvent e, DBTableManager repository, String tableName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(CREATE_RECORD_SCENE_FXML));
            Parent root = loader.load();

            CreateRecordSceneController controller = loader.getController();
            controller.setRepository(repository);
            controller.setTableName(tableName);

            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(STYLES_CSS)).toExternalForm());
            stage.show();
        } catch (IOException ex) {
            logger.error("problem with switching to create record scene");
            throw new RuntimeException(ex);
        }
    }
}
