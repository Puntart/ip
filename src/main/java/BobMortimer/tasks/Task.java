package BobMortimer.tasks;

/**
 * parent Task class to handle individual tasks
 */
public class Task {

    private String description;
    private boolean isDone;

    /**
     * Constructor
     *
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as completed
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed
     */
    public void markUndone() {
        this.isDone = false;
    }

    /**
     * getter method for isDone
     *
     * @return true if the task is completed, false otherwise
     */
    public boolean getisDone() {
        return this.isDone;
    }

    /**
     *
     * @return the string representation of the task,
     *         including its completion status and description
     */
    @Override
    public String toString() {
        String status = "";
        if (isDone == true) {
            status = "[X] ";
        }
        else if (isDone == false) {
            status = "[ ] ";
        }
        return status + description;
    }
}
