package projects.uah.project_manager.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a task within a project in the Project Manager application.
 * A task has a name, description, due date, and a priority level.
 *
 * @authors XC-Le and Duncan Williams
 * @version 1.3
 */
public class Task {

    private String name;
    private String description;
    private final LocalDate creationDate;
    private LocalDate dueDate;
    private int priority;
    private boolean isComplete;
    private List<Subtask> subtasks;
    private List<Subtask> deletedSubtasks = new ArrayList<>();
    /**
     * Constructs a new Task with the specified details.
     *
     * @param name        the name/title of the task
     * @param description a brief description of the task
     * @param dueDate     the due date of the task
     * @param priority    the priority level of the task
     * @param isComplete  whether or not the task is complete
     */
    public Task(String name, String description, LocalDate dueDate, int priority, boolean isComplete) {
        this.name = name;
        this.description = description;
        this.creationDate = LocalDate.now();
        this.dueDate = dueDate;
        this.priority = priority;
        this.isComplete = isComplete;
        this.subtasks = new ArrayList<>();
        
    }

    /**
     * Returns the name of the task.
     *
     * @return the task name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the task.
     *
     * @param name the new task name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the task.
     *
     * @return the task description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description the new task description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }
    
    /**
     * Returns the due date of the task.
     *
     * @return the task due date
     */
    public LocalDate getDueDate() {
        return this.dueDate;
    }

    /**
     * Sets the due date of the task.
     *
     * @param dueDate the new due date
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Returns the priority level of the task.
     *
     * @return the priority level
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * Sets the priority level of the task.
     *
     * @param priority the new priority level
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
 
    /**
     * Returns whether the task is complete or not.
     *
     * @return the completion value
     */
    public boolean getCompletion() {
        return this.isComplete;
    }

    /**
     * Sets whether the task is complete or not.
     *
     * @param isComplete true = task is finished, false = task is not finished
     */
    public void setCompletion(boolean isComplete) {
        this.isComplete = isComplete;
    }
    
    public List<Subtask> getSubtasks(){
        return this.subtasks;
    }
    
    public Subtask getSubtask(int index){
        return subtasks.get(index);
    }
    
    public List<Subtask> getDeletedSubtasks() {
        if(deletedSubtasks == null) deletedSubtasks = new ArrayList<>();
        return this.deletedSubtasks;
    }
    
    public void addSubtask(Subtask subtask){
        this.subtasks.add(subtask);
    }
    
    public void removeSubtask(int index) {
        if(deletedSubtasks == null) deletedSubtasks = new ArrayList<>();
        deletedSubtasks.add(subtasks.get(index));
        subtasks.remove(index);
}
}