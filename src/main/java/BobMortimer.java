import com.sun.source.util.TaskListener;

import javax.sound.midi.SysexMessage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class BobMortimer {

    private Storage storage;
    private TaskList taskList;
    private UI ui;
    private Parser parser;


    public static void main(String[] args) {

        String logo = " ____      ___     ____        __  __     ___     ____     _____    _____   __  __    _____    ____    \n" +
                "| __ )    / _ \\   | __ )       |  \\/  |   / _ \\   |  _ \\   |_   _|    ___    |  \\/  |  | ____|  |  _ \\   \n" +
                "|  _ \\   | | | |  |  _ \\       | |\\/| |  | | | |  | |_) |    | |      | |    | |\\/| |  |  _|    | |_) |  \n" +
                "| |_) |  | |_| |  | |_) |      | |  | |  | |_| |  |  _ <     | |      | |    | |  | |  | |___   |  _ <   \n" +
                "|____/    \\___/   |____/       |_|  |_|   \\___/   |_| \\_\\    |_|      |_|    |_|  |_|  |_____|  |_| \\_\\ :) \n";
        String LINE = "____________________________________________________________";
        Scanner userInput = new Scanner(System.in);
        ArrayList<Task> tasksListLoad = new ArrayList<>(100);
        TaskList tasksList;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Storage storage = new Storage("BobMortimer.txt");


        //Greeting
        System.out.println(LINE + "\n" +
                "Hello I'm\n" + logo
                + "\nWhat can I do for you?\n"
                + LINE + "\n");

        //Reading From File
        try {
            tasksListLoad = storage.load();
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist or File not found");
        }
        tasksList = new TaskList(tasksListLoad);

        //User input
        while (true) {
            try {
                String instruction = userInput.nextLine();
                if (instruction.equals("bye")) { //bye
                    break;
                } else if (instruction.equals("list")) { //list
                    System.out.println("\n" + LINE + "\n" + "Here you go:\n");
                    for (int i = 0; i < tasksList.size(); i++) {
                        System.out.print((i + 1) + ". " + tasksList.get(i).toString() + "\n");
                    }
                    System.out.println(LINE + "\n");
                } else if (instruction.matches("^mark\\s+\\d+$")) {  //mark
                    System.out.println("\n" + LINE + "\n" + "Nice! It's done!:\n");
                    int n = Integer.parseInt(instruction.split("\\s+")[1]);
                    if (n < 1 || n > tasksList.size()) {
                        throw new BobException("Invalid task number!");
                    }
                    tasksList.mark(n-1);
                    System.out.println(tasksList.get(n-1).toString() + "\n" + LINE);
                    storage.save(tasksList.getTasksList());
                } else if (instruction.matches("^unmark\\s+\\d+$")) {  //unmark
                    System.out.println("\n" + LINE + "\n" + "OK, not done!:\n");
                    int n = Integer.parseInt(instruction.split("\\s+")[1]);
                    if (n < 1 || n > tasksList.size()) {
                        throw new BobException("Invalid task number!");
                    }
                    tasksList.unmark(n-1);
                    System.out.println(tasksList.get(n-1).toString() + "\n" + LINE);
                    storage.save(tasksList.getTasksList());
                } else if (instruction.toLowerCase().startsWith("todo ") || instruction.toLowerCase().startsWith("todo")) {
                    if (instruction.length() == 4) {
                        throw new BobException("OOPS!!! The description of a todo cannot be empty.");
                    }
                    String description = instruction.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new BobException("OOPS!!! The description of a todo cannot be empty.");
                    }
                    TaskToDo task = new TaskToDo(description);
                    tasksList.add(task);
                    System.out.println("\n" + LINE + "\n" + "Got it. I've added this task:\n" + task.toString()
                            + "\nNow you have " + (tasksList.size()) + " tasks in the list\n" + LINE);
                    storage.save(tasksList.getTasksList());
                } else if (instruction.toLowerCase().startsWith("deadline ") || instruction.toLowerCase().startsWith("deadline")) {
                    String cut = instruction.substring(9).trim();
                    int by = cut.indexOf("/by");
                    String description = cut.substring(0, by).trim();
                    String deadlineString = cut.substring(by + 3).trim();
                    LocalDate deadlineDate = LocalDate.parse(deadlineString, dateFormat);
                    TaskDeadline task = new TaskDeadline(description, deadlineDate);
                    tasksList.add(task);
                    System.out.println("\n" + LINE + "\n" + "Got it. I've added this task:\n" + task.toString()
                            + "\nNow you have " + (tasksList.size()) + " tasks in the list\n" + LINE);
                    storage.save(tasksList.getTasksList());
                } else if (instruction.toLowerCase().startsWith("event ") || instruction.toLowerCase().startsWith("event")) {
                    String cut = instruction.substring(6).trim();
                    int from = cut.indexOf("/from");
                    int to = cut.indexOf("/to", from + 1);
                    String description = cut.substring(0, from).trim();
                    String startDateString = cut.substring(from + 5, to).trim();
                    String endDateString = cut.substring(to + 3).trim();
                    LocalDate startDate = LocalDate.parse(startDateString, dateFormat);
                    LocalDate endDate = LocalDate.parse(endDateString, dateFormat);
                    TaskEvent task = new TaskEvent(description, startDate, endDate);
                    tasksList.add(task);
                    System.out.println("\n" + LINE + "\n" + "Got it. I've added this task:\n" + task.toString()
                            + "\nNow you have " + (tasksList.size()) + " tasks in the list\n" + LINE);
                    storage.save(tasksList.getTasksList());
                } else if (instruction.matches("(?i)^delete\\s+\\d+$")) {
                    int n = Integer.parseInt(instruction.trim().split("\\s+")[1]);
                    if (n < 1 || n > tasksList.size()) {
                        throw new BobException("Invalid task number!");
                    }
                    System.out.println("\n" + LINE + "\n" + "Ok, I have removed the task:\n" + tasksList.get(n-1).toString()
                            + "\nNow you have " + (tasksList.size() - 1) + " tasks in the list\n" + LINE);
                    tasksList.remove(n-1);
                    storage.save(tasksList.getTasksList());
                } else {
                    throw new BobException("wot?");
                }
            } catch (BobException | IOException e) {
                System.out.println("\n" + LINE + "\n" + "  " + e.getMessage() + "\n" + LINE);
            }
        }

        //exit
        System.out.println("Bye. Hope to see you again soon!\n"
                        + LINE);
    }

}
