package projects.uah.project_manager.display;

import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;
import projects.uah.project_manager.model.Project;
import projects.uah.project_manager.model.Task;
import projects.uah.project_manager.manager.*;

/**
 * A Swing panel that displays and manages the list of projects.
 * Allows users to view, create, and interact with projects through the graphical interface.
 *
 * @author XC-Le and Duncan Williams
 * @version 1.2
 */
public class ProjectPanel extends JPanel {
    
    JPanel taskListPanel = new JPanel();
    
    /**
     * Constructs a new ProjectPanel and initializes its UI components.
     * @param project
     */
    public ProjectPanel(ProjectManager pm, Project project) {
        
        reloadTasks(pm, project);
        
        setLayout(new BorderLayout());    
        setVisible(true);
        
        // New button panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        
        // New button panel on right side of project panel
        JPanel rightBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        // add task button
        JButton addTaskBtn = new JButton("New Task");
        rightBtns.add(addTaskBtn, BorderLayout.EAST);
        
        // delete task button
        JButton delTaskBtn = new JButton("Delete Task");
        rightBtns.add(delTaskBtn, BorderLayout.SOUTH);
        
        buttonPanel.add(rightBtns);
        add(buttonPanel, BorderLayout.EAST);
        
        // Creates the panel for the tasks
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.X_AXIS));
        
        // M
        JScrollPane scrollPane = new JScrollPane(taskListPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        add(scrollPane, BorderLayout.CENTER);

        // Listeners 
        
        // listener for add button
        addTaskBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Task name:");
            
            boolean exists = project.getTasks().stream().anyMatch(p -> p.getName().equalsIgnoreCase(name));

            if(exists){
                JOptionPane.showMessageDialog(this, "A tasks with that name already exists in this project.");
            } else if(name != null && !name.isBlank()) {
                project.addTask(new Task(name, "", LocalDate.now(), 1, false));
                DataManager.save(pm);
                reloadTasks(pm, project);
            }
        });  
        
        // listener for delete button
        delTaskBtn.addActionListener(e -> {
            String[] task_names = project.getTasks().stream().map(Task::getName).toArray(String[]::new);
            if(project.getTasks().isEmpty()){
                JOptionPane.showMessageDialog(this, "No tasks to delete.");
                return;
            }
            JList<String> task_list = new JList<>(task_names);
            task_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            task_list.setSelectedIndex(0);
            int select_project = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(task_list),
                "Select Project to Delete",
                JOptionPane.OK_CANCEL_OPTION
            );
            if(select_project == JOptionPane.OK_OPTION){
                int double_check = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
                );
                if(double_check == JOptionPane.YES_OPTION){
                    int  index = task_list.getSelectedIndex();
                    project.removeTask(index);
                    reloadTasks(pm, project);
                    DataManager.save(pm); 
                }
            }
        });
        
        reloadTasks(pm, project);
    }
    
    private void reloadTasks(ProjectManager pm, Project project) {
        // removes all tasks to add from scratch
        taskListPanel.removeAll();
        for(Task task : project.getTasks()){
            taskListPanel.add(new TaskPanel(pm, project, task));
        }
        taskListPanel.revalidate();
        taskListPanel.repaint();
        
    }
}