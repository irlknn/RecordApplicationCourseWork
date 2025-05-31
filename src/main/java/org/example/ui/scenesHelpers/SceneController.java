package org.example.ui.scenesHelpers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.ui.scenes.CollectionSceneController;
import org.example.ui.scenes.CreateRecordSceneController;
import org.example.utils.CollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

import static org.example.ui.UIConstants.*;

public class SceneController {
    private static final Logger logger = LoggerFactory.getLogger(SceneController.class);

    public void goToRecordCollectionScene(ActionEvent event, int collectionId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(RECORD_COLLECTION_SCENE_FXML));
            Parent root = loader.load();

            CollectionSceneController controller = loader.getController();

            controller.initialize(collectionId);

            logger.info("Opened {} collection", collectionId);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(STYLES_CSS)).toExternalForm());
            stage.show();
        } catch (IOException e) {
            logger.error("problem with creating and loading record collection scene for {}", collectionId);
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


    public void goToCreateRecordPane(ActionEvent e, int collectionId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(CREATE_RECORD_SCENE_FXML));
            Parent root = loader.load();

            CreateRecordSceneController controller = loader.getController();
            controller.setCollectionId(collectionId);

            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(STYLES_CSS)).toExternalForm());
            stage.show();
        } catch (IOException ex) {
            logger.error("problem with switching to create record scene");
        }
    }
}
