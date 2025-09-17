package bobmortimer;

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

import bobmortimer.tasks.Task;
import bobmortimer.tasks.TaskDeadLine;
import bobmortimer.tasks.TaskEvent;
import bobmortimer.tasks.TaskToDo;

/**
 * Handles loading tasks from a file and saving tasks back to the file.
 */
public class Storage {

    private String filePath;
    private DateTimeFormatter dateFormat;

    private Pattern header = Pattern.compile("^\\[(T|D|E)\\]\\[(X| )\\]\\s+(.*)$");
    private Pattern deadlinePattern = Pattern.compile("^(.*)\\s*\\(by:\\s*(.*)\\)\\s*$", Pattern.CASE_INSENSITIVE);
    private Pattern eventPattern = Pattern.compile("^(.*)\\s*\\(from:\\s*(.*?)\\s+to:\\s*(.*?)\\)\\s*$",
            Pattern.CASE_INSENSITIVE);

    /**
     * Creates a new storage handler.
     * @param filePath the file path to load and save tasks
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        this.dateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
    }

    /**
     * Loads tasks from the file.
     *
     * @return a list of tasks
     * @throws FileNotFoundException if the file is not found
     */
    public ArrayList<Task> load() throws FileNotFoundException {
        File f = new File(filePath);
        ArrayList<Task> tasksList = new ArrayList<>();
        Scanner s = new Scanner(f);

        while (s.hasNext()) {
            String line = s.nextLine().trim();
            Task task = parseTaskLine(line);
            if (task == null) {
                continue;
            }
            tasksList.add(task);
        }

        return tasksList;
    }

    /**
     * Parses one line into a Task.
     *
     * @param line the raw line
     * @return the Task, or null if unparsable
     */
    private Task parseTaskLine(String line) {
        Matcher match = header.matcher(line);
        if (!match.matches()) {
            System.err.println("Skipping unparsable line: " + line);
            return null;
        }

        String taskType = match.group(1);
        boolean isDone = match.group(2).equals("X");
        String rest = match.group(3).trim();

        Task task = null;
        if (taskType.equals("T")) {
            task = new TaskToDo(rest);
        } else if (taskType.equals("D")) {
            task = parseDeadline(rest);
        } else if (taskType.equals("E")) {
            task = parseEvent(rest);
        }

        if (task != null && isDone) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Parses a deadline task string.
     *
     * @param rest the remaining line after header
     * @return the TaskDeadLine, or null if unparsable
     */
    private Task parseDeadline(String rest) {
        Matcher deadlineMatcher = deadlinePattern.matcher(rest);
        if (!deadlineMatcher.matches()) {
            System.err.println("Skipping unparsable deadline: " + rest);
            return null;
        }
        LocalDate deadlineDate = LocalDate.parse(deadlineMatcher.group(2).trim(), dateFormat);
        return new TaskDeadLine(deadlineMatcher.group(1).trim(), deadlineDate);
    }

    /**
     * Parses an event task string.
     *
     * @param rest the remaining line after header
     * @return the TaskEvent, or null if unparsable
     */
    private Task parseEvent(String rest) {
        Matcher eventMatcher = eventPattern.matcher(rest);
        if (!eventMatcher.matches()) {
            System.err.println("Skipping unparsable event: " + rest);
            return null;
        }
        LocalDate startDate = LocalDate.parse(eventMatcher.group(2).trim(), dateFormat);
        LocalDate endDate = LocalDate.parse(eventMatcher.group(3).trim(), dateFormat);
        return new TaskEvent(eventMatcher.group(1).trim(), startDate, endDate);
    }


    /**
     * Saves tasks to the file.
     * @param tasksList the list of tasks to save
     * @throws IOException if an I/O error occurs
     */
    public void save(ArrayList<Task> tasksList) throws IOException {
        try (FileWriter fw = new FileWriter(filePath)) {
            for (int i = 0; i < tasksList.size(); i++) {
                fw.write(tasksList.get(i).toString() + System.lineSeparator());
            }
        }
    }

}
