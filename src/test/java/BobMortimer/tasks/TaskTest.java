package BobMortimer.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {

    @Test
    public void markTaskTest() {
        Task task = new TaskToDo("Testing");
        task.markAsDone();
        assertEquals(true,task.getisDone());
    }

    @Test
    public void unmarkTaskTest() {
        Task task = new TaskToDo("Testing");
        task.markAsDone();
        task.markUndone();
        assertEquals(false, task.getisDone());
    }


}
