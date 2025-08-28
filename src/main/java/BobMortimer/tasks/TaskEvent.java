package BobMortimer.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Subclass of Task for Event Tasks
 */
public class TaskEvent extends Task {

    private LocalDate startDate;
    private LocalDate endDate;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Constructor
     *
     * @param description the description of the event task
     * @param startDate   the start date of the event
     * @param endDate     the end date of the event
     */
    public TaskEvent(String description, LocalDate startDate, LocalDate endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     *
     * @return a string representation of the event task,
     *         including its type, completion status, description, start date, and end date
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDate.format(dateFormat) + " to: "
                + endDate.format(dateFormat) + ")";
    }

}
