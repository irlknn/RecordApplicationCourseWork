package ui.serviceController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.DBTableManager;
import ui.CollectionSceneController;
import ui.CreateRecordController;

import java.io.IOException;
import java.util.Objects;

public class SceneController {
    private static final Logger logger = LogManager.getLogger(SceneController.class);

    public void goToRecordCollectionScene(ActionEvent event, String tableName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/recordCollectionScene.fxml"));
            Parent root = loader.load();

            // Передати tableName у контролер
            CollectionSceneController controller = loader.getController();
            controller.setTableName(tableName);
            logger.info("Opened {} collection", tableName);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.error("problem with loading recordCollectionScene.fxml");
            throw new RuntimeException(e);
        }
    }

    public void goBackToMainScene(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui/mainScene.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.error("problem with switching to main scene");
            throw new RuntimeException(e);
        }
    }


    public void goToCreateRecordPane(ActionEvent e, DBTableManager repository, String tableName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/createRecordScene.fxml"));
            Parent root = loader.load();

            CreateRecordController controller = loader.getController();
            controller.setRepository(repository);
            controller.setTableName(tableName);

            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            logger.error("problem with switching to create record scene");
            throw new RuntimeException(ex);
        }
    }
}
