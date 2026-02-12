package sage.utils;

import sage.tasks.Task;
import sage.tasks.TaskList;

public class Ui {
    public static void printHello() {
        System.out.println("Hello there, Sage here.");
        System.out.println("How are you doing?");
    }

    public static void printTaskList(TaskList tasklist) {
        if (tasklist.isEmpty()) {
            System.out.println("Oh, you have nothing you set out to do. Enjoy your day.");
        } else {
            System.out.println("These are what you set out to do:");
            tasklist.printTaskList();
        }
    }

    public static void printFoundList(TaskList foundList) {
        if (foundList.isEmpty()) {
            System.out.println("Oh, there are no matching tasks.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            foundList.printTaskList();
        }
    }

    public static void printMarkSuccess(Task task, int index) {
        System.out.println("Got it. I've marked \"" + index + ". "
                + task.getDescription() + "\" as done.");
    }

    public static void printUnmarkSuccess(Task task, int index) {
        System.out.println("Got it. I've marked \"" + index + ". "
                + task.getDescription() + "\" as undone.");
    }

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
