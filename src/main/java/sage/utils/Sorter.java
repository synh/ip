package sage.utils;

import sage.tasks.*;

import java.util.ArrayList;

/**
 * Provides sorting functionality for TaskList according to specified rules.
 * The sorting hierarchy is:
 * 1. Task type order: ToDo → Deadline → Event
 * 2. Within each type: unmarked tasks before marked tasks
 * 3. For Deadlines: earliest deadline first
 * 4. For Events: earliest start date first
 * 5. For any remaining ties: alphabetical order by description
 */
public class Sorter {

    /**
     * Sorts the tasks in the given TaskList according to the specified ordering rules.
     * The original list is modified in-place.
     *
     * @param taskList The TaskList whose tasks should be sorted. Must not be null.
     */
    public static void sortTasks(TaskList taskList) {
        // Access the internal ArrayList through a getter method
        ArrayList<Task> tasks = taskList.getTasks();

        tasks.sort((t1, t2) -> {
            // 1. Compare by task type priority
            int typeComparison = compareTaskType(t1, t2);
            if (typeComparison != 0) {
                return typeComparison;
            }

            // 2. Same type - compare by completion status (unmarked before marked)
            if (t1.getIsDone() != t2.getIsDone()) {
                return t1.getIsDone() ? 1 : -1; // false (unmarked) comes before true (marked)
            }

            // 3. Same type and completion status - compare by type-specific criteria
            if (t1 instanceof Deadline && t2 instanceof Deadline) {
                int deadlineComparison = compareDeadlines((Deadline) t1, (Deadline) t2);
                if (deadlineComparison != 0) {
                    return deadlineComparison;
                }
            } else if (t1 instanceof Event && t2 instanceof Event) {
                int eventComparison = compareEvents((Event) t1, (Event) t2);
                if (eventComparison != 0) {
                    return eventComparison;
                }
            }

            // 4. If still tied, sort alphabetically by description
            return compareAlphabetically(t1, t2);
        });
    }

    /**
     * Compares task types to establish priority order: ToDo → Deadline → Event.
     *
     * @param t1 First task to compare.
     * @param t2 Second task to compare.
     * @return Negative integer if t1 should come before t2,
     *         positive integer if t1 should come after t2,
     *         zero if they are the same type.
     */
    private static int compareTaskType(Task t1, Task t2) {
        return Integer.compare(getTypePriority(t1), getTypePriority(t2));
    }

    /**
     * Gets the priority value for a task type.
     * Lower number indicates higher priority (appears first in sorted list).
     *
     * @param task The task whose type priority is needed.
     * @return Priority value: 1 for ToDo, 2 for Deadline, 3 for Event, 4 for unknown types.
     */
    private static int getTypePriority(Task task) {
        if (task instanceof ToDo) return 1;
        if (task instanceof Deadline) return 2;
        if (task instanceof Event) return 3;
        return 4; // Default for unknown types
    }

    /**
     * Compares two Deadlines by their deadline date.
     * Earlier deadlines are considered smaller (come first in sorted list).
     *
     * @param d1 First Deadline to compare.
     * @param d2 Second Deadline to compare.
     * @return Negative if d1 has earlier deadline, positive if later, zero if same date.
     */
    private static int compareDeadlines(Deadline d1, Deadline d2) {
        return d1.getDeadline().compareTo(d2.getDeadline());
    }

    /**
     * Compares two Events by their start date.
     * Earlier start dates are considered smaller (come first in sorted list).
     *
     * @param e1 First Event to compare.
     * @param e2 Second Event to compare.
     * @return Negative if e1 has earlier start, positive if later, zero if same start date.
     */
    private static int compareEvents(Event e1, Event e2) {
        return e1.getStart().compareTo(e2.getStart());
    }

    /**
     * Compares two tasks alphabetically by their description.
     * Comparison is case-insensitive.
     *
     * @param t1 First task to compare.
     * @param t2 Second task to compare.
     * @return Negative if t1 comes before t2 alphabetically,
     *         positive if after, zero if descriptions are identical.
     */
    private static int compareAlphabetically(Task t1, Task t2) {
        return t1.getDescription().compareToIgnoreCase(t2.getDescription());
    }
}