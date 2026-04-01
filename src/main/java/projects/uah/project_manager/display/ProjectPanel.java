package projects.uah.project_manager.display;

import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;
import projects.uah.project_manager.model.Project;
import projects.uah.project_manager.model.Task;
import projects.uah.project_manager.manager.TaskManager;

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
    public ProjectPanel(Project project) {
        
        reloadTasks(project);
        
        setLayout(new BorderLayout());    
        setVisible(true);
        
        // New button panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        
        // Add task button added to the right side of project panel
        JPanel rightBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addTaskBtn = new JButton("New Task");
        rightBtns.add(addTaskBtn, BorderLayout.EAST);
        buttonPanel.add(rightBtns);
        
        
        add(buttonPanel, BorderLayout.EAST);
        
        // Creates the panel for the tasks
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.X_AXIS));
        
        // M
        JScrollPane scrollPane = new JScrollPane(taskListPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        
        add(scrollPane, BorderLayout.WEST);

        // Listeners 
        
        // listener for add button
        addTaskBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Task name:");
            
            boolean exists = project.getTasks().stream().anyMatch(p -> p.getName().equalsIgnoreCase(name));

            if(exists){
                JOptionPane.showMessageDialog(this, "A tasks with that name already exists in this project.");
            } else {
                project.addTask(new Task(name, "", LocalDate.now(), 1, false));
                reloadTasks(project);
            }
        });   
    }
    
    private void reloadTasks(Project project) {
        
        taskListPanel.removeAll();
        for(Task task : project.getTasks()){
            taskListPanel.add(new TaskPanel(task));
        }
        taskListPanel.revalidate();
        taskListPanel.repaint();
        
    }
}