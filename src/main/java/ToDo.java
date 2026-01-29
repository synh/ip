public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[" + this.getTaskIcon() + "][" + this.getStatusIcon() + "] " + this.getDescription();
    }

    @Override
    public String getTaskIcon() {
        return "T";
    }
}