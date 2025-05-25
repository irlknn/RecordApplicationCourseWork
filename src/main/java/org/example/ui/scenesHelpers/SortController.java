package org.example.ui.scenesHelpers;

import javafx.collections.ObservableList;
import org.example.models.Record;
import org.example.repository.DBTableManager;

public class SortController {
    private final DBTableManager tableManager;
    private String tableName;

    public SortController(DBTableManager tableManager, String tableName) {
        this.tableManager = tableManager;
        this.tableName = tableName;
    }

    public ObservableList<Record> sortBy(String parameter) {
        return tableManager.sortByParameter(tableName, parameter);
    }
}
