package projects.uah.project_manager.manager;

import projects.uah.project_manager.model.Project;

import java.util.List;

/**
 * Manages a collection of projects within the Project Manager application.
 * Provides functionality to add, remove, retrieve, and list projects.
 *
 * @author XC-Le
 * @version 1.0
 */
public class ProjectManager {

    /**
     * Constructs a new ProjectManager with an empty project list.
     */
    public ProjectManager() {

    }

    /**
     * Adds a new project to the manager.
     *
     * @param project the project to add
     */
    public void addProject(Project project) {

    }

    /**
     * Removes a project from the manager by button.
     *
     * project removed from list 
     */
    public void removeProject() {
        
    }

    /**
     * Retrieves a project by its name.
     *
     * @param name the name of the project to find
     * @return the matching Project, or null if not found
     */
    public Project getProject(String name) {
        return null;
    }

    /**
     * Returns all projects currently managed.
     *
     * @return a list of all projects
     */
    public List<Project> getAllProjects() {
        return null;
    }

    /**
     * Returns all projects filtered by their active status.
     *
     * @param isActive true to retrieve active projects, false for completed
     * @return a list of projects matching the given status
     */
    public List<Project> getProjectsByStatus(boolean isActive) {
        return null;
    }
}