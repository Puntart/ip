public class Task {

    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    public boolean getisDone() {
        return this.isDone;
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
