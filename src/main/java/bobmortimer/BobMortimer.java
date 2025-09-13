package bobmortimer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import bobmortimer.gui.Ui;
import bobmortimer.tasks.Task;
import bobmortimer.tasks.TaskDeadLine;
import bobmortimer.tasks.TaskEvent;
import bobmortimer.tasks.TaskList;
import bobmortimer.tasks.TaskToDo;


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
            ArrayList<Task> tasksListToLoad = storage.load();
            this.tasksList = new TaskList(tasksListToLoad); //loading the list from file
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist or File not found");
            this.tasksList = new TaskList(new ArrayList<>(100)); //creates a new list if not found
        }
    }

    /**
     * Runs the program.
     *
     * @throws BobException  if user input is invalid, such as invalid task number or missing arguments
     * @throws IOException   if there is an error writing tasks to the storage file
     */
    public String getResponse(String instruction) {

        try { //User input
            Parser.Result command = parser.parse(instruction);
            if (command.type == Parser.Type.BYE) { //bye
                return handleBye();
            } else if (command.type == Parser.Type.LIST) { //list
                return handleList();
            } else if (command.type == Parser.Type.MARK) { //mark
                return handleMark(instruction);
            } else if (command.type == Parser.Type.UNMARK) { //unmark
                return handleUnmark(instruction);
            } else if (command.type == Parser.Type.TODO) {
                return handleTodo(instruction);
            } else if (command.type == Parser.Type.DEADLINE) {
                assert command.args != null && command.args.length == 2
                        : "DEADLINE requires exactly 2 args (description, deadlineString)";
                String description = command.args[0];
                String deadlineString = command.args[1];
                return handleDeadline(description, deadlineString);
            } else if (command.type == Parser.Type.EVENT) {
                assert command.args != null && command.args.length == 3
                        : "EVENT requires exactly 3 args (description, startDate, endDate)";
                String description = command.args[0];
                String startDateString = command.args[1];
                String endDateString = command.args[2];
                return handleEvent(description, startDateString, endDateString);
            } else if (command.type == Parser.Type.DELETE) {
                return handleDelete(instruction);
            } else if (command.type == Parser.Type.FIND) {
                return handleFind(instruction);
            } else if (command.type == Parser.Type.STATISTICS) {
                return handleStatistics();
            } else {
                throw new BobException("wot?");
            }
        } catch (BobException | IOException e) {
            return ui.showError(e.getMessage());
        }
    }

    public boolean getIsFinished() {
        return this.isFinished;
    }

    public String showGreeting() {
        return ui.showGreeting();
    }

    /**
     * Helper method to handle Bye commands.
     */
    public String handleBye() {
        isFinished = true;
        return ui.showBye();
    }

    /**
     * Helper method to handle List commands.
     */
    public String handleList() {
        assert tasksList != null : "tasksList must be initialised";
        return ui.showList(tasksList.getTasksList());
    }

    /**
     * Helper method to handle Mark commands.
     */
    public String handleMark(String instruction) throws BobException, IOException {
        int n = Integer.parseInt(instruction.split("\\s+")[1]);
        if (n < 1 || n > tasksList.size()) {
            throw new BobException("Invalid task number!");
        }
        tasksList.mark(n - 1);
        storage.save(tasksList.getTasksList());
        return ui.showMark(tasksList.get(n - 1));
    }

    /**
     * Helper method to handle Unmark commands.
     */
    public String handleUnmark(String instruction) throws BobException, IOException {
        int n = Integer.parseInt(instruction.split("\\s+")[1]);
        if (n < 1 || n > tasksList.size()) {
            throw new BobException("Invalid task number!");
        }
        tasksList.unmark(n - 1);
        storage.save(tasksList.getTasksList());
        return ui.showUnmark(tasksList.get(n - 1));
    }

    /**
     * Helper method to handle Todo commands.
     */
    public String handleTodo(String instruction) throws BobException, IOException {
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
    }

    /**
     * Helper method to handle Deadline commands.
     */
    public String handleDeadline(String description, String deadlineString) throws IOException {
        LocalDate deadlineDate = LocalDate.parse(deadlineString, dateFormat);
        assert deadlineDate != null : "Parser must produce a valid date";
        TaskDeadLine task = new TaskDeadLine(description, deadlineDate);
        tasksList.add(task);
        storage.save(tasksList.getTasksList());
        return ui.showAdded(task, tasksList.size());
    }

    /**
     * Helper method to handle Event commands.
     */
    public String handleEvent(String description, String startDateString, String endDateString) throws IOException {
        LocalDate startDate = LocalDate.parse(startDateString, dateFormat);
        LocalDate endDate = LocalDate.parse(endDateString, dateFormat);
        assert !endDate.isBefore(startDate) : "Event end date must be >= start date";
        TaskEvent task = new TaskEvent(description, startDate, endDate);
        tasksList.add(task);
        storage.save(tasksList.getTasksList());
        return ui.showAdded(task, tasksList.size());
    }

    /**
     * Helper method to handle Delete commands.
     */
    public String handleDelete(String instruction) throws BobException, IOException {
        assert tasksList.size() != 0 : "Task list should not be empty";
        int n = Integer.parseInt(instruction.trim().split("\\s+")[1]);
        if (n < 1 || n > tasksList.size()) {
            throw new BobException("Invalid task number!");
        }
        Task taskToDelete = tasksList.get(n - 1);
        tasksList.remove(n - 1);
        storage.save(tasksList.getTasksList());
        return ui.showDeleted(taskToDelete, tasksList.size());
    }

    /**
     * Helper method to handle Find commands.
     */
    public String handleFind(String instruction) throws BobException {
        String keyword = instruction.substring(5).trim();
        ArrayList<Task> matchingTaskList = new ArrayList<>();
        if (keyword.isEmpty()) {
            throw new BobException("OOPS!!! The keyword cannot be empty.");
        }
        matchingTaskList = tasksList.findTasks(keyword);
        return ui.showFind(matchingTaskList);
    }

    /**
     * Helper method to count number of tasks done and not done.
     */
    public String handleStatistics() {
        int countOfMark = tasksList.countMark();
        int countOfUnmark = tasksList.countUnmark();
        return ui.showStatistics(countOfMark, countOfUnmark);
    }

}
