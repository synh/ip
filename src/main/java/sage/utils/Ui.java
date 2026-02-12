package sage.utils;

import sage.tasks.Task;
import sage.tasks.TaskList;

/**
 * Handles all statements printed to the user.
 */
public class Ui {
    public static void printHello() {
        System.out.println("Hello there, Sage here.");
        System.out.println("How are you doing?");
    }

    /**
     * Prints each task in list if list is not empty.
     */
    public static void printTaskList(TaskList tasklist) {
        if (tasklist.isEmpty()) {
            System.out.println("Oh, you have nothing you set out to do. Enjoy your day.");
        } else {
            System.out.println("These are what you set out to do:");
            tasklist.printTaskList();
        }
    }

    /**
     * Prints statement indicating successful mark.
     *
     * @param task Task that was marked.
     * @param index 1-indexed position of task in TaskList.
     */
    public static void printMarkSuccess(Task task, int index) {
        System.out.println("Got it. I've marked \"" + index + ". "
                + task.getDescription() + "\" as done.");
    }

    /**
     * Prints statement indicating successful unmark.
     *
     * @param task Task that was unmarked.
     * @param index 1-indexed position of task in TaskList.
     */
    public static void printUnmarkSuccess(Task task, int index) {
        System.out.println("Got it. I've marked \"" + index + ". "
                + task.getDescription() + "\" as undone.");
    }

    /**
     * Prints statement indicating successful delete.
     *
     * @param task Task that was deleted.
     * @param index original 1-indexed position of task in TaskList.
     */
    public static void printDeleteSuccess(Task task, int index, TaskList taskList) {
        System.out.println("Got it. I've removed \"" + index + ". "
                + task.getDescription() + "\"."
                + "\nYou've now set out to do " + String.valueOf(taskList.getSize()) + " thing(s).");
    }

    public static void printAddedSuccess(TaskList taskList) {
        int listSize = taskList.getSize();
        System.out.println("Got it. I've added this task:\n"
                + taskList.getTask(listSize - 1)
                + "\nYou've now set out to do " + String.valueOf(listSize) + " thing(s).");
    }

    public static void printGoodbye() {
        System.out.println("Goodbye. Have a beautiful day.");
    }
}
