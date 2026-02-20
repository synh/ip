package sage.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    @Test
    public void testStringConversion() {
        Deadline deadline1 = new Deadline("buy books", LocalDate.parse("2026-12-31"));
        assertEquals("[D][ ] buy books (by: 31 Dec 2026)", deadline1.toString());

        Deadline deadline2 = new Deadline("buy more books", LocalDate.parse("2026-02-28"));
        assertEquals("[D][ ] buy more books (by: 28 Feb 2026)", deadline2.toString());
    }
}