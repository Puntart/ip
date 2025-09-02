package BobMortimer.tasks;

/**
 * Subclass of task for ToDo Tasks.
 */
public class TaskToDo extends Task {

    /**
     * Constructor.
     *
     * @param description the description of the todo task
     */
    public TaskToDo(String description) {
        super(description);
    }

    /**
     *
     * @return a string representation of the todo task,
     *         including its type, completion status, and description
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}
