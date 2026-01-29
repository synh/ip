public abstract class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Returns icon to demarcate type of task.
     * @return "T" for ToDo, "D" for Deadline, "E" for Event.
     */
    public abstract String getTaskIcon();

    public boolean getIsDone() {
        return this.isDone;
    }

    /**
     * Returns icon to demarcate completion of task.
     *
     * @return "X" if task is done, else " ".
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks task as done
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks task as undone
     */
    public void markAsUndone() {
        this.isDone = false;
    }
}