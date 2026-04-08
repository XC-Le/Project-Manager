package projects.uah.project_manager.manager;

import java.util.ArrayList;
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

    private java.util.List<Project> projects = new ArrayList<>();
    private java.util.List<Project> del_projects = new ArrayList<>();
    
    /**
     * Constructs a new ProjectManager with an empty project list.
     */
    public ProjectManager() {
        
    }
    
    /**
     * Adds a project object to the project list
     * 
     * @param project Project object that is being added
     */
    public void addProject(Project project){
        projects.add(project);
    }
    
    /**
     * Moves a project by removing a project from the list and reinserting it elsewhere
     * 
     * @param project object that is being moved
     * @param initialIndex of the project being moved from
     * @param finalIndex of where to project is moved to
     */
    public void moveProject(Project project, int initialIndex, int finalIndex){
        projects.remove(initialIndex);
        projects.add(finalIndex, project);
    }
    
    /**
     * Removes a project from the list
     * 
     * @param index of project being removed
     */
    public void removeProject(int index){
        projects.get(index).setActive(false);
        del_projects.add(projects.get(index));
        projects.remove(index);
    }
    
    /**
     * Returns list of all projects
     * 
     * @return projects
     */
    public List<Project> getProjects(){
        return projects;
    }
    
    
    /**
     * Sets the list of projects
     * 
     * @param projects 
     */
    public void setProjects(List<Project> projects){
        this.projects = projects;
    }
    
    /**
     * Returns the one project from the list of projects
     * 
     * @param index of project 
     * @return project at index
     */
    public Project getProject(int index){
        return projects.get(index);
    }
    
    /**
     * Returns list of all deleted projects
     * 
     * @return del_projects
     */
    public List<Project> getDeletedProjects(){
        return del_projects;
    }
    
    /**
     * Sets the list of deleted projects
     * 
     * @param projects 
     */
    public void setDeletedProjects(List<Project> del_projects){
        this.del_projects = del_projects;
    }
}