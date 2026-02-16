package sage.utils;

import sage.tasks.Task;
import sage.tasks.TaskList;

/**
 * Handles all statements printed to the user.
 */
public class Ui {

    public static String printHello() {
        return "Hello there, Sage here.\n How are you doing?";
    }

    /**
     * Prints each task in list if list is not empty.
     */
    public static String printTaskList(TaskList tasklist) {
        if (tasklist.isEmpty()) {
            return "Oh, you have nothing you set out to do. Enjoy your day.";
        } else {
            return "These are what you set out to do:\n"
                    + tasklist.printTaskList();
        }
    }

    public static String printFoundList(TaskList foundList) {
        if (foundList.isEmpty()) {
            return "Oh, there are no matching tasks.";
        } else {
            return "Here are the matching tasks in your list:\n"
                    + foundList.printTaskList();
        }
    }

    /**
     * Prints statement indicating successful mark.
     *
     * @param task Task that was marked.
     * @param index 1-indexed position of task in TaskList.
     */
    public static String printMarkSuccess(Task task, int index) {
        return "Got it. I've marked \"" + index + ". "
                + task.getDescription() + "\" as done.";
    }

    /**
     * Prints statement indicating successful unmark.
     *
     * @param task Task that was unmarked.
     * @param index 1-indexed position of task in TaskList.
     */
    public static String printUnmarkSuccess(Task task, int index) {
        return "Got it. I've marked \"" + index + ". "
                + task.getDescription() + "\" as undone.";
    }

    /**
     * Prints statement indicating successful delete.
     *
     * @param task Task that was deleted.
     * @param index original 1-indexed position of task in TaskList.
     */
    public static String printDeleteSuccess(Task task, int index, TaskList taskList) {
        return "Got it. I've removed \"" + index + ". "
                + task.getDescription() + "\"."
                + "\nYou've now set out to do " + taskList.getSize() + " thing(s).";
    }

    public static String printAddSuccess(TaskList taskList) {
        int listSize = taskList.getSize();
        return "Got it. I've added this task:\n"
                + taskList.getTask(listSize - 1)
                + "\nYou've now set out to do " + listSize + " thing(s).";
    }

    public static String printGoodbye() {
        return "Goodbye. Have a beautiful day.";
    }
}
