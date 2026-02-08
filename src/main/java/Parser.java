import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Parser {
    public static void parse(TaskList taskList) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            try {
                String[] parts = input.split(" ");
                CommandType commandType = CommandType.fromString(parts[0]);
                switch(commandType) {
                case LIST:
                    Ui.printTaskList(taskList);
                    break;
                case MARK:
                    if (parts.length == 2 && parts[1].matches("[0-9]+")) {
                        // Validate task number exists
                        int index = Integer.parseInt(parts[1]); // 1-based indexing
                        if (1 <= index && index <= taskList.getSize()) {
                            Task task = taskList.getTask(index - 1);
                            task.markAsDone();
                            Storage.saveTasks(taskList);
                            Ui.printMarkSuccess(task, index);
                        } else {
                            throw SageException.invalidTaskNumber();
                        }
                    } else {
                        throw SageException.invalidCommand("Mark");
                    }
                    break;
                case UNMARK:
                    if (parts.length == 2 && parts[1].matches("[0-9]+")) {
                        // Validate task number exists
                        int index = Integer.parseInt(parts[1]); // 1-based indexing
                        if (1 <= index && index <= taskList.getSize()) {
                            Task task = taskList.getTask(index - 1);
                            task.markAsUndone();
                            Storage.saveTasks(taskList);
                            Ui.printUnmarkSuccess(task, index);
                        } else {
                            throw SageException.invalidTaskNumber();
                        }
                    } else {
                        throw SageException.invalidCommand("Unmark");
                    }
                    break;
                case DELETE:
                    if (parts.length == 2 && parts[1].matches("[0-9]+")) {
                        // Validate task number exists
                        int index = Integer.parseInt(parts[1]); // 1-based indexing
                        if (1 <= index && index <= taskList.getSize()) {
                            Task task = taskList.getTask(index - 1);
                            taskList.deleteTask(index - 1);
                            Storage.saveTasks(taskList);
                            Ui.printDeleteSuccess(task, index, taskList);
                        } else {
                            throw SageException.invalidTaskNumber();
                        }
                    } else {
                        throw SageException.invalidCommand("Delete");
                    }
                    break;
                case TODO:
                    if (parts.length > 1) {
                        taskList.addTask(new ToDo(input.substring(5)));
                        Storage.saveTasks(taskList);
                        Ui.printAddedSuccess(taskList);
                    } else {
                        throw SageException.invalidCommand("ToDo");
                    }
                    break;
                case DEADLINE:
                    if (input.matches("^deadline\\s+(\\S.+?)\\s+/by\\s+(\\S.+)")) {
                        parts = input.split("/by");
                        if (parts.length == 2) {
                            String description = parts[0].replaceFirst("^deadline\\s+", "").trim(); // Remove "deadline" command
                            try {
                                LocalDate deadline = LocalDate.parse(parts[1].trim());
                                taskList.addTask(new Deadline(description, deadline));
                                Storage.saveTasks(taskList);
                                Ui.printAddedSuccess(taskList);
                            } catch (Exception e) {
                                throw SageException.invalidDate();
                            }
                        }
                    } else {
                        throw SageException.invalidCommand("Deadline");
                    }
                    break;
                case EVENT:
                    if (input.matches("^event\\s+(\\S.+?)\\s+/from\\s+(\\S.+?)\\s+/to\\s+(\\S.+)")) {
                        parts = input.split(" /from | /to ");
                        if (parts.length == 3) {
                            String description = parts[0].replaceFirst("^event\\s+", "").trim(); // Remove "event" command
                            try {
                                LocalDate from = LocalDate.parse(parts[1].trim());
                                LocalDate to = LocalDate.parse(parts[2].trim());
                                taskList.addTask(new Event(description, from, to));
                                Storage.saveTasks(taskList);
                                Ui.printAddedSuccess(taskList);
                            } catch (Exception e) {
                                throw SageException.invalidDate();
                            }
                        }
                    } else {
                        throw SageException.invalidCommand("Event");
                    }
                    break;
                case UNKNOWN:
                    throw SageException.unknownCommand();
                }
            } catch (Exception e) { // Catch all exceptions
                System.out.print(e.getMessage());
            }

            System.out.println();
            input = scanner.nextLine();
        }
        scanner.close();
    }
}