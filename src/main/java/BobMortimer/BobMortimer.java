package BobMortimer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import BobMortimer.gui.Ui;
import BobMortimer.tasks.Task;
import BobMortimer.tasks.TaskDeadLine;
import BobMortimer.tasks.TaskEvent;
import BobMortimer.tasks.TaskList;
import BobMortimer.tasks.TaskToDo;


/**
 * Main class of BobMortimer.
 */
public class BobMortimer {

    private Storage storage;
    private TaskList tasksList;
    private Ui ui;
    private Parser parser;
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private boolean isFinished;

    /**
     * Constructor of BobMortimer.
     *
     * @param filePath the file path to load tasks from and save tasks to
     */
    public BobMortimer(String filePath) {
        this.ui = new Ui();
        this.parser = new Parser();
        this.storage = new Storage(filePath);
        // Reading from file
        try {
            ArrayList<Task> tasksListLoad = storage.load();
            this.tasksList = new TaskList(tasksListLoad);
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist or File not found");
            this.tasksList = new TaskList(new ArrayList<>(100));
        }
    }

    /**
     * Method to run the program.
     *
     * @throws BobException  if user input is invalid, such as invalid task number or missing arguments
     * @throws IOException   if there is an error writing tasks to the storage file
     */
    public String getResponse(String instruction) {

        String line = "____________________________________________________________";
        //User input
        try {
            Parser.Result cmd = parser.parse(instruction);
            if (cmd.type == Parser.Type.BYE) { //bye
                isFinished = true;
                return ui.showBye();
            } else if (cmd.type == Parser.Type.LIST) { //list
                return ui.showList(tasksList.getTasksList());
            } else if (cmd.type == Parser.Type.MARK) { //mark
                int n = Integer.parseInt(instruction.split("\\s+")[1]);
                if (n < 1 || n > tasksList.size()) {
                    throw new BobException("Invalid task number!");
                }
                tasksList.mark(n - 1);
                storage.save(tasksList.getTasksList());
                return ui.showMark(tasksList.get(n - 1));
            } else if (cmd.type == Parser.Type.UNMARK) { //unmark
                int n = Integer.parseInt(instruction.split("\\s+")[1]);
                if (n < 1 || n > tasksList.size()) {
                    throw new BobException("Invalid task number!");
                }
                tasksList.unmark(n - 1);
                storage.save(tasksList.getTasksList());
                return ui.showUnmark(tasksList.get(n - 1));
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
                storage.save(tasksList.getTasksList());
                return ui.showAdded(task, tasksList.size());
            } else if (cmd.type == Parser.Type.DEADLINE) {
                String description = cmd.args[0];
                String deadlineString = cmd.args[1];
                LocalDate deadlineDate = LocalDate.parse(deadlineString, dateFormat);
                TaskDeadLine task = new TaskDeadLine(description, deadlineDate);
                tasksList.add(task);
                storage.save(tasksList.getTasksList());
                return ui.showAdded(task, tasksList.size());
            } else if (cmd.type == Parser.Type.EVENT) {
                String description = cmd.args[0];
                String startDateString = cmd.args[1];
                String endDateString = cmd.args[2];
                LocalDate startDate = LocalDate.parse(startDateString, dateFormat);
                LocalDate endDate = LocalDate.parse(endDateString, dateFormat);
                TaskEvent task = new TaskEvent(description, startDate, endDate);
                tasksList.add(task);
                storage.save(tasksList.getTasksList());
                return ui.showAdded(task, tasksList.size());
            } else if (cmd.type == Parser.Type.DELETE) {
                int n = Integer.parseInt(instruction.trim().split("\\s+")[1]);
                if (n < 1 || n > tasksList.size()) {
                    throw new BobException("Invalid task number!");
                }
                ui.showDeleted(tasksList.get(n - 1), tasksList.size() - 1);
                tasksList.remove(n - 1);
                storage.save(tasksList.getTasksList());
            } else if (cmd.type == Parser.Type.FIND) {
                String keyword = instruction.substring(5).trim();
                ArrayList<Task> matchingTaskList = new ArrayList<>();
                if (keyword.isEmpty()) {
                    throw new BobException("OOPS!!! The keyword cannot be empty.");
                }
                matchingTaskList = tasksList.findTasks(keyword);
                return ui.showFind(matchingTaskList);
            } else {
                throw new BobException("wot?");
            }
        } catch (BobException | IOException e) {
            return ui.showError(e.getMessage());
        }
        return ui.showError("Unknown error");
    }

    public boolean getIsFinished() {
        return this.isFinished;
    }

    public String showGreeting() {
        return ui.showGreeting();
    }

}
