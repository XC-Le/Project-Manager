package projects.uah.project_manager.display;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import projects.uah.project_manager.model.Project;
import java.time.LocalDate;

/**
 * A Swing panel that displays and manages the list of projects.
 * Allows users to view, create, and interact with projects through the graphical interface.
 *
 * @author XC-Le
 * @version 1.0
 */
public class ProjectPanel extends JPanel {

    private JTabbedPane projectTabs;
    private List<Project> projects = new ArrayList<>();

    /**
     * Constructs a new ProjectPanel and initializes its UI components.
     */
    public ProjectPanel() {
        setLayout(new BorderLayout());

        // --- Top button bar ---
        JButton addProjectBtn = new JButton("New Project");
        JButton delProjectBtn = new JButton("Delete");

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topBar.add(addProjectBtn);
        topBar.add(delProjectBtn);

        // --- Tabbed pane ---
        projectTabs = new JTabbedPane(JTabbedPane.TOP);
        projectTabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        // --- Listeners ---
        addProjectBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Project name:");
            System.out.println("Name entered: " + name); // add this
            if (name != null && !name.isBlank()) {
                projects.add(new Project(name, "", LocalDate.now(), true));
                reloadTabs();
                projectTabs.setSelectedIndex(projectTabs.getTabCount() - 1);
            }
        });

        delProjectBtn.addActionListener(e -> {
            int idx = projectTabs.getSelectedIndex();
            if (idx != -1) {
                projects.remove(idx);
                reloadTabs();
            }
        });

        // --- Assemble ---
        add(topBar, BorderLayout.NORTH);
        add(projectTabs, BorderLayout.CENTER);

        // --- Load initial data ---
        reloadTabs();
    }

    private void reloadTabs() {
        System.out.println("reloadTabs called, projects size: " + projects.size());
        projectTabs.removeAll();
        for (Project project : projects) {
            System.out.println("Adding tab: " + project.getName());
            projectTabs.addTab(project.getName(), new TaskPanel(project));
        }
        projectTabs.revalidate(); // add this
        projectTabs.repaint();    // add this
    }
}