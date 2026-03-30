package projects.uah.project_manager.manager;

import java.util.ArrayList;
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

    private final java.util.List<Task> tasks = new ArrayList<>();
    
    /**
     * Constructs a new TaskManager with an empty task list.
     */
    public TaskManager() {

    }
    /**
     * Adds a task object to the project's task list
     * 
     * @param task Project object that is being added
     */
    public void addTask(Task task){
        tasks.add(task);
    }
}