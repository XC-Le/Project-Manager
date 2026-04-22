package projects.uah.project_manager.manager;

import com.google.gson.*;
import projects.uah.project_manager.model.Project;
import java.io.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Handles serialization and deserialization of application state to and from
 * a JSON flat file using the Gson library. Saves and loads active, deleted,
 * and completed project lists including all tasks and subtasks.
 *
 * @author XC-Le, Duncan Williams, and WhispyWaddle
 * @version 1.1
 */
public class DataManager {

    private static final String SAVE_FILE = System.getProperty("user.home") + "/ProjectManager/projects.json";

    // Gson needs instructions on how to save and load LocalDate since it isn't primitive
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class,
            (JsonSerializer<LocalDate>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
        .registerTypeAdapter(LocalDate.class,
            (JsonDeserializer<LocalDate>) (json, type, ctx) -> LocalDate.parse(json.getAsString()))
        .setPrettyPrinting().create();

    /**
     * Saves the current application state to a JSON file.
     *
     * @param pm the ProjectManager containing all project data to save
     */
    public static void save(ProjectManager pm) {
        new java.io.File(System.getProperty("user.home") + "/ProjectManager").mkdirs();
        SaveData to_save = new SaveData(pm.getProjects(), pm.getDeletedProjects(), pm.getCompletedProjects());
        try(Writer writer = new FileWriter(SAVE_FILE)){
            gson.toJson(to_save, writer);
        } catch(IOException e){
            System.err.println("Failed to save: " + e.getMessage());
        }
    }

    /**
     * Loads application state from the JSON file into the given ProjectManager.
     *
     * @param pm the ProjectManager to load data into
     */
    public static void load(ProjectManager pm) {
        File file = new File(SAVE_FILE);
        if(!file.exists()){
            System.out.println("No save file found.");
            return;
        }
        try(Reader reader = new FileReader(file)){
            SaveData to_load = gson.fromJson(reader, SaveData.class);
            pm.setProjects(to_load.projects);
            pm.setDeletedProjects(to_load.del_projects);
            if(to_load.completed_projects != null){
                pm.setCompletedProjects(to_load.completed_projects);
            }
        } catch(IOException e){
            System.err.println("Failed to load: " + e.getMessage());
        }
    }

    /**
     * Internal data structure used for JSON serialization.
     * Holds all three project lists for saving and loading.
     */
    private static class SaveData {

        List<Project> projects;
        List<Project> del_projects;
        List<Project> completed_projects;

        /**
         * Constructs a SaveData object with the given project lists.
         *
         * @param projects           the list of active projects
         * @param del_projects       the list of deleted projects
         * @param completed_projects the list of completed projects
         */
        SaveData(List<Project> projects, List<Project> del_projects, List<Project> completed_projects) {
            this.projects = projects;
            this.del_projects = del_projects;
            this.completed_projects = completed_projects;
        }
    }
}