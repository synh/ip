public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

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