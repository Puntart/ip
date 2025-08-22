public class TaskDeadline extends Task {

    private String deadline;

    public TaskDeadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString();
    }

}
