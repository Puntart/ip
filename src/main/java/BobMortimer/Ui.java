package BobMortimer;

import java.util.ArrayList;

import BobMortimer.tasks.Task;

public class Ui {

    private String logo = " ____      ___     ____        __  __     ___     ____"
            + "     _____    _____   __  __    _____    ____    \n"
            + "| __ )    / _ \\   | __ )       |  \\/  |   / _ \\   |  _ \\  "
            + " |_   _|    ___    |  \\/  |  | ____|  |  _ \\   \n"
            + "|  _ \\   | | | |  |  _ \\       | |\\/| |  | | | |  | |_) |  "
            + "  | |      | |    | |\\/| |  |  _|    | |_) |  \n"
            + "| |_) |  | |_| |  | |_) |      | |  | |  | |_| |  |  _ <     | | "
            + "     | |    | |  | |  | |___   |  _ <   \n"
            + "|____/    \\___/   |____/       |_|  |_|   \\___/   |_| \\_\\    |_|  "
            + "    |_|    |_|  |_|  |_____|  |_| \\_\\ :) \n";
    private String line = "____________________________________________________________";

    public Ui() {

    }

    public void showGreeting() {
        System.out.println(line + "\n"
                + "Hello I'm\n" + logo
                + "\nWhat can I do for you?\n"
                + line + "\n");
    }

    public void showList(ArrayList<Task> tasksList) {
        System.out.println("\n" + line + "\n" + "Here you go:\n");
        for (int i = 0; i < tasksList.size(); i++) {
            System.out.print((i + 1) + ". " + tasksList.get(i).toString() + "\n");
        }
        System.out.println("\n" + line + "\n");
    }

    public void showMark(Task t) {
        System.out.println("\n" + line + "\n" + "Nice! It's done!:\n");
        System.out.println(t.toString() + "\n" + line);
    }

    public void showUnmark(Task t) {
        System.out.println("\n" + line + "\n" + "OK, not done!:\n");
        System.out.println(t.toString() + "\n" + line);
    }

    public void showAdded(Task t, int total) {
        System.out.println("\n" + line + "\n" + "Got it. I've added this task:\n" + t.toString()
                + "\nNow you have " + (total) + " tasks in the list\n" + line);
    }

    public void showDeleted(Task t, int remaining) {
        System.out.println("\n" + line + "\n" + "Ok, I have removed the task:\n" + t.toString()
                + "\nNow you have " + (remaining) + " tasks in the list\n" + line);
    }

    public void showFind(ArrayList<Task> matchingTaskList) {
        System.out.println("\n" + line + "\n" + "Here are the matching tasks in your list:\n");
        for (int i = 0; i < matchingTaskList.size(); i++) {
            System.out.print((i + 1) + ". " + matchingTaskList.get(i).toString() + "\n");
        }
        System.out.println("\n" + line + "\n");
    }

    public void showError(String message) {
        System.out.println("\n" + line + "\n" + "  " + message + "\n" + line);
    }

    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!\n" + line);
    }

}

