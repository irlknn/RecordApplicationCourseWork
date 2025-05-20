package deprecated;

import models.Record;
import repository.DBTableManager;

import java.time.LocalTime;
import java.util.List;

public class RecordCollection implements UserActionHandler {
    private DBTableManager tableManager = new DBTableManager();

    @Override
    public void add() {
        models.Record record = createRecordWithConsole();
        tableManager.insertIntoTable(record, "records");
        System.out.println("Record was added");
    }

    public models.Record createRecordWithConsole() {
        System.out.println("Title: ");
        String title = UserInputReader.getString();
        System.out.println("Style: ");
        String style = UserInputReader.getString();
        System.out.println("Duration: ");
        LocalTime duration = UserInputReader.getDuration();
        return new models.Record(title, style, duration);
    }

    @Override
    public void show() {
        List<models.Record> records = tableManager.selectAllFromTable("records");
        System.out.println("Records:");
        records.forEach(System.out::println);
    }

    @Override
    public void find() {
        while (true) {
            System.out.println("""
                    1. Find by title
                    2. Find by style
                    3. Find by duration
                    4. Back
                    """);

            int input = UserInputReader.getInteger();
            switch (input) {
                case 1 -> {
                    System.out.println("title: ");
                    String title = UserInputReader.getString();
                    List<models.Record> foundRecords = tableManager.findByParameter("records", "title", title);
                    foundRecords.forEach(System.out::println);
                }
                case 2 -> {
                    System.out.println("style: ");
                    String style = UserInputReader.getString();
                    List<models.Record> foundRecords = tableManager.findByParameter("records", "style", style);
                    foundRecords.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.println("enter interval: " + "\nstart -> ");
                    String start = UserInputReader.getString();
                    System.out.println("\nend -> ");
                    String end = UserInputReader.getString();
//                    List<Record> foundRecords = recordContainer.findByParameter(title);
                }
                case 4 -> {
                    return;
                }
                default -> System.out.println("Try again");
            }
            System.out.println("Records were found");
        }

    }

    @Override
    public void sort() {
        while (true) {
            System.out.println("""
                    1. Sort by title
                    2. Sort by style
                    3. Back
                    """);

            int input = UserInputReader.getInteger();
            switch (input) {
                case 1 -> {
                    System.out.println("title: ");
//                    String title = UserInputReader.getString();
                    List<models.Record> sortedRecords = tableManager.sortByParameter("records", "title");
                    sortedRecords.forEach(System.out::println);
                }
                case 2 -> {
                    System.out.println("style: ");
//                    String style = UserInputReader.getString();
                    List<Record> sortedRecords = tableManager.sortByParameter("records", "style");
                    sortedRecords.forEach(System.out::println);
                }
                case 3 -> {
                    return;
                }
                default -> System.out.println("Try again");
            }
        }
    }

    @Override
    public void delete() {
        show();
        System.out.println("Select record by id");
        int recordId = UserInputReader.getInteger();
        tableManager.deleteFromTableById(recordId, "records");
        show();
    }

    @Override
    public void exit() {
        System.out.println("Exiting...");
    }
}
