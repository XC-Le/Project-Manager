package projects.uah.project_manager.display;

import projects.uah.project_manager.model.Project;

import javax.swing.*;
import java.awt.*;

public class TaskPanel extends JPanel {

    /**
     * Constructs a new TaskPanel and initializes its UI components.
     */
    public TaskPanel(Project project) {
        setLayout(new BorderLayout());
 
       // Placeholder so the tab shows something
        JLabel placeholder = new JLabel("Tasks for: " + project.getName());
        placeholder.setHorizontalAlignment(SwingConstants.CENTER);
        add(placeholder, BorderLayout.CENTER);
    }
}