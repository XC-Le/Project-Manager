package projects.uah.project_manager.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a project in the Project Manager
 * A project has a name, description, due date, and a status
 * indicating whether it is active or complete.
 *
 * @author XC-Le
 * @version 1.0
 */
public class Project {

    private String name;
    private String description;
    private LocalDate createdDate;
    private LocalDate dueDate;
    private boolean isActive;
    private List<Task> tasks;
    
    /**
     * Constructs a new Project with the specified details.
     *
     * @param name        the title of the project
     * @param description a brief description of the project
     * @param dueDate     the due date of the project
     * @param isActive    true if the project is active, false if complete
     */
    public Project(String name, String description, LocalDate dueDate, boolean isActive) {
        this.name=name;
        this.description=description;
        this.dueDate=dueDate;
        this.isActive=isActive;
        this.tasks = new ArrayList<>();
    }

    /**
     * Returns the name of the project.
     *
     * @return the project name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the project.
     *
     * @param name the new project name
     */
    public void setName(String name) {
        this.name=name;
    }

    /**
     * Returns the description of the project.
     *
     * @return the project description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the project.
     *
     * @param description the new project description
     */
    public void setDescription(String description) {
        this.description=description;
    }

    
    
    public LocalDate getCreatedDate(){
        return this.createdDate;
    }
    
    public void setCreatedDate(LocalDate createdDate){
        this.createdDate = createdDate;
    }
    /**
     * Returns the due date of the project.
     *
     * @return the project due date
     */
    public LocalDate getDueDate() {
        return this.dueDate;
    }

    /**
     * Sets the due date of the project.
     *
     * @param dueDate the new due date
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate=dueDate;
    }

    /**
     * Returns whether the project is currently active.
     *
     * @return true if the project is active, false if complete
     */
    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Sets the active status of the project.
     *
     * @param isActive true to mark as active, false to mark as complete
     */
    public void setActive(boolean isActive) {
        this.isActive=isActive;
    }
    
    /**
     * Returns the tasks for the project.
     *
     * @return the list of tasks
     */
    public List<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Sets the tasks for the project.
     *
     * @param tasks the tasks for the project
     */
    public void setTasks(List<Task> tasks) {
        this.tasks=tasks;
    }
    
    public void addTask(Task task){
        this.tasks.add(task);
    }
    public void removeTask(Task task){
        this.tasks.remove(task);
    }

}