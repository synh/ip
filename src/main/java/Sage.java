import java.util.Scanner;
import java.util.ArrayList;

public class Sage {
    public static void main(String[] args) {
        System.out.println("Hello there, Sage here.");
        System.out.println("How are you doing?");

        ArrayList<Task> taskList = new ArrayList<Task>();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            if (input.equals("list")) {
                if (taskList.isEmpty()) {
                    System.out.println("Oh, you have nothing you set out to do. Enjoy your day.");
                } else {
                    System.out.println("These are what you set out to do:");
                    int index = 1;
                    for (Task task : taskList) {
                        System.out.println(index + ". [" + task.getStatusIcon() + "] " + task.getDescription());
                        index++;
                    }
                }
            } else if (input.matches("mark [0-9]+")) {
                // Validate task number exists
                int index = Integer.parseInt(input.substring(5));
                if (1 <= index && index <= taskList.size()) {
                    taskList.get(index - 1).markAsDone();
                } else {
                    // Failed to mark
                    System.out.println("That task doesn't exist, apparently. I guess you've completed a task outside of what you set out to do.");
                }
            } else if (input.matches("unmark [0-9]+")) {
                // Validate task number exists
                int index = Integer.parseInt(input.substring(7));
                if (1 <= index && index <= taskList.size()) {
                    taskList.get(index - 1).markAsUndone();
                } else {
                    // Failed to unmark
                    System.out.println("That task doesn't exist, apparently. Try adding it to the list first.");
                }
            } else {
                taskList.add(new Task(input));
                System.out.println("added: " + input);
            }
            input = scanner.nextLine();
        }

        System.out.println("Goodbye. Have a beautiful day.");
        scanner.close();
    }
}