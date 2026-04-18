package projects.uah.project_manager.display;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import javax.swing.border.CompoundBorder;
import projects.uah.project_manager.manager.*;
import projects.uah.project_manager.model.*;

public class TaskPanel extends JPanel {

    JPanel subtaskListPanel = new JPanel();
    
    /**
     * Constructs a new TaskPanel and initializes its UI components.
     * @param pm      project manager to save data
     * @param project project that the task is part of
     * @param task to display and keep data 
     */
    public TaskPanel(ProjectManager pm, Project project, Task task) {
        setPreferredSize(new Dimension(270, 300));
        setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(8,8,8,8),
            new CompoundBorder(BorderFactory.createLineBorder(Color.GRAY),BorderFactory.createEmptyBorder(8, 8, 8, 8))));
        setLayout(new BorderLayout());

        // Header: task name and creation date
        JPanel infoPanel = new JPanel(new BorderLayout());
        JTextArea title = new JTextArea(task.getName()); 
        title.setEditable(false);
        title.setFocusable(false); 
        title.setBorder(null); 
        title.setBackground(null);
        title.setLineWrap(true);
        title.setWrapStyleWord(true);
        infoPanel.add(title, BorderLayout.WEST);
        JLabel creationDate = new JLabel("Created: " + task.getCreationDate());
        infoPanel.add(creationDate, BorderLayout.EAST);
        add(infoPanel, BorderLayout.NORTH);
        
        // New button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 0, 4));
        JButton addSubtaskBtn = new JButton("Add Subtask");
        buttonPanel.add(addSubtaskBtn);
        JButton delSubtaskBtn = new JButton("Delete Subtask");
        buttonPanel.add(delSubtaskBtn);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Subtask list
        subtaskListPanel.setLayout(new BoxLayout(subtaskListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(subtaskListPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
        
        // listener for add button
        addSubtaskBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Subtask name:");
            
            boolean exists = project.getTasks().stream().anyMatch(p -> p.getName().equalsIgnoreCase(name));
 
            if(exists){
                JOptionPane.showMessageDialog(this, "A subtask with that name already exists in this project.");
            } else if(name != null && !name.isBlank()) {
                task.addSubtask(new Subtask(name, LocalDate.now(), false));
                DataManager.save(pm);
                reloadSubtasks(pm, task);
                checkForCompletion(pm, task);
            }
        });  
        
        // listener for delete button
        delSubtaskBtn.addActionListener(e -> {
            String[] subtask_names = task.getSubtasks().stream().map(Subtask::getName).toArray(String[]::new);
            if(task.getSubtasks().isEmpty()){
                JOptionPane.showMessageDialog(this, "No tasks to delete.");
                return;
            }
            JList<String> subtask_list = new JList<>(subtask_names);
            subtask_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            subtask_list.setSelectedIndex(0);
            int select_project = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(subtask_list),
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
                    int  index = subtask_list.getSelectedIndex();
                    task.removeSubtask(index);
                    reloadSubtasks(pm, task);
                    checkForCompletion(pm, task);
                    DataManager.save(pm); 
                }
            }
        });
        
        reloadSubtasks(pm, task);
        
        checkForCompletion(pm, task);
        
        
    }
    
    private void reloadSubtasks(ProjectManager pm, Task task) {
        subtaskListPanel.removeAll();
        for (Subtask subtask : task.getSubtasks()) {
            JCheckBox checkBox = new JCheckBox(subtask.getName(), subtask.getCompletion());
            checkBox.addActionListener(e -> {
                subtask.setCompletion(checkBox.isSelected());
                DataManager.save(pm);
                checkForCompletion(pm, task);
            });
            subtaskListPanel.add(checkBox);
        }
        subtaskListPanel.revalidate();
        subtaskListPanel.repaint();
    }
    
    private void checkForCompletion(ProjectManager pm, Task task) {
        boolean isready = true;
        for (Subtask subtask2 : task.getSubtasks()) {
            isready = isready && subtask2.getCompletion();
            //System.out.println("Checked? " + isready);
        }
        if (!task.getSubtasks().isEmpty()) {
            task.setCompletion(isready);
        }
        //System.out.println("All checked for " + task.getName() + "? " + task.getCompletion());
        if (task.getCompletion()) {
            setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(8,8,8,8),
                new CompoundBorder(BorderFactory.createLineBorder(Color.GREEN),BorderFactory.createEmptyBorder(8, 8, 8, 8))));
        } else {
            setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(8,8,8,8),
                new CompoundBorder(BorderFactory.createLineBorder(Color.GRAY),BorderFactory.createEmptyBorder(8, 8, 8, 8))));
        }
    }
}