package projects.uah.project_manager.display;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import projects.uah.project_manager.model.Project;

public class MainFrame extends JFrame {

    private JTabbedPane projectTabs;
    private java.util.List<Project> projects = new ArrayList<>();
    private java.util.List<Project> del_projects = new ArrayList<>();
    
    public MainFrame() {
        setTitle("Project Manager");
        setSize(900, 600);
        setMinimumSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Pull in the project panel
        
        setVisible(true);
        
        
   

    /**
     * Constructs a new ProjectPanel and initializes its UI components.
     */
        setLayout(new BorderLayout());

        // --- Top button bar ---
        JButton addProjectBtn = new JButton("New Project");
        JButton delProjectBtn = new JButton("Delete");
        JButton editPriorityBtn = new JButton("Edit Priority");
        JButton delProjectLst = new JButton("Check Deleted");
        
        
        JPanel topBar = new JPanel(new BorderLayout());
        
        // --- Left buttons ---
        JPanel leftBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftBtns.add(addProjectBtn);
        leftBtns.add(delProjectBtn);
        
        // --- Right buttons ---
        JPanel rightBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightBtns.add(editPriorityBtn);
        rightBtns.add(delProjectLst);

        topBar.add(leftBtns, BorderLayout.WEST);
        topBar.add(rightBtns, BorderLayout.EAST);
        
        // --- Tabbed pane ---
        projectTabs = new JTabbedPane(JTabbedPane.TOP);
        projectTabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        // --- Listeners ---
        addProjectBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Project name:");
            System.out.println("Name entered: " + name);
            
            //checks if project name exists
            boolean exists = projects.stream().anyMatch(p -> p.getName().equalsIgnoreCase(name));
            
            if(exists){
                JOptionPane.showMessageDialog(this, "A project with that name already exists.");
            } else if (name != null && !name.isBlank()) {
                String description = JOptionPane.showInputDialog(this, "Project description:");
                projects.add(new Project(name, description, LocalDate.now(), projects.size()+1 , true));
                reloadTabs();
                projectTabs.setSelectedIndex(projectTabs.getTabCount() - 1);
            }
        });

        delProjectBtn.addActionListener(e -> {
            // gets a list of current project names
            String[] projectNames = projects.stream()
                .map(Project::getName)
                .toArray(String[]::new);

            if(projects.isEmpty()){
                JOptionPane.showMessageDialog(this, "No projects to delete.");
                return;
            }
            
            
            JList<String> project_list = new JList<>(projectNames);
            project_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            project_list.setSelectedIndex(0);

            
            int select_project = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(project_list),
                "Select Project to Delete",
                JOptionPane.OK_CANCEL_OPTION
            );

            
            
            if(select_project == JOptionPane.OK_OPTION){
                
                int double_check = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
                );
                
                if(double_check == JOptionPane.YES_OPTION){
                    int index = project_list.getSelectedIndex();
                    projects.get(index).setActive(false);
                    del_projects.add(projects.get(index));
                    projects.remove(index);
                    projectTabs.removeTabAt(index);
                }
            }
            
        });
        
        editPriorityBtn.addActionListener(e -> {
            
        });
        
        // --- Border modifications ---
        topBar.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); 
        projectTabs.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(5,5,5,5),
                BorderFactory.createLineBorder(Color.GRAY, 1)
        ));
        
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
