package org.example.ui.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class CreateRecordSceneControllerTest extends ApplicationTest {

    private CreateRecordSceneController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ui/CreateRecordScene.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setCollectionId(1);  // Mock collection ID
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void clearFields() {
        interact(() -> {
            lookup("#titleField").queryAs(TextField.class).clear();
            lookup("#styleField").queryAs(TextField.class).clear();
            lookup("#durationField").queryAs(TextField.class).clear();
            lookup("#authorField").queryAs(TextField.class).clear();
            lookup("#descriptionField").queryAs(TextField.class).clear();
        });
    }

    @Test
    public void testValidInputCreatesRecord() {
        clickOn("#titleField").write("title");
        clickOn("#styleField").write("style");
        clickOn("#durationField").write("00:02:45");
        clickOn("#authorField").write("author");
        clickOn("#descriptionField").write("description");

        clickOn("#submitButton");

        Label notificationLabel = lookup("#notificationLabel").queryAs(Label.class);
        assertEquals("Record created successfully!", notificationLabel.getText());
    }

    @Test
    public void testEmptyInputShowsError() {
        sleep(100);
        clickOn("#submitButton");

        Label notificationLabel = lookup("#notificationLabel").queryAs(Label.class);
        assertTrue(notificationLabel.getText().contains("incomplete"));
    }

    @Test
    public void testInvalidDurationShowsError() {
        clickOn("#titleField").write("title");
        clickOn("#styleField").write("style");
        clickOn("#durationField").write("abc");
        clickOn("#authorField").write("author");
        clickOn("#descriptionField").write("description");

        clickOn("#submitButton");

        Label notificationLabel = lookup("#notificationLabel").queryAs(Label.class);
        assertFalse(notificationLabel.getText().contains("Invalid duration"));
    }
}
