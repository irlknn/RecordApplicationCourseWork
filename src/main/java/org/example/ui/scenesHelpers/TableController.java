package org.example.ui.scenesHelpers;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.models.Record;
import org.example.repository.DBTableManager;

public class TableController {
    private DBTableManager tableManager;
    private String tableName;

    public TableController(DBTableManager tableManager, String tableName) {
        this.tableManager = tableManager;
        this.tableName = tableName;
    }

    public void initialize(TableView<Record> tableView,
                           TableColumn<Record, Integer> idColumn,
                           TableColumn<Record, String> titleColumn,
                           TableColumn<Record, String> styleColumn,
                           TableColumn<Record, String> durationColumn) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        styleColumn.setCellValueFactory(new PropertyValueFactory<>("style"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        displayRecords(tableView);
    }

    public void displayRecords(TableView<Record> tableView) {
        tableView.setItems(tableManager.selectAllFromTable(tableName));
    }
}