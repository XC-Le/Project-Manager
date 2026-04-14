/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projects.uah.project_manager.model;

import java.time.LocalDate;

/**
 *
 * @author super
 */
public class Subtask {
    private String name;
    private final LocalDate creationDate;
    private LocalDate dueDate;
    private boolean isComplete;
    
    public Subtask(String name, LocalDate dueDate, boolean isComplete){
        this.name = name;
        this.creationDate = LocalDate.now();
        this.dueDate = dueDate;
        this.isComplete = isComplete;
    }
    
    /**
     * Returns the name of the subtask.
     *
     * @return the subtask name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the subtask.
     *
     * @param name the new subtask name
     */
    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }
    
    /**
     * Returns the due date of the subtask.
     *
     * @return the subtask due date
     */
    public LocalDate getDueDate() {
        return this.dueDate;
    }

    /**
     * Sets the due date of the subtask.
     *
     * @param dueDate the new due date
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
 
    /**
     * Returns whether the subtask is complete or not.
     *
     * @return the completion value
     */
    public boolean getCompletion() {
        return this.isComplete;
    }

    /**
     * Sets whether the subtask is complete or not.
     *
     * @param isComplete true = subtask is finished, false = subtask is not finished
     */
    public void setCompletion(boolean isComplete) {
        this.isComplete = isComplete;
    }
}
