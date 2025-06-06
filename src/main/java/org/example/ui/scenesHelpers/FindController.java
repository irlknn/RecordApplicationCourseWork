package org.example.ui.scenesHelpers;

import javafx.collections.ObservableList;
import org.example.models.Record;
import org.example.repository.DBTableManager;

public class FindController {
    private DBTableManager repository;
    private String tableName;

    public FindController(DBTableManager repository, String tableName) {
        this.repository = repository;
        this.tableName = tableName;
    }

    public ObservableList<Record> findBy(String parameter, String input) {
        switch (parameter) {
            case "title", "style" -> {
                return repository.findByParameter(tableName, parameter, input);
            }
            case "duration" -> {
                return repository.findByDuration(tableName, "00:00:00", input);
            }
            default -> {
                return repository.findByParameter(tableName, "title", input);
            }
        }
    }
}
