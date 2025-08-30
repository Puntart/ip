package BobMortimer.tasks;

import java.util.ArrayList;

/**
 * Class containing the task list
 */
public class TaskList {

    private ArrayList<Task> tasksList = new ArrayList<>();

    /**
     * Constructor
     *
     * @param tasksList the initial list of tasks to store in this TaskList
     */
    public TaskList(ArrayList<Task> tasksList) {
        this.tasksList = tasksList;
    }

    /**
     * Method to return the size of the task list
     *
     * @return the number of tasks currently in the list
     */
    public int size() {
        return tasksList.size();
    }

    /**
     * Getter to retrive tasks in the list according to their index
     *
     * @param index the index of the task to retrieve
     * @return the Task at the specified index
     */
    public Task get(int index) {
        return tasksList.get(index);
    }

    /**
     * Add task to the task list
     *
     * @param t the Task to add to the list
     */
    public void add(Task t) {
        tasksList.add(t);
    }

    /**
     * Remove a task from the task list according to their index
     *
     * @param index the index of the Task to remove
     * @return the Task that was removed
     */
    public Task remove(int index) {
        return tasksList.remove(index);
    }

    /**
     * Mark the task according to the index
     *
     * @param index the index of the Task to mark as done
     */
    public void mark(int index) {
        tasksList.get(index).markAsDone();
    }

    /**
     * Unmark the task according to the index
     *
     * @param index the index of the Task to mark as not done
     */
    public void unmark(int index) {
        tasksList.get(index).markUndone();
    }

    /**
     * Getter method for the task list
     *
     * @return the underlying ArrayList containing all tasks
     */
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
