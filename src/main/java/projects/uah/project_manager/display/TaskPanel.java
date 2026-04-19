package projects.uah.project_manager.display;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import javax.swing.border.CompoundBorder;
import projects.uah.project_manager.manager.*;
import projects.uah.project_manager.model.*;

public class TaskPanel extends JPanel {

    JPanel subtaskListPanel = new JPanel();
    Runnable onCompletionChanged;
    
    public TaskPanel(ProjectManager pm, Project project, Task task, Runnable onCompletionChanged) {
        this.onCompletionChanged = onCompletionChanged;
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
        
        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 4));
        JButton addSubtaskBtn = new JButton("Add Subtask");
        buttonPanel.add(addSubtaskBtn);
        JButton delSubtaskBtn = new JButton("Delete Subtask");
        buttonPanel.add(delSubtaskBtn);
        JButton deletedSubtasksBtn = new JButton("Deleted Subtasks");
        buttonPanel.add(deletedSubtasksBtn);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Subtask list
        subtaskListPanel.setLayout(new BoxLayout(subtaskListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(subtaskListPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
        
        // listener for add subtask button
        addSubtaskBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Subtask name:");
            boolean exists = task.getSubtasks().stream().anyMatch(s -> s.getName().equalsIgnoreCase(name));
            if(exists){
                JOptionPane.showMessageDialog(this, "A subtask with that name already exists.");
            } else if(name != null && !name.isBlank()) {
                task.addSubtask(new Subtask(name, LocalDate.now(), false));
                DataManager.save(pm);
                reloadSubtasks(pm, project, task);
                checkForCompletion(pm, project, task);
            }
        });  
        
        // listener for delete subtask button
        delSubtaskBtn.addActionListener(e -> {
            if(task.getSubtasks().isEmpty()){
                JOptionPane.showMessageDialog(this, "No subtasks to delete.");
                return;
            }
            String[] subtask_names = task.getSubtasks().stream().map(Subtask::getName).toArray(String[]::new);
            JList<String> subtask_list = new JList<>(subtask_names);
            subtask_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            subtask_list.setSelectedIndex(0);
            int select = JOptionPane.showConfirmDialog(
                this, new JScrollPane(subtask_list), "Select Subtask to Delete", JOptionPane.OK_CANCEL_OPTION);
            if(select == JOptionPane.OK_OPTION){
                int double_check = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
                if(double_check == JOptionPane.YES_OPTION){
                    task.removeSubtask(subtask_list.getSelectedIndex());
                    reloadSubtasks(pm, project, task);
                    checkForCompletion(pm, project, task);
                    DataManager.save(pm); 
                }
            }
        });

        // listener for deleted subtasks button
        deletedSubtasksBtn.addActionListener(e -> {
            if(task.getDeletedSubtasks().isEmpty()){
                JOptionPane.showMessageDialog(this, "No deleted subtasks.");
                return;
            }
            String[] deletedNames = task.getDeletedSubtasks().stream()
                .map(s -> s.getName() + "  |  Created: " + (s.getCreationDate() != null ? s.getCreationDate() : "Unknown"))
                .toArray(String[]::new);
            JDialog dialog = new JDialog();
            dialog.setTitle("Deleted Subtasks");
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(this);
            dialog.setLayout(new BorderLayout());

            JList<String> deletedList = new JList<>(deletedNames);
            dialog.add(new JScrollPane(deletedList), BorderLayout.CENTER);

            JButton restoreBtn = new JButton("Restore");
            restoreBtn.addActionListener(re -> {
                int index = deletedList.getSelectedIndex();
                if(index == -1){
                    JOptionPane.showMessageDialog(dialog, "Please select a subtask to restore.");
                    return;
                }
                Subtask restored = task.getDeletedSubtasks().get(index);
                task.getDeletedSubtasks().remove(index);
                task.addSubtask(restored);
                reloadSubtasks(pm, project, task);
                checkForCompletion(pm, project, task);
                dialog.dispose();
                DataManager.save(pm);
            });

            JButton permDeleteBtn = new JButton("Delete Permanently");
            permDeleteBtn.addActionListener(pd -> {
                int index = deletedList.getSelectedIndex();
                if(index == -1){
                    JOptionPane.showMessageDialog(dialog, "Please select a subtask to delete.");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(
                    dialog, "Are you sure? This cannot be undone.", "Confirm Permanent Delete", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION){
                    task.getDeletedSubtasks().remove(index);
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

        reloadSubtasks(pm, project, task);
        checkForCompletion(pm, project, task);
    }
    
    private void reloadSubtasks(ProjectManager pm, Project project, Task task) {
        subtaskListPanel.removeAll();
        for (Subtask subtask : task.getSubtasks()) {
            JCheckBox checkBox = new JCheckBox(subtask.getName(), subtask.getCompletion());
            checkBox.addActionListener(e -> {
                subtask.setCompletion(checkBox.isSelected());
                DataManager.save(pm);
                checkForCompletion(pm, project, task);
            });
            subtaskListPanel.add(checkBox);
        }
        subtaskListPanel.revalidate();
        subtaskListPanel.repaint();
    }
    
    private void checkForCompletion(ProjectManager pm, Project project, Task task) {
        boolean isready = true;
        for (Subtask subtask2 : task.getSubtasks()) {
            isready = isready && subtask2.getCompletion();
        }
        if (!task.getSubtasks().isEmpty()) {
            task.setCompletion(isready);
        }
        if (task.getCompletion()) {
            setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(8,8,8,8),
                new CompoundBorder(BorderFactory.createLineBorder(Color.GREEN),BorderFactory.createEmptyBorder(8, 8, 8, 8))));
        } else {
            setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(8,8,8,8),
                new CompoundBorder(BorderFactory.createLineBorder(Color.GRAY),BorderFactory.createEmptyBorder(8, 8, 8, 8))));
        }
        if(!project.getTasks().isEmpty()){
            boolean allComplete = project.getTasks().stream().allMatch(Task::getCompletion);
            if(allComplete){
                int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Congratulations! All tasks in \"" + project.getName() + "\" are complete!\nWould you like to move this project to completed projects?",
                    "Project Complete!",
                    JOptionPane.YES_NO_OPTION
                );
                if(choice == JOptionPane.YES_OPTION){
                    int index = pm.getProjects().indexOf(project);
                    pm.completeProject(index);
                    DataManager.save(pm);
                    JTabbedPane tabs = (JTabbedPane) SwingUtilities.getAncestorOfClass(JTabbedPane.class, this);
                    if(tabs != null){
                        tabs.remove(tabs.getSelectedIndex());
                    }
                }
            }
            if(onCompletionChanged != null) onCompletionChanged.run();
        }
    }
}