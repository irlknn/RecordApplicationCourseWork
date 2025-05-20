package commands;

import repository.DBTableManager;

public class ShowItem implements Command{
    private DBTableManager repository;

    public ShowItem(DBTableManager repository){
        this.repository = repository;
    }

    @Override
    public void execute() {
        repository.selectAllFromTable("records");
    }
}
