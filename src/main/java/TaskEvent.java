public class TaskEvent extends Task {

    private String duration;

    public TaskEvent(String description, String deadline) {
        super(description);
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString();
    }

}
