package org.example.ui.scenesHelpers;

import javafx.collections.ObservableList;
import org.example.models.Record;
import org.example.repository.DBRecordCollectionManager;

public class FindController {
    private DBRecordCollectionManager recordCollectionManager;
    private int collectionId;

    public FindController(DBRecordCollectionManager recordCollectionManager, int collectionId) {
        this.recordCollectionManager = recordCollectionManager;
        this.collectionId = collectionId;
    }

    public ObservableList<Record> findBy(String parameter, String input) {
        switch (parameter) {
            case "title", "style" -> {
                return recordCollectionManager.findByParameter(collectionId, parameter, input);
            }
            case "duration" -> {
                return recordCollectionManager.findByDuration(collectionId, "00:00:00", input);
            }
            default -> {
                return recordCollectionManager.findByParameter(collectionId, "title", input);
            }
        }
    }
}
