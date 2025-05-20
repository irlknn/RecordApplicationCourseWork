package commands;

import models.Record;
import repository.DBTableManager;

public class DeleteItem implements Command{
    private DBTableManager repository;
    private Record record;

    public DeleteItem(DBTableManager repository, Record record){
        this.repository = repository;
        this.record = record;
    }

    @Override
    public void execute() {
        if (record != null){
            System.out.println("record was deleted" + record);
            repository.deleteFromTableById(record.getId(), "records");
        }else {
            System.out.println("Choose smth");
        }
    }
}
