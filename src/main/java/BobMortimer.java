import com.sun.source.util.TaskListener;

import javax.sound.midi.SysexMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");


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
                    writeToFile("BobMortimer.txt", tasksList);
                } else if (instruction.matches("^unmark\\s+\\d+$")) {  //unmark
                    System.out.println("\n" + LINE + "\n" + "OK, not done!:\n");
                    int n = Integer.parseInt(instruction.split("\\s+")[1]);
                    if (n < 1 || n > taskNo) {
                        throw new BobException("Invalid task number!");
                    }
                    tasksList.get(n-1).markUndone();
                    System.out.println(tasksList.get(n-1).toString() + "\n" + LINE);
                    writeToFile("BobMortimer.txt", tasksList);
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
                    writeToFile("BobMortimer.txt", tasksList);
                } else if (instruction.toLowerCase().startsWith("deadline ") || instruction.toLowerCase().startsWith("deadline")) {
                    String cut = instruction.substring(9).trim();
                    int by = cut.indexOf("/by");
                    String description = cut.substring(0, by).trim();
                    String deadlineString = cut.substring(by + 3).trim();
                    LocalDate deadlineDate = LocalDate.parse(deadlineString, dateFormat);
                    TaskDeadline task = new TaskDeadline(description, deadlineDate);
                    tasksList.add(task);
                    System.out.println("\n" + LINE + "\n" + "Got it. I've added this task:\n" + task.toString()
                            + "\nNow you have " + (taskNo + 1) + " tasks in the list\n" + LINE);
                    taskNo++;
                    writeToFile("BobMortimer.txt", tasksList);
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
                            + "\nNow you have " + (taskNo + 1) + " tasks in the list\n" + LINE);
                    taskNo++;
                    writeToFile("BobMortimer.txt", tasksList);
                } else if (instruction.matches("(?i)^delete\\s+\\d+$")) {
                    int n = Integer.parseInt(instruction.trim().split("\\s+")[1]);
                    if (n < 1 || n > taskNo) {
                        throw new BobException("Invalid task number!");
                    }
                    System.out.println("\n" + LINE + "\n" + "Ok, I have removed the task:\n" + tasksList.get(n-1).toString()
                            + "\nNow you have " + (taskNo - 1) + " tasks in the list\n" + LINE);
                    tasksList.remove(n-1);
                    taskNo--;
                    writeToFile("BobMortimer.txt", tasksList);
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

    private static void readFileTasks(String filePath, ArrayList<Task> tasksList) throws FileNotFoundException {
        File f = new File(filePath); // create a File for the given file path
        Scanner s = new Scanner(f); // create a Scanner using the File as the source
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");

        Pattern header = Pattern.compile("^\\[(T|D|E)\\]\\[(X| )\\]\\s+(.*)$");
        Pattern pDeadline = Pattern.compile("^(.*)\\s*\\(by:\\s*(.*)\\)\\s*$", Pattern.CASE_INSENSITIVE);
        Pattern pEvent = Pattern.compile("^(.*)\\s*\\(from:\\s*(.*?)\\s+to:\\s*(.*?)\\)\\s*$",
                Pattern.CASE_INSENSITIVE);

        while (s.hasNext()) {
            String line = s.nextLine().trim();
            Matcher match = header.matcher(line);
            if (!match.matches()) {
                System.err.println("Skipping unparsable line: " + line);
                continue;
            }

            String taskType = match.group(1);
            boolean isDone = match.group(2).equals("X");
            String rest = match.group(3).trim();

            Task task = null;
            if(taskType.equals("T")) {
                task = new TaskToDo(rest);
            } else if(taskType.equals("D")) {
                Matcher mDeadline = pDeadline.matcher(rest);
                if (mDeadline.matches()) {
                    LocalDate deadlineDate = LocalDate.parse(mDeadline.group(2).trim(), dateFormat);
                    task = new TaskDeadline(mDeadline.group(1).trim(), deadlineDate);
                } else {
                    System.err.println("Skipping unparsable deadline: " + rest);
                    continue;
                }
            } else if(taskType.equals("E")) {
                Matcher mEvent = pEvent.matcher(rest);
                if (mEvent.matches()) {
                    LocalDate startDate = LocalDate.parse(mEvent.group(2).trim(), dateFormat);
                    LocalDate endDate = LocalDate.parse(mEvent.group(3).trim(), dateFormat);
                    task = new TaskEvent(mEvent.group(1).trim(),
                            startDate,
                            endDate);
                } else {
                    System.err.println("Skipping unparsable event: " + rest);
                    continue;
                }
            }

            if (isDone) {
                task.markAsDone();
            }
            tasksList.add(task);
        }
    }

    private static void writeToFile(String filePath, ArrayList<Task> tasksList) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        for (int i = 0; i < tasksList.size(); i++) {
            fw.write(tasksList.get(i).toString() + System.lineSeparator());
        }
        fw.close();
    }

}
