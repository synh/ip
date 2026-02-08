import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;

public class Sage {
    public static void main(String[] args) throws SageException, IOException {
        Ui.printHello();

        TaskList taskList = new TaskList();

        // Try loading tasks from file at startup
        try {
            taskList = Storage.loadTasks();
        } catch (IOException e) {
            System.out.print("Saved tasks could not be loaded.");
        }

        Parser.parse(taskList);

        // Save tasks before exiting
        try {
            Storage.saveTasks(taskList);
        } catch (IOException e) {
            System.out.print("Tasks could not be saved.");
        }

        Ui.printGoodbye();
    }
}