import java.util.Scanner;
import java.util.ArrayList;

public class Sage {
    public static void main(String[] args) throws SageException {
        System.out.println("Hello there, Sage here.");
        System.out.println("How are you doing?");

        ArrayList<Task> taskList = new ArrayList<Task>();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            try {
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
                } else if (input.startsWith("mark")) {
                    if (input.matches("mark [0-9]+")) {
                        // Validate task number exists
                        int index = Integer.parseInt(input.substring(5));
                        if (1 <= index && index <= taskList.size()) {
                            Task task = taskList.get(index - 1);
                            task.markAsDone();
                            System.out.println("Got it. I've marked \"" + index + ". "
                                    + task.getDescription() + "\" as done.");
                        } else {
                            // Failed to mark
                            throw SageException.invalidTaskNumber();
                        }
                    } else {
                        throw SageException.invalidCommand("Mark");
                    }
                } else if (input.startsWith("unmark")) {
                    if (input.matches("unmark [0-9]+")) {
                        // Validate task number exists
                        int index = Integer.parseInt(input.substring(7));
                        if (1 <= index && index <= taskList.size()) {
                            Task task = taskList.get(index - 1);
                            task.markAsUndone();
                            System.out.println("Got it. I've marked \"" + index + ". "
                                    + task.getDescription() + "\" as undone.");
                        } else {
                            // Failed to unmark
                            throw SageException.invalidTaskNumber();
                        }
                    } else {
                        throw SageException.invalidCommand("Unmark");
                    }
                } else {
                    // Validate type of task
                    if (input.startsWith("todo")) {
                        if (input.matches("^todo\\s+(\\S.+)")) {
                            taskList.add(new ToDo(input.substring(5)));
                        } else {
                            throw SageException.invalidCommand("ToDo");
                        }
                    } else if (input.startsWith("deadline")) {
                        if (input.matches("^deadline\\s+(\\S.+?)\\s+/by\\s+(\\S.+)")) {
                            String[] parts = input.substring(9).split(" /by ");
                            taskList.add(new Deadline(parts[0], parts[1]));
                        } else {
                            throw SageException.invalidCommand("Deadline");
                        }
                    } else if (input.startsWith(("event"))) {
                        if (input.matches("^event\\s+(\\S.+?)\\s+/from\\s+(\\S.+?)\\s+/to\\s+(\\S.+)")) {
                            String[] parts = input.substring(6).split(" /from | /to ");
                            taskList.add(new Event(parts[0], parts[1], parts[2]));
                        } else {
                            throw SageException.invalidCommand("Event");
                        }
                    } else {
                        throw SageException.unknownCommand();
                    }
                    System.out.println("Got it. I've added this task:\n"
                            + taskList.get(taskList.size() - 1)
                            + "\nYou've now set out to do " + String.valueOf(taskList.size()) + " thing(s).");
                }
            } catch (Exception e) { // Catch all exceptions
                System.out.print(e.getMessage());
            }
            System.out.println();
            input = scanner.nextLine();
        }

        System.out.println("Goodbye. Have a beautiful day.");
        scanner.close();
    }
}