package org.example.ui.scenesHelpers;

import javafx.collections.ObservableList;
import org.example.models.Record;
import org.example.repository.DBRecordCollectionManager;

public class SortController {
    private DBRecordCollectionManager recordCollectionManager;
    private int collectionId;

    public SortController(DBRecordCollectionManager recordCollectionManager, int collectionId) {
        this.recordCollectionManager = recordCollectionManager;
        this.collectionId = collectionId;
    }

    public ObservableList<Record> sortBy(String parameter) {
        return recordCollectionManager.sortByParameter(collectionId, parameter);
    }
}
