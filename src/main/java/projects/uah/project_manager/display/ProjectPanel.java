package projects.uah.project_manager.display;

import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;
import projects.uah.project_manager.model.Project;


/**
 * A Swing panel that displays and manages the list of projects.
 * Allows users to view, create, and interact with projects through the graphical interface.
 *
 * @author XC-Le and Duncan Williams
 * @version 1.2
 */
public class ProjectPanel extends JPanel {

    
    /**
     * Constructs a new ProjectPanel and initializes its UI components.
     */
    public ProjectPanel(Project project) {
        setLayout(new BorderLayout());
        
        // New task button
        JButton addTaskBtn = new JButton("New Task");
        
        JPanel tasks = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tasks.add(addTaskBtn);
        add(tasks, BorderLayout.WEST);
         // Listeners 
        
        // listener for add button
        addTaskBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Task name:");
            System.out.println("Task name entered: " + name);
        });
       
        // Placeholder so the tab shows something
        JLabel placeholder = new JLabel("Tasks for: " + project.getName());
        placeholder.setHorizontalAlignment(SwingConstants.CENTER);
        add(placeholder, BorderLayout.CENTER);
    }
}