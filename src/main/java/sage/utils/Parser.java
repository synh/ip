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
    protected TaskList taskList;

    public Parser(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Parses user input and returns chatbot response.
     * @param input user input
     * @return String containing chatbot response.
     * @throws SageException if parsing fails.
     */
    public String parse(String input) throws SageException {
        String[] parts = input.split(" ");

        CommandType commandType = CommandType.fromString(parts[0]);

        switch (commandType) {
        case LIST:
            return processListCommand();
        case MARK:
            return processMarkCommand(parts);
        case UNMARK:
            return processUnmarkCommand(parts);
        case DELETE:
            return processDeleteCommand(parts);
        case TODO:
            return processTodoCommand(input, parts);
        case DEADLINE:
            return processDeadlineCommand(input);
        case EVENT:
            return processEventCommand(input);
        case FIND:
            return processFindCommand(parts);
        default:
            throw new SageException("I'm afraid I didn't catch that.");
        }
    }


    /**
     * Handles list command.
     *
     * @return String of formatted list.
     */
    public String processListCommand() {
        return Ui.printTaskList(taskList);
    }

    /**
     * Handles mark task command.
     * @param parts user input split by whitespace.
     * @return String declaring success of mark command.
     * @throws SageException
     */
    public String processMarkCommand(String[] parts) throws SageException {
        validateTaskNumberFormat(parts, "Mark");

        int index = Integer.parseInt(parts[1]); // 1-based indexing
        validateTaskNumberExists(index);

        // Process valid mark command
        Task task = taskList.getTask(index - 1);
        task.markAsDone();
        Sorter.sortTasks(taskList);
        Storage.saveTasks(taskList);
        return Ui.printMarkSuccess(task, index);
    }

    /**
     * Handles unmark task command.
     * @param parts user input split by whitespace.
     * @return String declaring success of unmark command.
     * @throws SageException
     */
    public String processUnmarkCommand(String[] parts) throws SageException {
        validateTaskNumberFormat(parts, "Unmark");

        int index = Integer.parseInt(parts[1]); // 1-based indexing
        validateTaskNumberExists(index);

        // Process valid unmark command
        Task task = taskList.getTask(index - 1);
        task.markAsUndone();
        Sorter.sortTasks(taskList);
        Storage.saveTasks(taskList);
        return Ui.printUnmarkSuccess(task, index);
    }

    /**
     * Handles delete task command.
     * @param parts user input split by whitespace.
     * @return String declaring success of delete command.
     * @throws SageException
     */
    public String processDeleteCommand(String[] parts) throws SageException {
        validateTaskNumberFormat(parts, "Delete");

        int index = Integer.parseInt(parts[1]); // 1-based indexing
        validateTaskNumberExists(index);

        // Process valid delete command
        Task task = taskList.getTask(index - 1);
        taskList.deleteTask(index - 1);
        Storage.saveTasks(taskList);
        return Ui.printDeleteSuccess(task, index, taskList);
    }

    /**
     * Handles create todo task command.
     * @param input user input.
     * @param parts user input split by whitespace.
     * @return String declaring success of create command.
     * @throws SageException
     */
    public String processTodoCommand(String input, String[] parts) throws SageException {
        // Validate command format
        if (parts.length <= 1) {
            throw SageException.invalidCommand("ToDo");
        }

        // Process valid todo command
        return addTodoTask(input);
    }

    /**
     * Handles create deadline task command.
     * @return String declaring success of create command.
     * @throws SageException
     */
    public String processDeadlineCommand(String input) throws SageException {
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

    /**
     * Handles create event task command.
     * @return String declaring success of create command.
     * @throws SageException
     */
    public String processEventCommand(String input) throws SageException {
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

    /**
     * Handles find task command.
     * @param parts user input split by whitespace.
     * @return String of formatted list of tasks found.
     * @throws SageException
     */
    public String processFindCommand(String[] parts) throws SageException {
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
    public void validateTaskNumberFormat(String[] parts, String command) throws SageException {
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

    /**
     * Converts String into LocalDate object if possible.
     */
    public static LocalDate parseDateFormat(String dateString) throws SageException {
        try {
            return LocalDate.parse(dateString.trim());
        } catch (Exception e) {
            throw SageException.invalidDate();
        }
    }



    public String addTodoTask(String input) throws SageException {
        ToDo task = new ToDo(input.substring(5));
        taskList.addTask(task);
        Storage.saveTasks(taskList);
        return Ui.printAddSuccess(taskList, task);
    }

    public String addDeadlineTask(String[] deadlinePart) throws SageException {
        String description = deadlinePart[0].replaceFirst("^deadline\\s+", "").trim(); // Remove "deadline" command
        LocalDate deadline = parseDateFormat(deadlinePart[1].trim());
        Deadline task = new Deadline(description, deadline);
        taskList.addTask(task);
        Storage.saveTasks(taskList);
        return Ui.printAddSuccess(taskList, task);
    }

    public String addEventTask(String[] eventPart) throws SageException {
        String description = eventPart[0].replaceFirst("^event\\s+", "").trim(); // Remove "event" command

        LocalDate from = parseDateFormat(eventPart[1].trim());
        LocalDate to = parseDateFormat(eventPart[2].trim());
        Event task = new Event(description, from, to);
        taskList.addTask(task);
        Storage.saveTasks(taskList);
        return Ui.printAddSuccess(taskList, task);
    }
}