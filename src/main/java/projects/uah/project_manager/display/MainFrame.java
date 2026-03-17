package projects.uah.project_manager.display;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Project Manager");
        setSize(900, 600);
        setMinimumSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Pull in the project panel
        add(new ProjectPanel(), BorderLayout.CENTER);

        setVisible(true);
    }
}