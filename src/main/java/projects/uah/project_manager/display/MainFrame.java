package projects.uah.project_manager.display;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.ZoneId;
import com.toedter.calendar.JDateChooser;
import projects.uah.project_manager.model.*;
import projects.uah.project_manager.manager.*;


/**
 * Mainframe for the entire project
 * Builds the header along with it's buttons
 *
 * @author XC-Le
 * @version 1.0
 */
public class MainFrame extends JFrame {

    private DraggableTabbedPane projectTabs;
    JButton completeProjectBtn = new JButton("Complete");
    
    /**
     * Creates MainFrame for project
     * 
     * @param pm ProjectManager
     */
    public MainFrame(ProjectManager pm) {
        // Window settings
        setTitle("Project Manager");
        setSize(900, 600);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top button bar
        JButton addProjectBtn = new JButton("New Project");
        JButton delProjectBtn = new JButton("Delete Project");
        JButton editPriorityBtn = new JButton("Edit Priority");
        JButton delProjectLst = new JButton("Deleted Projects");
        JButton completedProjectsBtn = new JButton("Completed Projects");
        completeProjectBtn.setVisible(false);
        
        JPanel topBar = new JPanel(new BorderLayout());

        // Left buttons 
        JPanel leftBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftBtns.add(addProjectBtn);
        leftBtns.add(delProjectBtn);
        leftBtns.add(completeProjectBtn);
        
        // Right buttons 
        JPanel rightBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightBtns.add(editPriorityBtn);
        rightBtns.add(delProjectLst);
        rightBtns.add(completedProjectsBtn);

        topBar.add(leftBtns, BorderLayout.WEST);
        topBar.add(rightBtns, BorderLayout.EAST);
        
        // Tabbed pane 
        projectTabs = new DraggableTabbedPane(pm);
        projectTabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        //updates Complete button when switching tabs
        projectTabs.addChangeListener(ce -> checkProjectCompletion(pm, completeProjectBtn));

        // Listeners 
        
        // listener for add button
        addProjectBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Project name:");
            boolean exists = pm.getProjects().stream().anyMatch(p -> p.getName().equalsIgnoreCase(name));
            if(exists){
                JOptionPane.showMessageDialog(this, "A project with that name already exists.");
            } else if (name != null && !name.isBlank()) {
                String description = JOptionPane.showInputDialog(this, "Project description:");
                
                LocalDate dueDate = null;
                
                JDateChooser dateChooser = new JDateChooser();
                dateChooser.setMinSelectableDate(new Date()); // Prevent past dates

                Object[] message = {
                    "Select a due date:", dateChooser
                };

                int option = JOptionPane.showConfirmDialog(null, message, "Due Date", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    Date selectedDate = dateChooser.getDate();
                    dueDate = LocalDate.ofInstant(selectedDate.toInstant(), ZoneId.systemDefault());
                }
                
                pm.addProject(new Project(name, description, dueDate, true));
                reloadTabs(pm);
                projectTabs.setSelectedIndex(projectTabs.getTabCount() - 1);
                DataManager.save(pm);
            }
        });
        
        // listener for delete button
        delProjectBtn.addActionListener(e -> {
            String[] projectNames = pm.getProjects().stream().map(Project::getName).toArray(String[]::new);
            if(pm.getProjects().isEmpty()){
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
                    pm.removeProject(index);
                    projectTabs.removeTabAt(index);
                    DataManager.save(pm); 
                }
            }
        });
        
        // listener for edit priority button
        editPriorityBtn.addActionListener(e -> {
            projectTabs.toggleDraggingEnabled();
            if(projectTabs.isDraggingEnabled()){
                editPriorityBtn.setBackground(new Color(100, 149, 237, 60));
            } else {
                editPriorityBtn.setBackground(UIManager.getColor("Button.background"));
            }
        });


        // listener for check deleted button
        delProjectLst.addActionListener(e -> {
            if(pm.getDeletedProjects().isEmpty()){
                JOptionPane.showMessageDialog(this, "No deleted projects.");
                return;
            }
            
            JDialog dialog = new JDialog(this, "Deleted Projects", true);
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(this);
            dialog.setLayout(new BorderLayout());

            String[] deletedNames = pm.getDeletedProjects().stream()
                .map(p -> p.getName() + "  |  Created: " + p.getCreationDate())
                .toArray(String[]::new);

            JList<String> deletedList = new JList<>(deletedNames);
            dialog.add(new JScrollPane(deletedList), BorderLayout.CENTER);

            JButton restoreBtn = new JButton("Restore");
            restoreBtn.addActionListener(re -> {
                int index = deletedList.getSelectedIndex();
                if(index == -1){
                    JOptionPane.showMessageDialog(dialog, "Please select a project to restore.");
                    return;
                }
                Project restored = pm.getDeletedProjects().get(index);
                pm.getDeletedProjects().remove(index);
                restored.setActive(true);
                pm.addProject(restored);
                reloadTabs(pm);
                dialog.dispose();
                DataManager.save(pm);
            });

            JButton permDeleteBtn = new JButton("Delete Permanently");
            permDeleteBtn.addActionListener(pd -> {
                int index = deletedList.getSelectedIndex();
                if(index == -1){
                    JOptionPane.showMessageDialog(dialog, "Please select a project to delete.");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(
                    dialog,
                    "Are you sure? This cannot be undone.",
                    "Confirm Permanent Delete",
                    JOptionPane.YES_NO_OPTION
                );
                if(confirm == JOptionPane.YES_OPTION){
                    pm.getDeletedProjects().remove(index);
                    dialog.dispose();
                    DataManager.save(pm);
                }
            });

            JPanel bottomBtns = new JPanel(new FlowLayout());
            bottomBtns.add(restoreBtn);
            bottomBtns.add(permDeleteBtn);
            dialog.add(bottomBtns, BorderLayout.SOUTH);
            dialog.setVisible(true);
        });
        // listener for completed projects button
        completedProjectsBtn.addActionListener(e -> {
            if(pm.getCompletedProjects().isEmpty()){
                JOptionPane.showMessageDialog(this, "No completed projects.");
                return;
            }
            String[] completedNames = pm.getCompletedProjects().stream()
                .map(p -> p.getName() + "  |  Created: " + (p.getCreationDate() != null ? p.getCreationDate() : "Unknown"))
                .toArray(String[]::new);
            JDialog dialog = new JDialog(this, "Completed Projects", true);
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(this);
            dialog.setLayout(new BorderLayout());

            JList<String> completedList = new JList<>(completedNames);
            dialog.add(new JScrollPane(completedList), BorderLayout.CENTER);

            JButton restartBtn = new JButton("Restart");
            restartBtn.addActionListener(re -> {
                int index = completedList.getSelectedIndex();
                if(index == -1){
                    JOptionPane.showMessageDialog(dialog, "Please select a project to restart.");
                    return;
                }
                Project restarted = pm.getCompletedProjects().get(index);
                // reset all tasks and subtasks to incomplete
                for(Task t : restarted.getTasks()){
                    t.setCompletion(false);
                    for(Subtask s : t.getSubtasks()){
                        s.setCompletion(false);
                    }
                }
                restarted.setActive(true);
                pm.getCompletedProjects().remove(index);
                pm.addProject(restarted);
                reloadTabs(pm);
                dialog.dispose();
                DataManager.save(pm);
            });

            JButton permDeleteBtn = new JButton("Delete Permanently");
            permDeleteBtn.addActionListener(pd -> {
                int index = completedList.getSelectedIndex();
                if(index == -1){
                    JOptionPane.showMessageDialog(dialog, "Please select a project to delete.");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(
                    dialog,
                    "Are you sure? This cannot be undone.",
                    "Confirm Permanent Delete",
                    JOptionPane.YES_NO_OPTION
                );
                if(confirm == JOptionPane.YES_OPTION){
                    pm.getCompletedProjects().remove(index);
                    dialog.dispose();
                    DataManager.save(pm);
                }
            });

            JPanel bottomBtns = new JPanel(new FlowLayout());
            bottomBtns.add(restartBtn);
            bottomBtns.add(permDeleteBtn);
            dialog.add(bottomBtns, BorderLayout.SOUTH);
            dialog.setVisible(true);
        });
        
        //listener for the complete project button
        completeProjectBtn.addActionListener(e -> {
            int index = projectTabs.getSelectedIndex();
            if(index == -1) return;
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to mark this project as complete?",
                "Complete Project",
                JOptionPane.YES_NO_OPTION
            );
            if(choice == JOptionPane.YES_OPTION){
                pm.completeProject(index);
                projectTabs.removeTabAt(index);
                checkProjectCompletion(pm, completeProjectBtn);
                DataManager.save(pm);
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
        
        // set as visible
        setVisible(true);
    }

    /**
     * Reloads projects tabs 
     * 
     * @param pm ProjectManager
     */
    private void reloadTabs(ProjectManager pm) {
        projectTabs.removeAll();
        for (Project project : pm.getProjects()) {
            projectTabs.addTab(project.getName(), new ProjectPanel(pm, project,
                () -> checkProjectCompletion(pm, completeProjectBtn)));
        }
        projectTabs.revalidate();
        projectTabs.repaint();  
        checkProjectCompletion(pm, completeProjectBtn);
    }
    private void checkProjectCompletion(ProjectManager pm, JButton completeProjectBtn) {
        int index = projectTabs.getSelectedIndex();
        if(index == -1 || index >= pm.getProjects().size()){
            completeProjectBtn.setVisible(false);
            return;
        }
        Project selected = pm.getProjects().get(index);
        if(selected.getTasks().isEmpty()){
            completeProjectBtn.setVisible(false);
            return;
        }
        boolean allComplete = selected.getTasks().stream().allMatch(Task::getCompletion);
        completeProjectBtn.setVisible(allComplete);
    }
}