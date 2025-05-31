package org.example.ui.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.models.Record;
import org.example.repository.DBTableManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.mockito.Mockito.*;

public class CreateRecordSceneControllerTest extends ApplicationTest {

    private CreateRecordSceneController controller;
    private TextField titleField;
    private TextField styleField;
    private TextField durationField;
    private TextField authorField;
    private TextField descriptionField;
    private Button submitButton;
    private DBTableManager mockRepository;

    @Override
    public void start(Stage stage) {
        controller = new CreateRecordSceneController();
        controller.setTableName("test_table");

        titleField = new TextField();
        titleField.setId("titleField");

        styleField = new TextField();
        styleField.setId("styleField");

        durationField = new TextField();
        durationField.setId("durationField");

        authorField = new TextField();
        authorField.setId("authorField");

        descriptionField = new TextField();
        descriptionField.setId("descriptionField");

        Label notificationLabel = new Label();
        notificationLabel.setId("notificationLabel");
        controller.notificationLabel = notificationLabel;

        submitButton = new Button("Add");
        submitButton.setId("submitButton");

        submitButton.setOnAction(controller::clickOnSubmitButton);

        VBox root = new VBox(titleField, styleField, durationField, authorField, descriptionField, notificationLabel, submitButton);

        // Прив'язуємо поля вручну
        controller.titleField = titleField;
        controller.styleField = styleField;
        controller.durationField = durationField;
        controller.authorField = authorField;
        controller.descriptionField = descriptionField;

        stage.setScene(new Scene(root, 400, 200));
        stage.show();
    }

    @BeforeEach
    public void setupMock() {
        mockRepository = mock(DBTableManager.class);
        controller.setRepository(mockRepository);
    }

    @Test
    public void testClickOnSubmitButtonCreatesRecord() {
        clickOn("#titleField").write("Test Title");
        clickOn("#styleField").write("Jazz");
        clickOn("#durationField").write("00:01:23");
        clickOn("#authorField").write("author");
        clickOn("#descriptionField").write("description");

        clickOn("#submitButton");

        verify(mockRepository, times(1)).insertIntoTable(any(Record.class), eq("test_table"));
    }

}
