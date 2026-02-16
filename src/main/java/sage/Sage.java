package sage;

import java.io.IOException;

import sage.utils.Parser;
import sage.utils.Storage;
import sage.utils.Ui;
import sage.tasks.TaskList;

public class Sage {
    public static void main(String[] args) throws SageException {
        Ui.printHello();

        TaskList taskList;
        // Try loading tasks from file at startup
        try {
            taskList = Storage.loadTasks();
        } catch (SageException e) {
            throw new SageException("Saved tasks could not be loaded. \nReason: " + e.getMessage());
        }

        Parser parser = new Parser(taskList);
        parser.parse();

        // Save tasks before exiting
        Storage.saveTasks(taskList);

        Ui.printGoodbye();
    }
}