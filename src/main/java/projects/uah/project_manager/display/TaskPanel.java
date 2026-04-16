package projects.uah.project_manager.display;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.CompoundBorder;
import projects.uah.project_manager.manager.ProjectManager;
import projects.uah.project_manager.model.Project;
import projects.uah.project_manager.model.Task;

public class TaskPanel extends JPanel {

    /**
     * Constructs a new TaskPanel and initializes its UI components.
     * @param pm      project manager to save data
     * @param project project that the task is part of
     * @param task to display and keep data 
     */
    public TaskPanel(ProjectManager pm, Project project, Task task) {
        setPreferredSize(new Dimension(200, 300));
        setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(8,8,8,8),
            new CompoundBorder(BorderFactory.createLineBorder(Color.GRAY),BorderFactory.createEmptyBorder(8, 8, 8, 8))));
        setLayout(new BorderLayout());

        //Create time text added to the task panel
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(new JLabel(task.getName()), BorderLayout.WEST);
        infoPanel.add(new JLabel("Created: " + task.getCreationDate()), BorderLayout.EAST);
        add(infoPanel, BorderLayout.NORTH);
        
        // New button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Add subtask button
        JButton addSubtaskBtn = new JButton("Add Subtask");
        buttonPanel.add(addSubtaskBtn);
        
        // Remove subtask button added to the bottom right side of task panel
        JButton removeSubtaskBtn = new JButton("Delete Subtask");
        buttonPanel.add(removeSubtaskBtn);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
    }
}