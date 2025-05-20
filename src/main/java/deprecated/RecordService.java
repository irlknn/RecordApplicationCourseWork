package deprecated;

import models.Record;
import repository.DBTableManager;

import java.util.List;

public class RecordService {
    private DBTableManager recordContainer;

    public RecordService(DBTableManager recordContainer) {
        this.recordContainer = recordContainer;
    }

    public void addRecord(Record record) {
        recordContainer.insertIntoTable(record, "records");
    }

    public List<Record> getAllRecords() {
        return recordContainer.selectAllFromTable("records");
    }

    public List<Record> findRecordByTitle(String title) {
        return recordContainer.findByParameter("records", "title", title);
    }

}
