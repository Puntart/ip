package bobmortimer.gui;

import java.util.ArrayList;

import bobmortimer.tasks.Task;

//Javadocs here were made by ChatGPT
/**
 * Handles interactions with the user interface (text output).
 */
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

    /**
     * Shows the greeting message with logo.
     * @return the greeting string
     */
    public String showGreeting() {
        return "Hello I'm\n" + logo
                + "\nWhat can I do for you?";
    }

    /**
     * Shows the list of tasks.
     * @param tasksList the tasks
     * @return the formatted list
     */
    public String showList(ArrayList<Task> tasksList) {
        String showL = "Here you go:\n";
        for (int i = 0; i < tasksList.size(); i++) {
            showL = showL + (i + 1) + ". " + tasksList.get(i).toString() + "\n";
        }
        return showL;
    }

    /**
     * Shows a task marked as done.
     * @param t the task
     * @return the confirmation string
     */
    public String showMark(Task t) {
        return "Nice! It's done!:\n" + t.toString();
    }

    /**
     * Shows a task marked as not done.
     * @param t the task
     * @return the confirmation string
     */
    public String showUnmark(Task t) {
        return "OK, not done!:\n" + t.toString();
    }

    /**
     * Shows a task added to the list.
     * @param t the task
     * @param total total tasks after addition
     * @return the confirmation string
     */
    public String showAdded(Task t, int total) {
        return "Got it. I've added this task:\n" + t.toString()
                + "\nNow you have " + (total) + " tasks in the list";
    }

    /**
     * Shows a task deleted from the list.
     * @param t the task
     * @param remaining remaining tasks
     * @return the confirmation string
     */
    public String showDeleted(Task t, int remaining) {
        return "Ok, I have removed the task:\n" + t.toString()
                + "\nNow you have " + (remaining) + " tasks in the list";
    }

    /**
     * Shows the results of a find command.
     * @param matchingTaskList the matching tasks
     * @return the formatted list of matches
     */
    public String showFind(ArrayList<Task> matchingTaskList) {
        String showF = "Here are the matching tasks in your list:\n";
        for (int i = 0; i < matchingTaskList.size(); i++) {
            showF = showF + (i + 1) + ". " + matchingTaskList.get(i).toString() + "\n";
        }
        return showF;
    }

    /**
     * Construct the statistics string.
     * @param countOfMark number of completed tasks
     * @param countOfUnmark number of uncompleted tasks
     * @return the formatted statistics string
     */
    public String showStatistics(int countOfMark, int countOfUnmark) {
        String statistics = "Here are some statistics!\n";
        statistics += countOfMark + " are done!\n" + countOfUnmark + " are not done!";
        if (countOfMark <= 2) {
            statistics += "\nGet to it you lazy sausage.";
        }
        return statistics;
    }

    public String showError(String message) {
        return message;
    }

    public String showBye() {
        return "Bye. Hope to see you again soon!\n";
    }

}

