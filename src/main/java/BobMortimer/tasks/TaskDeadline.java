package BobMortimer.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Subclass of Task for Deadline Tasks
 */
public class TaskDeadline extends Task {

    private LocalDate deadline;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Constructor
     *
     * @param description the description of the deadline task
     * @param deadline the date by which the task should be completed
     */
    public TaskDeadline(String description, LocalDate deadline) {
        super(description);
        this.deadline = deadline;
    }

    /**
     *
     * @return a string representation of the deadline task,
     *         including its type, completion status, description, and deadline date
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline.format(dateFormat) + ")";
    }

}
