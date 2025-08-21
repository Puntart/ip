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

    @Override
    public String toString() {
        if (isDone == true) {
            System.out.println("[X]" + description);
        }
        else if (isDone == false) {
            System.out.println("[ ]" + description);
        }
        return "fail";
    }
}
