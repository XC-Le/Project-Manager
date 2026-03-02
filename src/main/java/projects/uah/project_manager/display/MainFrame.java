package projects.uah.project_manager.display;

import javax.swing.JFrame;

/**
 * The main application window for the Project Manager.
 * Serves as the top-level container for all panels and UI components.
 *
 * @author XC-Le
 * @version 1.0
 */
public class MainFrame extends JFrame {

    /**
     * Constructs and initializes the main application window.
     * Sets up the layout, panels, and window properties.
     */
    public MainFrame() {
        setTitle("Project-Manager");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Initializes and arranges the UI components within the frame.
     */
    private void initComponents() {

    }
}