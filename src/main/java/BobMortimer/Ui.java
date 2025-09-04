package BobMortimer;

import java.util.ArrayList;

import BobMortimer.tasks.Task;

public class Ui {

    private static final String logo = " ____      ___     ____        __  __     ___     ____"
            + "     _____    _____   __  __    _____    ____    \n"
            + "| __ )    / _ \\   | __ )       |  \\/  |   / _ \\   |  _ \\  "
            + " |_   _|    ___    |  \\/  |  | ____|  |  _ \\   \n"
            + "|  _ \\   | | | |  |  _ \\       | |\\/| |  | | | |  | |_) |  "
            + "  | |      | |    | |\\/| |  |  _|    | |_) |  \n"
            + "| |_) |  | |_| |  | |_) |      | |  | |  | |_| |  |  _ <     | | "
            + "     | |    | |  | |  | |___   |  _ <   \n"
            + "|____/    \\___/   |____/       |_|  |_|   \\___/   |_| \\_\\    |_|  "
            + "    |_|    |_|  |_|  |_____|  |_| \\_\\ :) \n";
    private static final String line = "____________________________________________________________";

    public Ui() {

    }

    public String showGreeting() {
        return line + "\n"
                + "Hello I'm\n" + logo
                + "\nWhat can I do for you?\n"
                + line + "\n";
    }

    public String showList(ArrayList<Task> tasksList) {
        String showL = "\n" + line + "\n" + "Here you go:\n";
        for (int i = 0; i < tasksList.size(); i++) {
            showL = showL + (i + 1) + ". " + tasksList.get(i).toString() + "\n";
        }
        showL = showL + "\n" + line + "\n";
        return showL;
    }

    public String showMark(Task t) {
        return "\n" + line + "\n" + "Nice! It's done!:\n" + t.toString() + "\n" + line;
    }

    public String showUnmark(Task t) {
        return "\n" + line + "\n" + "OK, not done!:\n" + t.toString() + "\n" + line;
    }

    public String showAdded(Task t, int total) {
        return "\n" + line + "\n" + "Got it. I've added this task:\n" + t.toString()
                + "\nNow you have " + (total) + " tasks in the list\n" + line;
    }

    public String showDeleted(Task t, int remaining) {
        return "\n" + line + "\n" + "Ok, I have removed the task:\n" + t.toString()
                + "\nNow you have " + (remaining) + " tasks in the list\n" + line;
    }

    public String showFind(ArrayList<Task> matchingTaskList) {
        String showF = "\n" + line + "\n" + "Here are the matching tasks in your list:\n";
        for (int i = 0; i < matchingTaskList.size(); i++) {
            showF = showF + (i + 1) + ". " + matchingTaskList.get(i).toString() + "\n";
        }
        showF = showF + "\n" + line + "\n";
        return showF;
    }

    public String showError(String message) {
        return "\n" + line + "\n" + "  " + message + "\n" + line;
    }

    public String showBye() {
        return "Bye. Hope to see you again soon!\n" + line;
    }

}

