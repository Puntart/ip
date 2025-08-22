public class TaskToDo extends Task {

    public TaskToDo(String description, String deadline) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}
