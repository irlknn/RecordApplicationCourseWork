package commands;

import models.Record;
import repository.DBTableManager;

public class DeleteItem implements Command {
    private DBTableManager repository;
    private Record record;
    private String tableName;

    public DeleteItem(DBTableManager repository, Record record, String tableName) {
        this.repository = repository;
        this.record = record;
        this.tableName = tableName;
    }

    @Override
    public void execute() {
        if (record != null) {
            System.out.println("record was deleted" + record);
            repository.deleteFromTableById(record.getId(), tableName);
        } else {
            System.out.println("Choose smth");
        }
    }
}
