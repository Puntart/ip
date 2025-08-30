package BobMortimer;

import BobMortimer.tasks.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * main class of BobMortimer
 */
public class BobMortimer {

    private Storage storage;
    private TaskList tasksList;
    private UI ui;
    private Parser parser;
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructor of BobMortimer
     *
     * @param filePath the file path to load tasks from and save tasks to
     */
    public BobMortimer(String filePath) {
        this.ui = new UI();
        this.parser = new Parser();
        this.storage = new Storage(filePath);
        //Reading from file
        try {
            ArrayList<Task> tasksListLoad = storage.load();
            this.tasksList = new TaskList(tasksListLoad);
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist or File not found");
            this.tasksList = new TaskList(new ArrayList<>(100));
        }
    }

    /**
     * method to run the program
     *
     * @throws BobException  if user input is invalid, such as invalid task number or missing arguments
     * @throws IOException   if there is an error writing tasks to the storage file
     */
    public void run() {

        String LOGO = " ____      ___     ____        __  __     ___     ____     _____    _____   __  __    _____    ____    \n" +
                "| __ )    / _ \\   | __ )       |  \\/  |   / _ \\   |  _ \\   |_   _|    ___    |  \\/  |  | ____|  |  _ \\   \n" +
                "|  _ \\   | | | |  |  _ \\       | |\\/| |  | | | |  | |_) |    | |      | |    | |\\/| |  |  _|    | |_) |  \n" +
                "| |_) |  | |_| |  | |_) |      | |  | |  | |_| |  |  _ <     | |      | |    | |  | |  | |___   |  _ <   \n" +
                "|____/    \\___/   |____/       |_|  |_|   \\___/   |_| \\_\\    |_|      |_|    |_|  |_|  |_____|  |_| \\_\\ :) \n";
        String LINE = "____________________________________________________________";
        Scanner userInput = new Scanner(System.in);
        ArrayList<Task> tasksListLoad = new ArrayList<>(100);

        //Greeting
        ui.showGreeting(LINE, LOGO);

        //User input
        while (true) {
            try {
                String instruction = userInput.nextLine();
                Parser.Result cmd = parser.parse(instruction);
                if (cmd.type == Parser.Type.BYE) { //bye
                    break;
                } else if (cmd.type == Parser.Type.LIST) { //list
                    ui.showList(LINE, tasksList.getTasksList());
                } else if (cmd.type == Parser.Type.MARK) {  //mark
                    int n = Integer.parseInt(instruction.split("\\s+")[1]);
                    if (n < 1 || n > tasksList.size()) {
                        throw new BobException("Invalid task number!");
                    }
                    tasksList.mark(n-1);
                    ui.showMark(LINE, tasksList.get(n-1));
                    storage.save(tasksList.getTasksList());
                } else if (cmd.type == Parser.Type.UNMARK) {  //unmark
                    int n = Integer.parseInt(instruction.split("\\s+")[1]);
                    if (n < 1 || n > tasksList.size()) {
                        throw new BobException("Invalid task number!");
                    }
                    tasksList.unmark(n-1);
                    ui.showUnmark(LINE, tasksList.get(n-1));
                    storage.save(tasksList.getTasksList());
                } else if (cmd.type == Parser.Type.TODO) {
                    if (instruction.length() == 4) {
                        throw new BobException("OOPS!!! The description of a todo cannot be empty.");
                    }
                    String description = instruction.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new BobException("OOPS!!! The description of a todo cannot be empty.");
                    }
                    TaskToDo task = new TaskToDo(description);
                    tasksList.add(task);
                    ui.showAdded(LINE, task, tasksList.size());
                    storage.save(tasksList.getTasksList());
                } else if (cmd.type == Parser.Type.DEADLINE) {
                    String description = cmd.args[0];
                    String deadlineString = cmd.args[1];
                    LocalDate deadlineDate = LocalDate.parse(deadlineString, dateFormat);
                    TaskDeadline task = new TaskDeadline(description, deadlineDate);
                    tasksList.add(task);
                    ui.showAdded(LINE, task, tasksList.size());
                    storage.save(tasksList.getTasksList());
                } else if (cmd.type == Parser.Type.EVENT) {
                    String description = cmd.args[0];
                    String startDateString = cmd.args[1];
                    String endDateString = cmd.args[2];
                    LocalDate startDate = LocalDate.parse(startDateString, dateFormat);
                    LocalDate endDate = LocalDate.parse(endDateString, dateFormat);
                    TaskEvent task = new TaskEvent(description, startDate, endDate);
                    tasksList.add(task);
                    ui.showAdded(LINE, task, tasksList.size());
                    storage.save(tasksList.getTasksList());
                } else if (cmd.type == Parser.Type.DELETE) {
                    int n = Integer.parseInt(instruction.trim().split("\\s+")[1]);
                    if (n < 1 || n > tasksList.size()) {
                        throw new BobException("Invalid task number!");
                    }
                    ui.showDeleted(LINE, tasksList.get(n-1), tasksList.size() - 1);
                    tasksList.remove(n-1);
                    storage.save(tasksList.getTasksList());
                } else if (cmd.type == Parser.Type.FIND) {
                    String keyword = instruction.substring(5).trim();
                    ArrayList<Task> matchingTaskList = new ArrayList<>();
                    if (keyword.isEmpty()) {
                        throw new BobException("OOPS!!! The keyword cannot be empty.");
                    }
                    matchingTaskList = tasksList.findTasks(keyword);
                    ui.showFind(LINE, matchingTaskList);
                } else {
                    throw new BobException("wot?");
                }
            } catch (BobException | IOException e) {
                System.out.println("\n" + LINE + "\n" + "  " + e.getMessage() + "\n" + LINE);
            }
        }

        //exit
        ui.showBye(LINE);
    }

    /**
     * main method
     *
     * @param args the command-line arguments, unused in this application
     */
    public static void main(String[] args) {
        new BobMortimer("BobMortimer.txt").run();
    }

}
