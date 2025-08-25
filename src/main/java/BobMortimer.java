import com.sun.source.util.TaskListener;

import javax.sound.midi.SysexMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BobMortimer {
    public static void main(String[] args) {
        String logo = " ____      ___     ____        __  __     ___     ____     _____    _____   __  __    _____    ____    \n" +
                "| __ )    / _ \\   | __ )       |  \\/  |   / _ \\   |  _ \\   |_   _|    ___    |  \\/  |  | ____|  |  _ \\   \n" +
                "|  _ \\   | | | |  |  _ \\       | |\\/| |  | | | |  | |_) |    | |      | |    | |\\/| |  |  _|    | |_) |  \n" +
                "| |_) |  | |_| |  | |_) |      | |  | |  | |_| |  |  _ <     | |      | |    | |  | |  | |___   |  _ <   \n" +
                "|____/    \\___/   |____/       |_|  |_|   \\___/   |_| \\_\\    |_|      |_|    |_|  |_|  |_____|  |_| \\_\\ :) \n";
        String LINE = "____________________________________________________________";
        Scanner userInput = new Scanner(System.in);
        ArrayList<Task> tasksList = new ArrayList<>(100);
        int taskNo = 0;


        //Greeting
        System.out.println(LINE + "\n" +
                "Hello I'm\n" + logo
                + "\nWhat can I do for you?\n"
                + LINE + "\n");

        //Reading From File
        try {
            readFileTasks("BobMortimer.txt", tasksList);
            taskNo = tasksList.size();
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist or File not found");
        }

        //User input
        while (true) {
            try {
                String instruction = userInput.nextLine();
                if (instruction.equals("bye")) { //bye
                    break;
                } else if (instruction.equals("list")) { //list
                    System.out.println("\n" + LINE + "\n" + "Here you go:\n");
                    for (int i = 0; i < taskNo; i++) {
                        System.out.print((i + 1) + ". " + tasksList.get(i).toString() + "\n");
                    }
                    System.out.println(LINE + "\n");
                } else if (instruction.matches("^mark\\s+\\d+$")) {  //mark
                    System.out.println("\n" + LINE + "\n" + "Nice! It's done!:\n");
                    int n = Integer.parseInt(instruction.split("\\s+")[1]);
                    if (n < 1 || n > taskNo) {
                        throw new BobException("Invalid task number!");
                    }
                    tasksList.get(n-1).markAsDone();
                    System.out.println(tasksList.get(n-1).toString() + "\n" + LINE);
                } else if (instruction.matches("^unmark\\s+\\d+$")) {  //unmark
                    System.out.println("\n" + LINE + "\n" + "OK, not done!:\n");
                    int n = Integer.parseInt(instruction.split("\\s+")[1]);
                    if (n < 1 || n > taskNo) {
                        throw new BobException("Invalid task number!");
                    }
                    tasksList.get(n-1).markUndone();
                    System.out.println(tasksList.get(n-1).toString() + "\n" + LINE);
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
                            + "\nNow you have " + (taskNo + 1) + " tasks in the list\n" + LINE);
                    taskNo++;
                } else if (instruction.toLowerCase().startsWith("deadline ") || instruction.toLowerCase().startsWith("deadline")) {
                    String cut = instruction.substring(9).trim();
                    int by = cut.indexOf("/by");
                    String description = cut.substring(0, by).trim();
                    String deadline = cut.substring(by + 3).trim();
                    TaskDeadline task = new TaskDeadline(description, deadline);
                    tasksList.add(task);
                    System.out.println("\n" + LINE + "\n" + "Got it. I've added this task:\n" + task.toString()
                            + "\nNow you have " + (taskNo + 1) + " tasks in the list\n" + LINE);
                    taskNo++;
                } else if (instruction.toLowerCase().startsWith("event ") || instruction.toLowerCase().startsWith("event")) {
                    String cut = instruction.substring(6).trim();
                    int from = cut.indexOf("/from");
                    int to = cut.indexOf("/to", from + 1);
                    String description = cut.substring(0, from).trim();
                    String startDate = cut.substring(from + 5, to).trim();
                    String endDate = cut.substring(to + 3).trim();
                    TaskEvent task = new TaskEvent(description, startDate, endDate);
                    tasksList.add(task);
                    System.out.println("\n" + LINE + "\n" + "Got it. I've added this task:\n" + task.toString()
                            + "\nNow you have " + (taskNo + 1) + " tasks in the list\n" + LINE);
                    taskNo++;
                } else if (instruction.matches("(?i)^delete\\s+\\d+$")) {
                    int n = Integer.parseInt(instruction.trim().split("\\s+")[1]);
                    if (n < 1 || n > taskNo) {
                        throw new BobException("Invalid task number!");
                    }
                    System.out.println("\n" + LINE + "\n" + "Ok, I have removed the task:\n" + tasksList.get(n-1).toString()
                            + "\nNow you have " + (taskNo - 1) + " tasks in the list\n" + LINE);
                    tasksList.remove(n-1);
                    taskNo--;
                } else {
                    throw new BobException("wot?");
                }
            } catch (BobException e) {
                System.out.println("\n" + LINE + "\n" + "  " + e.getMessage() + "\n" + LINE);
            }
        }

        //exit
        System.out.println("Bye. Hope to see you again soon!\n"
                        + LINE);
    }

    private static void readFileTasks(String filePath, ArrayList<Task> tasksList) throws FileNotFoundException {
        File f = new File(filePath); // create a File for the given file path
        Scanner s = new Scanner(f); // create a Scanner using the File as the source
        while (s.hasNext()) {
            String line = s.nextLine().trim();
            String[] p = line.split("\\s*\\|\\s*");

            String taskType = p[0];
            boolean isDone = "1".equals(p[1]);
            String description = p[2];

            Task task = null;
            if(taskType.equals("T")) {
                task = new TaskToDo(description);
            } else if(taskType.equals("D")) {
                String deadline = (p.length >= 4) ? p[3] : "";
                task = new TaskDeadline(description, deadline);
            } else if(taskType.equals("E")) {
                String startDate = (p.length >= 4) ? p[3] : "";
                String endDate = (p.length >= 5) ? p[4] : "";
                task = new TaskEvent(description, startDate, endDate);
            }

            if (isDone) {
                task.markAsDone();
            }
            tasksList.add(task);
        }
    }

}
