package BobMortimer.tasks;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {

    @Test
    public void addTaskTest() {
        TaskList tasksList = new TaskList(new ArrayList<>());
        tasksList.add(new TaskToDo("Testing"));
        assertEquals(1, tasksList.size());
    }

    @Test
    public void removeTaskTest() {
        TaskList tasksList = new TaskList(new ArrayList<>());
        tasksList.add(new TaskToDo("Testing"));
        tasksList.remove(0);
        assertEquals(0, tasksList.size());
    }

}