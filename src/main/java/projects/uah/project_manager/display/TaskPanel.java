package projects.uah.project_manager.display;

import javax.swing.*;
import java.awt.*;
import projects.uah.project_manager.model.Task;

public class TaskPanel extends JPanel {

    /**
     * Constructs a new TaskPanel and initializes its UI components.
     * @param task to display and keep data 
     */
    public TaskPanel(Task task) {
    setPreferredSize(new Dimension(200, 300));
    setBorder(BorderFactory.createLineBorder(Color.GRAY));
    setLayout(new BorderLayout());
    add(new JLabel(task.getName()), BorderLayout.NORTH);
}
}