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
                        System.out.println(index + ". " + task);
                        index++;
                    }
                }
            } else if (input.matches("mark [0-9]+")) {
                // Validate task number exists
                int index = Integer.parseInt(input.substring(5));
                if (1 <= index && index <= taskList.size()) {
                    Task task = taskList.get(index - 1);
                    task.markAsDone();
                    System.out.println("Got it. I've marked \"" + index + ". "
                            + task.getDescription() + "\" as done.");
                } else {
                    // Failed to mark
                    System.out.println("That task doesn't exist, apparently. I guess you've completed a task outside of what you set out to do.");
                }
            } else if (input.matches("unmark [0-9]+")) {
                // Validate task number exists
                int index = Integer.parseInt(input.substring(7));
                if (1 <= index && index <= taskList.size()) {
                    Task task = taskList.get(index - 1);
                    task.markAsUndone();
                    System.out.println("Got it. I've marked \"" + index + ". "
                            + task.getDescription() + "\" as undone.");
                } else {
                    // Failed to unmark
                    System.out.println("That task doesn't exist, apparently. Try adding it to the list first.");
                }
            } else {
                // Validate type of task
                boolean isValid = false;
                if (input.startsWith("todo ")) {
                    taskList.add(new ToDo(input.substring(5)));
                    isValid = true;
                } else if (input.startsWith("deadline ")) {
                    String[] parts = input.substring(9).split(" /by ");
                    taskList.add(new Deadline(parts[0], parts[1]));
                    isValid = true;
                } else if (input.startsWith(("event "))) {
                    String[] parts = input.substring(6).split(" /from | /to ");
                    taskList.add(new Event(parts[0], parts[1], parts[2]));
                    isValid = true;
                } else {
                    System.out.println("I'm afraid I didn't catch that.");
                }
                if (isValid) {
                    System.out.println("Got it. I've added this task:\n"
                            + taskList.get(taskList.size() - 1)
                            + "\nYou've now set out to do " + String.valueOf(taskList.size()) + " thing(s).");
                }
            }
            System.out.println();
            input = scanner.nextLine();
        }

        System.out.println("Goodbye. Have a beautiful day.");
        scanner.close();
    }
}