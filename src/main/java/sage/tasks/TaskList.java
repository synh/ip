package sage.tasks;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int getSize() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    public Task getTask(int index) {
        return tasks.get(index); //! passing by reference, not safe?
    }

    public String getDescription(int index) {
        return tasks.get(index).getDescription();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(int index) {
        tasks.remove(index);
    }

    public void printTaskList() {
        int index = 1;
        for (Task task : tasks) {
            System.out.println(index + ". " + task);
            index++;
        }
    }
}
