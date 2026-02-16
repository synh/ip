package sage.tasks;

import java.util.ArrayList;

/**
 * Stores a list of Tasks, with functionality for reading and modifying the list.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns number of tasks in TaskList.
     *
     * @return number of tasks.
     */
    public int getSize() {
        return tasks.size();
    }

    /** Returns whether TaskList is empty.
     *
     * @return True if empty, false otherwise.
     */
    public boolean isEmpty() {
        return getSize() == 0;
    }

    public Task getTask(int index) {
        return tasks.get(index); //! passing by reference, not safe?
    }

    public String getDescription(int index) {
        return tasks.get(index).getDescription();
    }

    /**
     * Appends Task to end of TaskList.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes Task from TaskList.
     *
     * @param index Zero-indexed position of Task in TaskList to be deleted.
     */
    public void deleteTask(int index) {
        tasks.remove(index);
    }

    public TaskList findTask(String keyword) {
        TaskList foundList = new TaskList();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                foundList.addTask(task);
            }
        }
        return foundList;
    }

    public String printTaskList() {
        String output = "";
        int index = 1;
        for (Task task : tasks) {
            output += index + ". " + task + "\n";
            index++;
        }
        return output.substring(0, output.length() - 1); // Remove last "\n"
    }
}
