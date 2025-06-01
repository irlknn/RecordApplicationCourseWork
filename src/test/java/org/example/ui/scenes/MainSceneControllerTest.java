package org.example.ui.scenes;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

@ExtendWith(ApplicationExtension.class)
public class MainSceneControllerTest {

    private Stage stage;
    private VBox collectionsBox;

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ui/MainScene.fxml"));
        Parent root = loader.load();
        this.stage = stage;
        stage.setScene(new Scene(root));
        stage.show();

        MainSceneController controller = loader.getController();
        collectionsBox = controller.collectionsNameContainer;
    }

    @Test
    void testCreateCollection(FxRobot robot) {
        robot.clickOn("#newTableName").write("test_collection");
        robot.clickOn("#createButton");

        // Очікуємо, що VBox міститиме щось (імітація оновлення)
        assertFalse(collectionsBox.getChildren().isEmpty());
    }

    @Test
    void testSearchCollection(FxRobot robot) {
        robot.clickOn("#enterField").write("my_query");
        robot.clickOn("#searchButton");

        // Перевіримо, що VBox оновився (як приклад — очистився)
        assertTrue(collectionsBox.getChildren().isEmpty());
    }

    @Test
    void testClearFilters(FxRobot robot) {
        robot.clickOn("#clearFiltersButton");

        // Після очистки — має бути щось у VBox (імітація перезавантаження)
        assertFalse(collectionsBox.getChildren().isEmpty());
    }

    @Test
    void testExit(FxRobot robot) {
        robot.clickOn("#exitButton");
        assertFalse(stage.isShowing());
    }
}

//package org.example.ui.scenes;
//
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.testfx.api.FxRobot;
//import org.testfx.framework.junit5.ApplicationExtension;
//import org.testfx.framework.junit5.Start;
//
//import java.util.Objects;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(ApplicationExtension.class)
//public class MainSceneControllerTest {
//
//    private MainSceneController controller;
//
//    @Start
//    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/ui/MainScene.fxml")));
//        stage.setScene(new Scene(root));
//        stage.show();
//    }
//
//    @BeforeEach
//    void setupEach() {
//    }
//
//    @Test
//    void testCreateCollection(FxRobot robot) {
//        robot.clickOn("#newTableName").write("test_table");
//        robot.clickOn("#crateButton");
//
//        VBox container = robot.lookup("#tablesNameContainer").queryAs(VBox.class);
//        assertFalse(container.getChildren().isEmpty(), "New table name should appear in the container.");
//    }
//
//    @Test
//    void testSearchButton(FxRobot robot) {
//        robot.clickOn("#enterField").write("test");
//        robot.clickOn("#searchButton");
//
//        VBox container = robot.lookup("#tablesNameContainer").queryAs(VBox.class);
//        assertNotNull(container, "Container should not be null after search.");
//    }
//
//    @Test
//    void testClearFilters(FxRobot robot) {
//        robot.clickOn("#enterField").write("temp");
//        robot.clickOn("#searchButton");
//
//        robot.clickOn("#newTableName").write("temp");
//        robot.clickOn("#crateButton");
//
//        robot.clickOn("#exitButton");
//    }
//
//    @Test
//    void testExit(FxRobot robot) {
//        Stage stage = (Stage) robot.lookup("#scenePane").query().getScene().getWindow();
//        assertTrue(stage.isShowing());
//        robot.clickOn("#exitButton");
//        assertFalse(stage.isShowing());
//    }
//}
