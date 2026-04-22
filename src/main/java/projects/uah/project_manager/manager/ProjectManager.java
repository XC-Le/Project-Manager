package projects.uah.project_manager.manager;

import java.util.ArrayList;
import projects.uah.project_manager.model.Project;
import java.util.List;

/**
 * Manages the collections of active, deleted, and completed projects
 * within the Project Manager application.
 * Provides functionality to add, remove, complete, retrieve, and list projects.
 *
 * @author XC-Le, Duncan Williams, and WhispyWaddle
 * @version 1.1
 */
public class ProjectManager {

    private java.util.List<Project> projects = new ArrayList<>();
    private java.util.List<Project> del_projects = new ArrayList<>();
    private java.util.List<Project> completed_projects = new ArrayList<>();

    /**
     * Constructs a new ProjectManager with empty project lists.
     */
    public ProjectManager() {
    }

    /**
     * Adds a project to the active project list.
     *
     * @param project the project to add
     */
    public void addProject(Project project) {
        projects.add(project);
    }

    /**
     * Moves a project from one position to another in the active project list.
     *
     * @param project      the project to move
     * @param initialIndex the index to move from
     * @param finalIndex   the index to move to
     */
    public void moveProject(Project project, int initialIndex, int finalIndex) {
        projects.remove(initialIndex);
        projects.add(finalIndex, project);
    }

    /**
     * Removes a project from the active list and moves it to the deleted list.
     *
     * @param index the index of the project to remove
     */
    public void removeProject(int index) {
        projects.get(index).setActive(false);
        del_projects.add(projects.get(index));
        projects.remove(index);
    }

    /**
     * Moves a project from the active list to the completed list.
     *
     * @param index the index of the project to complete
     */
    public void completeProject(int index) {
        completed_projects.add(projects.get(index));
        projects.remove(index);
    }

    /**
     * Returns the list of active projects.
     *
     * @return list of active projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Sets the list of active projects.
     *
     * @param projects the new list of active projects
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Returns a single active project by index.
     *
     * @param index the index of the project
     * @return the project at the given index
     */
    public Project getProject(int index) {
        return projects.get(index);
    }

    /**
     * Returns the list of deleted projects.
     *
     * @return list of deleted projects
     */
    public List<Project> getDeletedProjects() {
        return del_projects;
    }

    /**
     * Sets the list of deleted projects.
     *
     * @param del_projects the new list of deleted projects
     */
    public void setDeletedProjects(List<Project> del_projects) {
        this.del_projects = del_projects;
    }

    /**
     * Returns the list of completed projects.
     *
     * @return list of completed projects
     */
    public List<Project> getCompletedProjects() {
        return completed_projects;
    }

    /**
     * Sets the list of completed projects.
     *
     * @param completed_projects the new list of completed projects
     */
    public void setCompletedProjects(List<Project> completed_projects) {
        this.completed_projects.clear();
        this.completed_projects.addAll(completed_projects);
    }
}