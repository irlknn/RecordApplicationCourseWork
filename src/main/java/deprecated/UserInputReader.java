package deprecated;

import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInputReader {
    private static Scanner scanner = new Scanner(System.in);

    public static int getInteger(){
        int input;
        while(true){
            try{
                input = (scanner.nextInt());
            }catch (InputMismatchException e){
                System.out.println("Try again");
                continue;
            }finally {
                scanner.nextLine();
            }
            return input;
        }
    }

    public static String getString(){
        return scanner.nextLine();
    }

    public static LocalTime getDuration(){
        LocalTime duration = null;

        while (duration == null) {
            String input = scanner.nextLine().trim();

            if (!input.matches("^\\d{1,2}:\\d{2}$")) {
                System.out.println("Wrong format");
                continue;
            }

            String[] parts = input.split(":");
            try {
                int minutes = Integer.parseInt(parts[0]);
                int seconds = Integer.parseInt(parts[1]);

                if (seconds < 0 || seconds > 59) {
                    System.out.println("Wrong seconds interval (0<s<59)");
                    continue;
                }

                duration = LocalTime.of(0, minutes, seconds);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Incorrect numbers. Try again");
            }
        }
        return duration;
    }

}
