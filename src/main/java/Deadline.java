public class Deadline extends Task {
    private String deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[" + this.getTaskIcon() + "][" + this.getStatusIcon() + "] "
                + this.getDescription() + " (by: " + this.deadline + ")";
    }

    @Override
    public String getTaskIcon() {
        return "D";
    }
}