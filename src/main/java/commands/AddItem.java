package commands;

import models.Record;
import repository.DBTableManager;

public class AddItem implements Command {

    private DBTableManager repository;
    private Record record;
    private String tableName;

    public AddItem(DBTableManager repository, Record record, String tableName) {
        this.repository = repository;
        this.record = record;
        this.tableName = tableName;
    }

    @Override
    public void execute() {
        repository.insertIntoTable(record, tableName);
    }
}
