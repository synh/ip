package sage;

import sage.utils.Parser;
import sage.utils.Storage;
import sage.utils.Ui;
import sage.tasks.TaskList;

public class Sage {
    protected TaskList taskList = new TaskList();
    protected Parser parser;

    public void loadTasks() throws SageException {
        // Try loading tasks from file at startup
        try {
            taskList = Storage.loadTasks();
        } catch (SageException e) {
            throw new SageException("Saved tasks could not be loaded. \nReason: " + e.getMessage());
        }
        parser = new Parser(taskList);
    }

    public String getHello() {
        return Ui.printHello();
    }

    public String getResponse(String input) {
        // Handle exit case
        if (input.equals("bye")) {
            return saveTasksAndExit();
        }

        // Handle all other cases including exceptions
        try {
            return parser.parse(input);
        } catch (SageException e) {
            return e.getMessage();
        }
    }

    /**
     * Tries to save tasks before exiting.
     * If saving fails, will show error message, but exits anyway.
     */
    public String saveTasksAndExit() {
        String output = "";
        try {
            Storage.saveTasks(taskList);
        } catch (SageException e) {
            output += e.getMessage() + System.lineSeparator();
        }
        output += Ui.printGoodbye();
        return output;
    }
}