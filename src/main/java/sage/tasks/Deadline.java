package sage.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Stores a Task that has a deadline.
 */
public class Deadline extends Task {
    protected LocalDate deadline;

    public Deadline(String description, LocalDate deadline) {
        super(description);
        this.deadline = deadline;
    }

    public LocalDate getDeadline() {
        return this.deadline;
    }

    /**
     * Returns Deadline formatted as a String.
     * Includes deadline indicator "D", description and deadline.
     *
     * @return Deadline formatted as a String.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
        return "[D]" + super.toString()
                + " (by: " + this.deadline.format(formatter) + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + deadline;
    }
}