package BobMortimer;

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

import BobMortimer.tasks.Task;
import BobMortimer.tasks.TaskDeadLine;
import BobMortimer.tasks.TaskEvent;
import BobMortimer.tasks.TaskToDo;


public class Storage {

    private String filePath;
    private DateTimeFormatter dateFormat;

    private Pattern header = Pattern.compile("^\\[(T|D|E)\\]\\[(X| )\\]\\s+(.*)$");
    private Pattern deadlinePattern = Pattern.compile("^(.*)\\s*\\(by:\\s*(.*)\\)\\s*$", Pattern.CASE_INSENSITIVE);
    private Pattern eventPattern = Pattern.compile("^(.*)\\s*\\(from:\\s*(.*?)\\s+to:\\s*(.*?)\\)\\s*$",
            Pattern.CASE_INSENSITIVE);

    public Storage(String filePath) {
        this.filePath = filePath;
        this.dateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
    }

    public ArrayList<Task> load() throws FileNotFoundException {
        File f = new File(filePath);
        ArrayList<Task> tasksList = new ArrayList<>();
        Scanner s = new Scanner(f);

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
            if (taskType.equals("T")) {
                task = new TaskToDo(rest);
            } else if (taskType.equals("D")) {
                Matcher deadlineMatcher = deadlinePattern.matcher(rest);
                if (!deadlineMatcher.matches()) {
                    System.err.println("Skipping unparsable deadline: " + rest);
                    continue;
                } else if (deadlineMatcher.matches()) {
                    LocalDate deadlineDate = LocalDate.parse(deadlineMatcher.group(2).trim(), dateFormat);
                    task = new TaskDeadLine(deadlineMatcher.group(1).trim(), deadlineDate);
                }
            } else if (taskType.equals("E")) {
                Matcher eventMatcher = eventPattern.matcher(rest);
                if (!eventMatcher.matches()) {
                    System.err.println("Skipping unparsable event: " + rest);
                    continue;
                } else if (eventMatcher.matches()) {
                    LocalDate startDate = LocalDate.parse(eventMatcher.group(2).trim(), dateFormat);
                    LocalDate endDate = LocalDate.parse(eventMatcher.group(3).trim(), dateFormat);
                    task = new TaskEvent(eventMatcher.group(1).trim(),
                            startDate,
                            endDate);
                }
            }

            if (isDone) {
                task.markAsDone();
            }
            tasksList.add(task);
        }

        return tasksList;
    }

    public void save(ArrayList<Task> tasksList) throws IOException {
        try (FileWriter fw = new FileWriter(filePath)) {
            for (int i = 0; i < tasksList.size(); i++) {
                fw.write(tasksList.get(i).toString() + System.lineSeparator());
            }
        }
    }

}
