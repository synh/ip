package sage.utils;

import java.time.LocalDate;

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

    public String parse(String input) throws SageException {
        parts = input.split(" ");
        CommandType commandType = CommandType.fromString(parts[0]);
        switch(commandType) {
        case LIST:
            return processListCommand();
        case MARK:
            return processMarkCommand();
        case UNMARK:
            return processUnmarkCommand();
        case DELETE:
            return processDeleteCommand();
        case TODO:
            return processTodoCommand();
        case DEADLINE:
            return processDeadlineCommand();
        case EVENT:
            return processEventCommand();
        case FIND:
            return processFindCommand();
        default:
            throw SageException.unknownCommand();
        }
    }

    public String processListCommand() {
        return Ui.printTaskList(taskList);
    }

    public String processMarkCommand() throws SageException {
        if (parts.length == 2 && parts[1].matches("[0-9]+")) {
            // Validate task number exists
            int index = Integer.parseInt(parts[1]); // 1-based indexing
            if (1 <= index && index <= taskList.getSize()) {
                Task task = taskList.getTask(index - 1);
                task.markAsDone();
                Storage.saveTasks(taskList);
                return Ui.printMarkSuccess(task, index);
            } else {
                throw SageException.invalidTaskNumber();
            }
        } else {
            throw SageException.invalidCommand("Mark");
        }
    }

    public String processUnmarkCommand() throws SageException {
        if (parts.length == 2 && parts[1].matches("[0-9]+")) {
            // Validate task number exists
            int index = Integer.parseInt(parts[1]); // 1-based indexing
            if (1 <= index && index <= taskList.getSize()) {
                Task task = taskList.getTask(index - 1);
                task.markAsUndone();
                Storage.saveTasks(taskList);
                return Ui.printUnmarkSuccess(task, index);
            } else {
                throw SageException.invalidTaskNumber();
            }
        } else {
            throw SageException.invalidCommand("Unmark");
        }
    }

    public String processDeleteCommand() throws SageException {
        if (parts.length == 2 && parts[1].matches("[0-9]+")) {
            // Validate task number exists
            int index = Integer.parseInt(parts[1]); // 1-based indexing
            if (1 <= index && index <= taskList.getSize()) {
                Task task = taskList.getTask(index - 1);
                taskList.deleteTask(index - 1);
                Storage.saveTasks(taskList);
                return Ui.printDeleteSuccess(task, index, taskList);
            } else {
                throw SageException.invalidTaskNumber();
            }
        } else {
            throw SageException.invalidCommand("Delete");
        }
    }

    public String processTodoCommand() throws SageException {
        if (parts.length > 1) {
            taskList.addTask(new ToDo(input.substring(5)));
            Storage.saveTasks(taskList);
            return Ui.printAddedSuccess(taskList);
        } else {
            throw SageException.invalidCommand("ToDo");
        }
    }

    public String processDeadlineCommand() throws SageException {
        if (input.matches("^deadline\\s+(\\S.+?)\\s+/by\\s+(\\S.+)")) {
            String[] deadlinePart = input.split("/by");
            if (deadlinePart.length == 2) {
                String description = deadlinePart[0].replaceFirst("^deadline\\s+", "").trim(); // Remove "deadline" command
                try {
                    LocalDate deadline = LocalDate.parse(deadlinePart[1].trim());
                    taskList.addTask(new Deadline(description, deadline));
                    Storage.saveTasks(taskList);
                    return Ui.printAddedSuccess(taskList);
                } catch (Exception e) {
                    throw SageException.invalidDate();
                }
            }
        }
        throw SageException.invalidCommand("Deadline");
    }

    public String processEventCommand() throws SageException {
        if (input.matches("^event\\s+(\\S.+?)\\s+/from\\s+(\\S.+?)\\s+/to\\s+(\\S.+)")) {
            String[] eventPart = input.split(" /from | /to ");
            if (eventPart.length == 3) {
                String description = eventPart[0].replaceFirst("^event\\s+", "").trim(); // Remove "event" command
                try {
                    LocalDate from = LocalDate.parse(eventPart[1].trim());
                    LocalDate to = LocalDate.parse(eventPart[2].trim());
                    taskList.addTask(new Event(description, from, to));
                    Storage.saveTasks(taskList);
                    return Ui.printAddedSuccess(taskList);
                } catch (Exception e) {
                    throw SageException.invalidDate();
                }
            }
        }
        throw SageException.invalidCommand("Event");
    }

    public String processFindCommand() throws SageException {
        if (parts.length == 2) {
            TaskList foundList = taskList.findTask(parts[1].trim());
            return Ui.printFoundList(foundList);
        } else {
            throw SageException.invalidCommand("Find");
        }
    }
}