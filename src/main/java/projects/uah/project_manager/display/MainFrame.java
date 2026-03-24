package projects.uah.project_manager.display;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.time.LocalDate;
import projects.uah.project_manager.model.Project;
import projects.uah.project_manager.model.DraggableTabbedPane;
import projects.uah.project_manager.manager.ProjectManager;


/**
 * Mainframe for the entire project
 * Builds the header along with it's buttons
 *
 * @author XC-Le
 * @version 1.0
 */

public class MainFrame extends JFrame {

    private DraggableTabbedPane projectTabs;
    
    /**
     * Creates MainFrame for project
     * 
     * @param pm ProjectManager
     */
    public MainFrame(ProjectManager pm) {
        // Window settings
        setTitle("Project Manager");
        setSize(900, 600);
        setMinimumSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());        
        setVisible(true);
        
        
        setLayout(new BorderLayout());

        // Top button bar
        JButton addProjectBtn = new JButton("New Project");
        JButton delProjectBtn = new JButton("Delete");
        JButton editPriorityBtn = new JButton("Edit Priority");
        JButton delProjectLst = new JButton("Check Deleted");
        
        
        JPanel topBar = new JPanel(new BorderLayout());
        
        // Left buttons 
        JPanel leftBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftBtns.add(addProjectBtn);
        leftBtns.add(delProjectBtn);
        
        // Right buttons 
        JPanel rightBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightBtns.add(editPriorityBtn);
        rightBtns.add(delProjectLst);

        topBar.add(leftBtns, BorderLayout.WEST);
        topBar.add(rightBtns, BorderLayout.EAST);
        
        // Tabbed pane 
        projectTabs = new DraggableTabbedPane(pm);
        projectTabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        // Listeners 
        
        // listener for add button
        addProjectBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Project name:");
            System.out.println("Name entered: " + name);
            
            //checks if project name exists
            boolean exists = pm.getProjects().stream().anyMatch(p -> p.getName().equalsIgnoreCase(name));
            
            if(exists){
                JOptionPane.showMessageDialog(this, "A project with that name already exists.");
            } else if (name != null && !name.isBlank()) {
                String description = JOptionPane.showInputDialog(this, "Project description:");
                
                // adds project to project manager and reloads tabs
                pm.addProject(new Project(name, description, LocalDate.now(), true));
                reloadTabs(pm);
                
                projectTabs.setSelectedIndex(projectTabs.getTabCount() - 1);
            }
        });
        
        // listener for delete button
        delProjectBtn.addActionListener(e -> {
            // gets a list of current project names
            String[] projectNames = pm.getProjects().stream().map(Project::getName).toArray(String[]::new);

            // checks if projects is empty
            if(pm.getProjects().isEmpty()){
                JOptionPane.showMessageDialog(this, "No projects to delete.");
                return;
            }
            
            // sets up window to prompt user
            JList<String> project_list = new JList<>(projectNames);
            project_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            project_list.setSelectedIndex(0);

            // prompts user to delete a project
            int select_project = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(project_list),
                "Select Project to Delete",
                JOptionPane.OK_CANCEL_OPTION
            );
            
            // double checks user choice
            if(select_project == JOptionPane.OK_OPTION){
                
                int double_check = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
                );
                
                // removes project and tab
                if(double_check == JOptionPane.YES_OPTION){
                    int index = project_list.getSelectedIndex();
                    pm.removeProject(index);
                    projectTabs.removeTabAt(index);
                }
            }
            
        });
        
        // listener for edit priority button
        editPriorityBtn.addActionListener(e -> {
            
            // calls toggle dragging enable function
            projectTabs.toggleDraggingEnabled();
            
            // updates button color
            if(projectTabs.isDraggingEnabled()){
                editPriorityBtn.setBackground(new Color(100, 149, 237, 60)); // cornflower blue
            }
            else{
                editPriorityBtn.setBackground(UIManager.getColor("Button.background"));

            }
        });
        
        // Border modifications 
        topBar.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); 
        projectTabs.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(5,5,5,5),
                BorderFactory.createLineBorder(Color.GRAY, 1)
        ));
        
        // Assemble 
        add(topBar, BorderLayout.NORTH);
        add(projectTabs, BorderLayout.CENTER);

        // Load initial data 
        reloadTabs(pm);
    }
    
    /**
     * Reloads projects tabs 
     * 
     * @param pm ProjectManager
     */
    private void reloadTabs(ProjectManager pm) {
        System.out.println("reloadTabs called, projects size: " + pm.getProjects().size());
        
        // removes all tabs to add from scratch
        projectTabs.removeAll();
        for (Project project : pm.getProjects()) {
            System.out.println("Adding tab: " + project.getName());
            projectTabs.addTab(project.getName(), new TaskPanel(project));
        }
        projectTabs.revalidate();
        projectTabs.repaint();    
    }
        
}
