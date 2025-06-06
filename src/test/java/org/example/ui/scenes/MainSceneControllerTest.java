package org.example.ui.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
