package sage.utils;

/**
 * Enum representing all valid command types for the Sage chatbot.
 */
public enum CommandType {
    LIST, BYE, MARK, UNMARK, DELETE,
    TODO, DEADLINE, EVENT, UNKNOWN;

    /**
     * Parses a string input to determine the command type.
     *
     * @param input The user input string.
     * @return The corresponding CommandType, or UNKNOWN if not recognised.
     */
    public static CommandType fromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return UNKNOWN;
        }

        String firstWord = input.split(" ")[0].toLowerCase();

        switch (firstWord) {
        case "list":
            return LIST;
        case "bye":
            return BYE;
        case "mark":
            return MARK;
        case "unmark":
            return UNMARK;
        case "delete":
            return DELETE;
        case "todo":
            return TODO;
        case "deadline":
            return DEADLINE;
        case "event":
            return EVENT;
        default:
            return UNKNOWN;
        }
    }
}