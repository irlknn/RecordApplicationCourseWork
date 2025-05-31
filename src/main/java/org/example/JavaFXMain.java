package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.repository.DBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

import static org.example.ui.UIConstants.*;

public class JavaFXMain extends Application {
    private static final Logger logger = LoggerFactory.getLogger(JavaFXMain.class);
    DBManager dbManager = new DBManager();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {

//            logger.error("Test error email, This is a test exception");

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(MAIN_SCENE_FXML)));
            stage.setTitle(APP_TITLE);

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(STYLES_CSS)).toExternalForm());
            stage.show();
            logger.info("Program have been started");
        } catch (IOException e) {
            logger.error("Failed to start program {}", e);
        }
    }
}
