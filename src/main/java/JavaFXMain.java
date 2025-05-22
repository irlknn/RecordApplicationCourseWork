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

import static ui.UIConstants.*;

public class JavaFXMain extends Application {
    private static final Logger logger = LogManager.getLogger(JavaFXMain.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            DBInitializer.initializeDB();
//        DBInitializer.addData();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(MAIN_SCENE_FXML)));
            stage.setTitle(APP_TITLE);

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource(STYLES_CSS).toExternalForm());
            stage.show();
            logger.info("Program have been started");
        } catch (IOException e) {
            logger.error("Failed to start program {}", e);
//            throw new RuntimeException(e);
        }
    }
}
