package projects.uah.project_manager;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Font;
import projects.uah.project_manager.display.MainFrame;
import projects.uah.project_manager.manager.*;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
/**
 * Entry point for the Project Manager application.
 * Initializes and launches the Swing GUI.
 *
 * @author XC-Le
 * @version 1.0
 */
public class Project_Manager {

    
    /**
     * Launches the Project Manager application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        
        
        UIManager.put("Component.accentColor", new Color(0x2D7DD2));
        UIManager.put("Button.arc", 10);
        UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));
        FlatLightLaf.setup(); // or FlatDarkLaf.setup()

        
        SwingUtilities.invokeLater(() -> {
            ProjectManager pm = new ProjectManager();
            new MainFrame(pm);
        });
    }
}