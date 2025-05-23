package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import ui.scenes.MainSceneController;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class MainSceneControllerTest {

    private MainSceneController controller;

    @Start
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/MainScene.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    void setupEach() {
        // Optionally reset state before each test
    }

    @Test
    void testCreateTable(FxRobot robot) {
        robot.clickOn("#newTableName").write("test_table");
        robot.clickOn("#crateButton");

        VBox container = robot.lookup("#tablesNameContainer").queryAs(VBox.class);
        assertFalse(container.getChildren().isEmpty(), "New table name should appear in the container.");
    }

    @Test
    void testSearchButton(FxRobot robot) {
        robot.clickOn("#enterField").write("test");
        robot.clickOn("#searchButton");

        VBox container = robot.lookup("#tablesNameContainer").queryAs(VBox.class);
        assertNotNull(container, "Container should not be null after search.");
        // Additional checks for search result display
    }

    @Test
    void testClearFilters(FxRobot robot) {
        robot.clickOn("#enterField").write("temp");
        robot.clickOn("#searchButton");

        robot.clickOn("#newTableName").write("temp");
        robot.clickOn("#crateButton");

        robot.clickOn("#exitButton");
    }

    @Test
    void testExit(FxRobot robot) {
        Stage stage = (Stage) robot.lookup("#scenePane").query().getScene().getWindow();
        assertTrue(stage.isShowing());
        robot.clickOn("#exitButton");
        assertFalse(stage.isShowing());
    }
}
