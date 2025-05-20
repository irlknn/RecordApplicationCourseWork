import commands.FindItem;
import deprecated.RecordCollection;
import deprecated.UserInputReader;
import repository.DBInitializer;
import repository.DBTableManager;

public class Main {
    private final DBTableManager tableManager = new DBTableManager();

    public static void main(String[] args) {
        RecordCollection recordCollection = new RecordCollection();
        DBInitializer.initializeDB();
//        DBInitializer.addData();

        while (true) {
            System.out.println(menu());
            int input = UserInputReader.getInteger();

            switch (input) {
//                case 1 -> new AddItem(recordCollection).execute();
//                case 2 -> new DeleteItem(recordCollection).execute();
                case 3 -> new FindItem(recordCollection).execute();
//                case 4 -> new ShowItem(recordCollection).execute();
//                case 5 -> new SortItem(recordCollection).execute();
                case 6 -> {
//                    new ExitItem(recordCollection).execute();
                    return;
                }
                default -> System.out.println("Try again");
            }
        }

    }

    public static String menu() {
        return """
                Main menu
                1. Add record
                2. Delete record
                3. Find record
                4. Show collection
                5. Sort collection
                6. Exit""";
    }
}
