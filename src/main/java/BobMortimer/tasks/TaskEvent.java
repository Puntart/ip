package BobMortimer.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskEvent extends Task {

    private LocalDate startDate;
    private LocalDate endDate;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public TaskEvent(String description, LocalDate startDate, LocalDate endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDate.format(dateFormat) + " to: "
                + endDate.format(dateFormat) + ")";
    }

}
