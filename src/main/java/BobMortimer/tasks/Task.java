package BobMortimer.tasks;

/**
 * Parent Task class to handle individual tasks.
 */
public class Task {

    private String description;
    private boolean isDone;

    /**
     * Constructor.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markUndone() {
        this.isDone = false;
    }

    /**
     * Getter method for isDone.
     *
     * @return true if the task is completed, false otherwise
     */
    public boolean getisDone() {
        return this.isDone;
    }

    /**
     * Method to check if task contains keyword.
     *
     * @return true if the task contains keyword, false otherwise
     */
    public boolean containsKeyword(String keyword) {
        if (description.contains(keyword)) {
            return true;
        }
        return false;
    }

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
