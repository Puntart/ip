package BobMortimer.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskDeadline extends Task {

    private LocalDate deadline;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public TaskDeadline(String description, LocalDate deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline.format(dateFormat) + ")";
    }

}
