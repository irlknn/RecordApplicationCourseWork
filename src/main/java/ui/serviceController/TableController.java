package ui.serviceController;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import repository.DBTableManager;

import models.Record;

public class TableController {
    private DBTableManager tableManager;
    private String tableName;

    public TableController(DBTableManager tableManager, String tableName){
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
        showAll(tableView);
    }

    public void showAll(TableView<Record> tableView){
        tableView.setItems(tableManager.selectAllFromTable(tableName));
    }
}