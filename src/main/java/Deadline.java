import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDate deadline;

    public Deadline(String description, LocalDate deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[" + this.getTaskIcon() + "]" + super.toString()
                + " (by: " + this.deadline.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ")";
    }

    @Override
    public String getTaskIcon() {
        return "D";
    }
}