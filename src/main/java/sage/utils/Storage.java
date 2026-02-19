package sage.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import java.time.LocalDate;

import sage.SageException;
import sage.tasks.Task;
import sage.tasks.ToDo;
import sage.tasks.Deadline;
import sage.tasks.Event;
import sage.tasks.TaskList;

/**
 * Handles storage of TaskList into a text file.
 */
public class Storage {
    private static final String FILE_PATH = "./data/sage.txt";
    private static final String DIRECTORY_PATH = "./data/";

    /**
     * Loads tasks from file.
     *
     * @return List of tasks loaded from file.
     * @throws SageException If error reading from file.
     */
    public static TaskList loadTasks() throws SageException {
        TaskList tasks = new TaskList();

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                processLineFromFile(line, tasks);
                Sorter.sortTasks(tasks); // Sort in case file is unsorted
            }
        } catch (IOException e) {
            throw new SageException("Could not read from file.");
        }

        return tasks;
    }

    /**
     * Saves all tasks to file.
     *
     * @throws SageException If error writing to file.
     */
    public static void saveTasks(TaskList tasks) throws SageException {
        // Create directory if it doesn't exist
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Write tasks to file
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            for (int i = 0; i < tasks.getSize(); i++) {
                String fileString = tasks.getTask(i).toFileString();
                writer.write(fileString + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            throw new SageException("Tasks could not be saved.");
        }
    }

    private static void processLineFromFile(String line, TaskList tasks) throws SageException {
        try {
            Task task = parseTaskFromFile(line);
            if (task != null) {
                tasks.addTask(task);
            }
        } catch (Exception e) {
            throw new SageException("Could not parse line: " + line);
        }
    }

    /**
     * Parses a file line and returns a Task subclass object.
     *
     * @return Task subclass.
     */
    private static Task parseTaskFromFile(String line) {
        String[] parts = line.split(" \\| ");

        // Invalid format
        if (parts.length < 3) {
            return null;
        }

        // Create task object
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
                task = new Deadline(description, LocalDate.parse(parts[3]));
            }
            break;
        case "E":
            if (parts.length >= 5) {
                task = new Event(description, LocalDate.parse(parts[3]), LocalDate.parse(parts[4]));
            }
            break;
        }

        if (task != null && isDone) {
            task.markAsDone();
        }

        return task;
    }
}
