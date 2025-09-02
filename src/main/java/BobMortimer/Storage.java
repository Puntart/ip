package BobMortimer;

import BobMortimer.tasks.Task;
import BobMortimer.tasks.TaskDeadLine;
import BobMortimer.tasks.TaskEvent;
import BobMortimer.tasks.TaskToDo;

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

public class Storage {

    private String filePath;
    private DateTimeFormatter dateFormat;

    Pattern header = Pattern.compile("^\\[(T|D|E)\\]\\[(X| )\\]\\s+(.*)$");
    Pattern pDeadline = Pattern.compile("^(.*)\\s*\\(by:\\s*(.*)\\)\\s*$", Pattern.CASE_INSENSITIVE);
    Pattern pEvent = Pattern.compile("^(.*)\\s*\\(from:\\s*(.*?)\\s+to:\\s*(.*?)\\)\\s*$",
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
            if(taskType.equals("T")) {
                task = new TaskToDo(rest);
            } else if(taskType.equals("D")) {
                Matcher mDeadline = pDeadline.matcher(rest);
                if (mDeadline.matches()) {
                    LocalDate deadlineDate = LocalDate.parse(mDeadline.group(2).trim(), dateFormat);
                    task = new TaskDeadLine(mDeadline.group(1).trim(), deadlineDate);
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
