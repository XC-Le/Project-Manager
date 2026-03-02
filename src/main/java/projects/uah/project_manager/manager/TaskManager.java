package projects.uah.project_manager.manager;

import projects.uah.project_manager.model.Task;
import java.util.List;

/**
 * Manages a collection of tasks associated with projects.
 * Provides functionality to add, remove, retrieve, and list tasks.
 *
 * @author XC-Le
 * @version 1.0
 */
public class TaskManager {

    /**
     * Constructs a new TaskManager with an empty task list.
     */
    public TaskManager() {

    }

    /**
     * Adds a new task to the manager.
     *
     * @param task the task to add
     */
    public void addTask(Task task) {

    }

    /**
     * Removes a task from the manager by name.
     *
     * @param name the name of the task to remove
     * @return true if the task was found and removed, false otherwise
     */
    public boolean removeTask(String name) {
        return false;
    }

    /**
     * Retrieves a task by its name.
     *
     * @param name the name of the task to find
     * @return the matching Task, or null if not found
     */
    public Task getTask(String name) {
        return null;
    }

    /**
     * Returns all tasks currently managed.
     *
     * @return a list of all tasks
     */
    public List<Task> getAllTasks() {
        return null;
    }

    /**
     * Returns all tasks filtered by priority level.
     *
     * @param priority the priority level to filter by
     * @return a list of tasks matching the given priority
     */
    public List<Task> getTasksByPriority(int priority) {
        return null;
    }
}