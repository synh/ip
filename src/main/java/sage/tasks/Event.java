package sage.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Stores a Task that has a start and end date.
 */
public class Event extends Task {
    protected LocalDate start;
    protected LocalDate end;

    public Event(String description, LocalDate start, LocalDate end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return this.start;
    }

    public LocalDate getEnd() {
        return this.end;
    }

    /**
     * Returns Event formatted as a String.
     * Includes event indicator "E", description, start date and end date.
     *
     * @return Event formatted as a String.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
        return "[E]" + super.toString()
                + " (from: " + this.start.format(formatter)
                + " to: " + this.end.format(formatter) + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + start + " | " + end;
    }
}