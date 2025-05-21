package mainPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.DBInitializer;

import java.io.IOException;
import java.util.Objects;

import static ui.UIConstants.APP_TITLE;

public class JavaFXMain extends Application {
    private static final Logger logger = LogManager.getLogger(JavaFXMain.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            DBInitializer.initializeDB();
//        DBInitializer.addData();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui/mainScene.fxml")));
            stage.setTitle(APP_TITLE);

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            stage.show();
            logger.info("Program have been started");
        } catch (IOException e) {
            logger.error("Failed to start program {}", e);
//            throw new RuntimeException(e);
        }
    }
}
