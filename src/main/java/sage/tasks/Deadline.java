package sage.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDate deadline;

    public Deadline(String description, LocalDate deadline) {
        super(description);
        this.deadline = deadline;
    }

    public LocalDate getDeadline() {
        return this.deadline;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
        return "[D]" + super.toString()
                + " (by: " + this.deadline.format(formatter) + ")";
    }
}