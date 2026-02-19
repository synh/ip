package sage;

public class SageException extends Exception {
    public SageException(String message) {
        super(message);
    }

    /**
     * Returns SageException with error message for invalid task number for Mark and Unmark commands.
     *
     * @return SageException.
     */
    public static SageException invalidTaskNumber() {
        return new SageException("That task doesn't exist, apparently.");
    }

    /**
     * Returns SageException with error message for Mark, Unmark, ToDo, Deadline, Event commands with invalid format.
     *
     * @return SageException.
     */
    public static SageException invalidCommand(String command) {
        return new SageException("That's an invalid " + command + ".");
    }

    /**
     * Returns SageException with error message for invalid date format.
     *
     * @return SageException.
     */
    public static SageException invalidDate() {
        return new SageException("That's an invalid date. Date should be in YYYY-MM-DD format.");
    }
}
