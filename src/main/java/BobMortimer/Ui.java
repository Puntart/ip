package BobMortimer;

import java.util.ArrayList;

import BobMortimer.tasks.Task;

public class Ui {

    String LOGO = " ____      ___     ____        __  __     ___     ____     _____    _____   __  __    _____    ____    \n" +
            "| __ )    / _ \\   | __ )       |  \\/  |   / _ \\   |  _ \\   |_   _|    ___    |  \\/  |  | ____|  |  _ \\   \n" +
            "|  _ \\   | | | |  |  _ \\       | |\\/| |  | | | |  | |_) |    | |      | |    | |\\/| |  |  _|    | |_) |  \n" +
            "| |_) |  | |_| |  | |_) |      | |  | |  | |_| |  |  _ <     | |      | |    | |  | |  | |___   |  _ <   \n" +
            "|____/    \\___/   |____/       |_|  |_|   \\___/   |_| \\_\\    |_|      |_|    |_|  |_|  |_____|  |_| \\_\\ :) \n";
    String LINE = "____________________________________________________________";

    public Ui() {

    }

    public void showGreeting() {
        System.out.println(LINE + "\n" +
                "Hello I'm\n" + LOGO
                + "\nWhat can I do for you?\n"
                + LINE + "\n");
    }

    public void showList(ArrayList<Task> tasksList) {
        System.out.println("\n" + LINE + "\n" + "Here you go:\n");
        for (int i = 0; i < tasksList.size(); i++) {
            System.out.print((i + 1) + ". " + tasksList.get(i).toString() + "\n");
        }
        System.out.println("\n" + LINE + "\n");
    }

    public void showMark(Task t) {
        System.out.println("\n" + LINE + "\n" + "Nice! It's done!:\n");
        System.out.println(t.toString() + "\n" + LINE);
    }

    public void showUnmark(Task t) {
        System.out.println("\n" + LINE + "\n" + "OK, not done!:\n");
        System.out.println(t.toString() + "\n" + LINE);
    }

    public void showAdded(Task t, int total) {
        System.out.println("\n" + LINE + "\n" + "Got it. I've added this task:\n" + t.toString()
                + "\nNow you have " + (total) + " tasks in the list\n" + LINE);
    }

    public void showDeleted(Task t, int remaining) {
        System.out.println("\n" + LINE + "\n" + "Ok, I have removed the task:\n" + t.toString()
                + "\nNow you have " + (remaining) + " tasks in the list\n" + LINE);
    }

    public void showFind(ArrayList<Task> matchingTaskList) {
        System.out.println("\n" + LINE + "\n" + "Here are the matching tasks in your list:\n");
        for (int i = 0; i < matchingTaskList.size(); i++) {
            System.out.print((i + 1) + ". " + matchingTaskList.get(i).toString() + "\n");
        }
        System.out.println("\n" + LINE + "\n");
    }

    public void showError(String message) {
        System.out.println("\n" + LINE + "\n" + "  " + message + "\n" + LINE);
    }

    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!\n" + LINE);
    }

}

