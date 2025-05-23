package ui.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.Record;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.DBTableManager;
import ui.scenesHelpers.SceneController;

import static utils.TimeUtils.changeTimeFormat;

public class CreateRecordSceneController {
    private static final Logger logger = LogManager.getLogger(CreateRecordSceneController.class);
    private DBTableManager repository;
    private String tableName;

    @FXML
    TextField titleField;
    @FXML
    TextField styleField;
    @FXML
    TextField durationField;

    public CreateRecordSceneController() {}

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setRepository(DBTableManager repository) {
        this.repository = repository;
    }

    public void clickOnSubmitButton(ActionEvent e) {
        System.out.println("add button pressed");
        Record record = createRecord();
        if (record != null) {
            repository.insertIntoTable(record, tableName);
        } else {
            System.out.println("Record is invalid or incomplete.");
        }
    }

    public Record createRecord() {
        String title = titleField.getText();
        String style = styleField.getText();
        String duration = durationField.getText();

        if (title == null || style == null || duration == null || title.isEmpty() || style.isEmpty() || duration.isEmpty()) {
            System.out.println("Please, enter all data");
            return null;
        }

        try {
            return new Record(title, style, changeTimeFormat(duration));
        } catch (Exception e) {
            logger.error("Create record error title - {}, style - {}, duration - {}", title, style, duration);
            return null;
        }
    }

    @FXML
    public void goToProgramUI(ActionEvent e) {
        SceneController sceneController = new SceneController();
        System.out.println(tableName);
        sceneController.goToRecordCollectionScene(e, tableName);
    }
}
