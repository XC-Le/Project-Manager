package projects.uah.project_manager.display;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.CompoundBorder;
import projects.uah.project_manager.model.Task;

public class TaskPanel extends JPanel {

    /**
     * Constructs a new TaskPanel and initializes its UI components.
     * @param task to display and keep data 
     */
    public TaskPanel(Task task) {
    setPreferredSize(new Dimension(200, 300));
    setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(8,8,8,8),
        new CompoundBorder(BorderFactory.createLineBorder(Color.GRAY),BorderFactory.createEmptyBorder(8, 8, 8, 8))));
    setLayout(new BorderLayout());
    
    add(new JLabel(task.getName()), BorderLayout.NORTH); // placeholder
    
    // New button panel
    JPanel buttonPanel = new JPanel(new BorderLayout());

    // Remove task button added to the bottom right side of task panel
    JPanel rightBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton removeTaskBtn = new JButton("Delete");
    rightBtns.add(removeTaskBtn, BorderLayout.EAST);
    buttonPanel.add(rightBtns);
    add(buttonPanel, BorderLayout.SOUTH);
}
}