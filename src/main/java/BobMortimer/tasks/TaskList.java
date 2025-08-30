package BobMortimer.tasks;

import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> tasksList = new ArrayList<>();

    public TaskList(ArrayList<Task> tasksList) {
        this.tasksList = tasksList;
    }

    public int size() {
        return tasksList.size();
    }

    public Task get(int index) {
        return tasksList.get(index);
    }

    public void add(Task t) {
        tasksList.add(t);
    }

    public Task remove(int index) {
        return tasksList.remove(index);
    }

    public void mark(int index) {
        tasksList.get(index).markAsDone();
    }

    public void unmark(int index) {
        tasksList.get(index).markUndone();
    }

    public ArrayList<Task> getTasksList() {
        return tasksList;
    }

    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchingTaskList = new ArrayList<>();
        for (int i = 0; i < tasksList.size(); i++) {
            if (tasksList.get(i).containsKeyword(keyword)) {
                matchingTaskList.add(tasksList.get(i));
            }
        }
        return matchingTaskList;
    }

}

