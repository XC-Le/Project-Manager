package projects.uah.project_manager.model;

import java.time.LocalDate;

/**
 * Represents a subtask within a task in the Project Manager application.
 * A subtask has a name, creation date, due date, and a completion status.
 *
 * @author XC-Le, Duncan Williams, and WhispyWaddle
 * @version 1.0
 */
public class Subtask {

    private String name;
    private final LocalDate creationDate;
    private LocalDate dueDate;
    private boolean isComplete;

    /**
     * Constructs a new Subtask with the specified details.
     *
     * @param name       the name of the subtask
     * @param dueDate    the due date of the subtask
     * @param isComplete whether or not the subtask is complete
     */
    public Subtask(String name, LocalDate dueDate, boolean isComplete) {
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

    /**
     * Returns the creation date of the subtask.
     *
     * @return the subtask creation date
     */
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
     * @param isComplete true if the subtask is finished, false if not
     */
    public void setCompletion(boolean isComplete) {
        this.isComplete = isComplete;
    }
}