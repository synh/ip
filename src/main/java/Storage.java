import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final String FILE_PATH = "./data/sage.txt";
    private static final String DIRECTORY_PATH = "./data/";

    /**
     * Loads tasks from file.
     *
     * @return List of tasks loaded from file
     * @throws IOException If error reading from file
     */
    public static ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return tasks; // Return empty list if file doesn't exist
        }

        List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));

        for (String line : lines) {
            try {
                Task task = parseTaskFromFile(line);
                if (task != null) {
                    tasks.add(task);
                }
            } catch (Exception e) {
                System.out.println("Warning: Could not parse line: " + line);
            }
        }

        return tasks;
    }

    /**
     * Saves all tasks to file.
     *
     * @param tasks The array of tasks to save
     * @throws IOException If error writing to file
     */
    public static void saveTasks(ArrayList<Task> tasks) throws IOException {
        // Create directory if it doesn't exist
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Write tasks to file
        FileWriter writer = new FileWriter(FILE_PATH);
        for (Task task : tasks) {
            writer.write(taskToFileString(task) + System.lineSeparator());
        }
        writer.close();
    }

    /**
     * Parses a file line and returns a Task subclass object.
     *
     * @return Task subclass
     */
    private static Task parseTaskFromFile(String line) {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            return null; // Invalid format
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = null;

        switch (type) {
        case "T":
            task = new ToDo(description);
            break;
        case "D":
            if (parts.length >= 4) {
                task = new Deadline(description, parts[3]);
            }
            break;
        case "E":
            if (parts.length >= 5) {
                task = new Event(description, parts[3], parts[4]);
            }
            break;
        }

        if (task != null && isDone) {
            task.markAsDone();
        }

        return task;
    }

    /**
     * Converts and returns a task to file format string.
     *
     * @param task Task to convert
     * @return Task in string format
     */
    private static String taskToFileString(Task task) {
        String type = task.getTaskIcon(); // T, D, or E
        String isDone = task.getIsDone() ? "1" : "0";
        String description = task.getDescription();

        if (task instanceof ToDo) {
            return type + " | " + isDone + " | " + description;
        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return type + " | " + isDone + " | " + description + " | " + deadline.deadline;
        } else if (task instanceof Event) {
            Event event = (Event) task;
            return type + " | " + isDone + " | " + description + " | " + event.start + " | " + event.end;
        }
        return ""; // Should not reach here
    }
}
