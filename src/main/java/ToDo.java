public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[" + this.getTaskIcon() + "]" + super.toString();
    }

    @Override
    public String getTaskIcon() {
        return "T";
    }
}