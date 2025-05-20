import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.DBInitializer;

import java.util.Objects;

public class JavaFXMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        DBInitializer.initializeDB();
//        DBInitializer.addData();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui/mainScene.fxml")));
        stage.setTitle("Record storage");

        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
//        stage.setScene(scene);
        stage.show();
    }
}
