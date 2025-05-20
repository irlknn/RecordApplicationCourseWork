package ui.serviceController;

import javafx.collections.ObservableList;
import models.Record;
import repository.DBTableManager;

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
