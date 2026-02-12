package sage.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    @Test
    public void testStringConversion() {
        Deadline deadline = new Deadline("buy books", LocalDate.parse("2026-12-31"));
        assertEquals("[D][ ] buy books (by: 31 Dec 2026)", deadline.toString());
    }
}
