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
        switch (commandType) {
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
            throw new SageException("I'm afraid I didn't catch that.\n");
        }
    }



    public String processListCommand() {
        return Ui.printTaskList(taskList);
    }

    public String processMarkCommand() throws SageException {
        validateTaskNumberFormat("Mark");

        int index = Integer.parseInt(parts[1]); // 1-based indexing
        validateTaskNumberExists(index);

        // Process valid mark command
        Task task = taskList.getTask(index - 1);
        task.markAsDone();
        Storage.saveTasks(taskList);
        return Ui.printMarkSuccess(task, index);
    }

    public String processUnmarkCommand() throws SageException {
        validateTaskNumberFormat("Unmark");

        int index = Integer.parseInt(parts[1]); // 1-based indexing
        validateTaskNumberExists(index);

        // Process valid unmark command
        Task task = taskList.getTask(index - 1);
        task.markAsUndone();
        Storage.saveTasks(taskList);
        return Ui.printUnmarkSuccess(task, index);
    }

    public String processDeleteCommand() throws SageException {
        validateTaskNumberFormat("Delete");

        int index = Integer.parseInt(parts[1]); // 1-based indexing
        validateTaskNumberExists(index);

        // Process valid delete command
        Task task = taskList.getTask(index - 1);
        taskList.deleteTask(index - 1);
        Storage.saveTasks(taskList);
        return Ui.printDeleteSuccess(task, index, taskList);
    }

    public String processTodoCommand() throws SageException {
        // Validate command format
        if (parts.length <= 1) {
            throw SageException.invalidCommand("ToDo");
        }

        // Process valid todo command
        return addTodoTask();
    }

    public String processDeadlineCommand() throws SageException {
        // Validate command format by regex
        if (!input.matches("^deadline\\s+(\\S.+?)\\s+/by\\s+(\\S.+)")) {
            throw SageException.invalidCommand("Deadline");
        }

        String[] deadlinePart = input.split("/by");

        // Validate command format after splitting
        if (deadlinePart.length != 2) {
            throw SageException.invalidCommand("Deadline");
        }

        // Process valid deadline command
        return addDeadlineTask(deadlinePart);
    }

    public String processEventCommand() throws SageException {
        // Validate command format by regex
        if (!input.matches("^event\\s+(\\S.+?)\\s+/from\\s+(\\S.+?)\\s+/to\\s+(\\S.+)")) {
            throw SageException.invalidCommand("Event");
        }

        String[] eventPart = input.split(" /from | /to ");

        // Validate command format after splitting
        if (eventPart.length != 3) {
            throw SageException.invalidCommand("Event");
        }

        // Process valid event command
        return addEventTask(eventPart);
    }

    public String processFindCommand() throws SageException {
        // Validate command format
        if (parts.length != 2) {
            throw SageException.invalidCommand("Find");
        }

        // Process valid find command
        TaskList foundList = taskList.findTask(parts[1].trim());
        return Ui.printFoundList(foundList);
    }



    /**
     * Validates format of commands including index of task already in tasklist.
     * List of commands: Mark, Unmark, Delete.
     *
     * @param command Command to be validated with first letter capitalised.
     * @throws SageException
     */
    public void validateTaskNumberFormat(String command) throws SageException {
        if (parts.length != 2 || !parts[1].matches("[0-9]+")) {
            throw SageException.invalidCommand(command);
        }
    }

    /**
     * Validate that task is in tasklist.
     *
     * @param index 1-based indexing.
     * @throws SageException
     */
    public void validateTaskNumberExists(int index) throws SageException {
        if (index < 1 || index > taskList.getSize()) {
            throw SageException.invalidTaskNumber();
        }
    }

    public static LocalDate parseDateFormat(String dateString) throws SageException {
        try {
            return LocalDate.parse(dateString);
        } catch (Exception e) {
            throw SageException.invalidDate();
        }
    }



    public String addTodoTask() throws SageException {
        taskList.addTask(new ToDo(input.substring(5)));
        Storage.saveTasks(taskList);
        return Ui.printAddSuccess(taskList);
    }

    public String addDeadlineTask(String[] deadlinePart) throws SageException {
        String description = deadlinePart[0].replaceFirst("^deadline\\s+", "").trim(); // Remove "deadline" command
        LocalDate deadline = parseDateFormat(deadlinePart[1].trim());
        taskList.addTask(new Deadline(description, deadline));
        Storage.saveTasks(taskList);
        return Ui.printAddSuccess(taskList);
    }

    public String addEventTask(String[] eventPart) throws SageException {
        String description = eventPart[0].replaceFirst("^event\\s+", "").trim(); // Remove "event" command

        LocalDate from = parseDateFormat(eventPart[1].trim());
        LocalDate to = parseDateFormat(eventPart[2].trim());
        taskList.addTask(new Event(description, from, to));
        Storage.saveTasks(taskList);
        return Ui.printAddSuccess(taskList);
    }
}