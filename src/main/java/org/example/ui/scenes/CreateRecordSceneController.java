package org.example.ui.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.models.Record;
import org.example.repository.DBRecordCollectionManager;
import org.example.ui.scenesHelpers.SceneController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.utils.TimeUtils.changeTimeFormat;

public class CreateRecordSceneController {
    private static final Logger logger = LoggerFactory.getLogger(CreateRecordSceneController.class);

    @FXML
    TextField titleField;
    @FXML
    TextField styleField;
    @FXML
    TextField durationField;
    @FXML
    TextField authorField;
    @FXML
    TextField descriptionField;
    @FXML
    Label notificationLabel;

    private DBRecordCollectionManager recordCollectionManager = new DBRecordCollectionManager();
    //    private DBRecordManager recordManager;
    private int collectionId;

    public CreateRecordSceneController() {
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public void clickOnSubmitButton(ActionEvent e) {
        Record record = createRecord();
        if (record != null) {
            recordCollectionManager.addRecordToCollection(record, collectionId);
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
        authorField.clear();
        descriptionField.clear();
        titleField.requestFocus();
    }

    public Record createRecord() {
        String title = titleField.getText();
        String style = styleField.getText();
        String duration = durationField.getText();
        String author = authorField.getText();
        String description = descriptionField.getText();

        if (title == null || style == null || duration == null ||
                title.trim().isEmpty() || style.trim().isEmpty() || duration.trim().isEmpty() || author.trim().isEmpty() || description.trim().isEmpty()) {
            showError("Please enter all data");
            return null;
        }

        try {
            return new Record(title, style, changeTimeFormat(duration), author, description);
        } catch (Exception e) {
            logger.error("Create record error title - {}, style - {}, duration - {}, author - {}, description - {}", title, style, duration, author, description);
            showError("Invalid duration format. Please use MM:SS format.");
            return null;
        }
    }

    @FXML
    public void goToProgramUI(ActionEvent e) {
        SceneController sceneController = new SceneController();
        sceneController.goToRecordCollectionScene(e, collectionId);
    }
}