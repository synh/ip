import java.util.Scanner;
import java.util.ArrayList;

import java.lang.String;

public class Sage {
    public static void main(String[] args) {
        System.out.println("Hello there, Sage here.");
        System.out.println("How are you doing?");

        ArrayList<String> taskList = new ArrayList<String>();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            if (input.equals("list")) {
                int index = 1;
                for (String task : taskList) {
                    System.out.println(index + ". " + task);
                    index++;
                }
            } else {
                taskList.add(input);
                System.out.println("added: " + input);
            }
            input = scanner.nextLine();
        }

        System.out.println("Goodbye. Have a beautiful day.");
        scanner.close();
    }
}