package org.example.ui.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.models.Record;
import org.example.repository.DBTableManager;
import org.example.ui.scenesHelpers.SceneController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.utils.TimeUtils.changeTimeFormat;

public class CreateRecordSceneController {
    private static final Logger logger = LoggerFactory.getLogger(CreateRecordSceneController.class);

    @FXML
    private TextField titleField;
    @FXML
    private TextField styleField;
    @FXML
    private TextField durationField;
    @FXML
    private Label notificationLabel;

    private DBTableManager repository;
    private String tableName;

    public CreateRecordSceneController() {
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setRepository(DBTableManager repository) {
        this.repository = repository;
    }

    public void clickOnSubmitButton(ActionEvent e) {
        Record record = createRecord();
        if (record != null) {
            repository.insertIntoTable(record, tableName);
            showSuccess("Record created successfully!");
            clearFields();
        } else {
            showError("Record is invalid or incomplete. Please check all fields.");
        }
    }

    private void showSuccess(String message) {
        notificationLabel.setStyle("-fx-text-fill: #2d8345; -fx-font-size: 14px;");
        notificationLabel.setText(message);
    }

    private void showError(String message) {
        notificationLabel.setStyle("-fx-text-fill: #dc3545; -fx-font-size: 14px;");
        notificationLabel.setText(message);
    }

    private void clearFields() {
        titleField.clear();
        styleField.clear();
        durationField.clear();
        titleField.requestFocus();
    }

    public Record createRecord() {
        String title = titleField.getText();
        String style = styleField.getText();
        String duration = durationField.getText();

        if (title == null || style == null || duration == null ||
                title.trim().isEmpty() || style.trim().isEmpty() || duration.trim().isEmpty()) {
            showError("Please enter all data");
            return null;
        }

        try {
            return new Record(title, style, changeTimeFormat(duration), "", "");
        } catch (Exception e) {
            logger.error("Create record error title - {}, style - {}, duration - {}", title, style, duration);
            showError("Invalid duration format. Please use MM:SS format.");
            return null;
        }
    }

    @FXML
    public void goToProgramUI(ActionEvent e) {
        SceneController sceneController = new SceneController();
        sceneController.goToRecordCollectionScene(e, tableName);
    }
}