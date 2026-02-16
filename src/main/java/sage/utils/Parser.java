package sage.utils;

import java.time.LocalDate;
import java.util.Scanner;

import sage.SageException;
import sage.tasks.Task;
import sage.tasks.ToDo;
import sage.tasks.Deadline;
import sage.tasks.Event;
import sage.tasks.TaskList;

/**
 * Parses user input.
 */
public class Parser {
    protected String[] parts;
    protected String input;
    protected TaskList taskList;

    public Parser(TaskList taskList) {
        this.taskList = taskList;
    }

    public void parse() {
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();

        while (!input.equals("bye")) {
            try {
                parts = input.split(" ");
                CommandType commandType = CommandType.fromString(parts[0]);
                switch(commandType) {
                case LIST:
                    processListCommand();
                    break;
                case MARK:
                    processMarkCommand();
                    break;
                case UNMARK:
                    processUnmarkCommand();
                    break;
                case DELETE:
                    processDeleteCommand();
                    break;
                case TODO:
                    processTodoCommand();
                    break;
                case DEADLINE:
                    processDeadlineCommand();
                    break;
                case EVENT:
                    processEventCommand();
                    break;
                case FIND:
                    processFindCommand();
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

    public void processListCommand() {
        Ui.printTaskList(taskList);
    }

    public void processMarkCommand() throws SageException {
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
    }

    public void processUnmarkCommand() throws SageException {
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
    }

    public void processDeleteCommand() throws SageException {
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
    }

    public void processTodoCommand() throws SageException {
        if (parts.length > 1) {
            taskList.addTask(new ToDo(input.substring(5)));
            Storage.saveTasks(taskList);
            Ui.printAddedSuccess(taskList);
        } else {
            throw SageException.invalidCommand("ToDo");
        }
    }

    public void processDeadlineCommand() throws SageException {
        if (input.matches("^deadline\\s+(\\S.+?)\\s+/by\\s+(\\S.+)")) {
            String[] deadlinePart = input.split("/by");
            if (deadlinePart.length == 2) {
                String description = deadlinePart[0].replaceFirst("^deadline\\s+", "").trim(); // Remove "deadline" command
                try {
                    LocalDate deadline = LocalDate.parse(deadlinePart[1].trim());
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
    }

    public void processEventCommand() throws SageException {
        if (input.matches("^event\\s+(\\S.+?)\\s+/from\\s+(\\S.+?)\\s+/to\\s+(\\S.+)")) {
            String[] eventPart = input.split(" /from | /to ");
            if (eventPart.length == 3) {
                String description = eventPart[0].replaceFirst("^event\\s+", "").trim(); // Remove "event" command
                try {
                    LocalDate from = LocalDate.parse(eventPart[1].trim());
                    LocalDate to = LocalDate.parse(eventPart[2].trim());
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
    }

    public void processFindCommand() throws SageException {
        if (parts.length == 2) {
            TaskList foundList = taskList.findTask(parts[1].trim());
            Ui.printFoundList(foundList);
        } else {
            throw SageException.invalidCommand("Find");
        }
    }
}