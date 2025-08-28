public class UI {

    public UI() {

    }

    public void showGreeting(String LINE, String logo) {
        System.out.println(LINE + "\n" +
                "Hello I'm\n" + logo
                + "\nWhat can I do for you?\n"
                + LINE + "\n");
    }

    // List header + footer are printed around the items (items are printed by caller)
    public void showListHeader(String LINE) {
        System.out.println("\n" + LINE + "\n" + "Here you go:\n");
    }

    public void showListFooter(String LINE) {
        System.out.println(LINE + "\n");
    }

    public void showMark(String LINE, Task t) {
        System.out.println("\n" + LINE + "\n" + "Nice! It's done!:\n");
        System.out.println(t.toString() + "\n" + LINE);
    }

    public void showUnmark(String LINE, Task t) {
        System.out.println("\n" + LINE + "\n" + "OK, not done!:\n");
        System.out.println(t.toString() + "\n" + LINE);
    }

    public void showAdded(String LINE, Task t, int total) {
        System.out.println("\n" + LINE + "\n" + "Got it. I've added this task:\n" + t.toString()
                + "\nNow you have " + (total) + " tasks in the list\n" + LINE);
    }

    public void showDeleted(String LINE, Task t, int remaining) {
        System.out.println("\n" + LINE + "\n" + "Ok, I have removed the task:\n" + t.toString()
                + "\nNow you have " + (remaining) + " tasks in the list\n" + LINE);
    }

    public void showError(String LINE, String message) {
        System.out.println("\n" + LINE + "\n" + "  " + message + "\n" + LINE);
    }

    public void showBye(String LINE) {
        System.out.println("Bye. Hope to see you again soon!\n" + LINE);
    }

}

