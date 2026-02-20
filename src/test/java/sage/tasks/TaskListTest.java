package sage.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TaskListTest {
    @Test
    public void testDeleteSuccess() {
        ArrayList<Task> taskArray = new ArrayList<Task>();
        taskArray.add(new ToDo("todo1"));

        TaskList taskList = new TaskList(taskArray);
        taskList.deleteTask(0);

        assertEquals(0, taskList.getSize());

        ArrayList<Task> taskArray2 = new ArrayList<Task>();
        taskArray.add(new ToDo("todo1"));
        taskArray.add(new ToDo("todo2"));
        taskArray.add(new ToDo("todo3"));

        taskList = new TaskList(taskArray);
        taskList.deleteTask(0);
        taskList.deleteTask(0);

        assertEquals(1, taskList.getSize());

    }
}
