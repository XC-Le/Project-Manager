package projects.uah.project_manager.display;
 
import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;
import projects.uah.project_manager.model.Project;
import projects.uah.project_manager.model.Task;
import projects.uah.project_manager.manager.*;
 
/**
 * A Swing panel that displays and manages the list of projects.
 * Allows users to view, create, and interact with projects through the graphical interface.
 *
 * @author XC-Le and Duncan Williams
 * @version 1.2
 */
public class ProjectPanel extends JPanel {
    
    JPanel taskListPanel = new JPanel();
    
    /**
     * Constructs a new ProjectPanel and initializes its UI components.
     * @param project
     */
    public ProjectPanel(ProjectManager pm, Project project) {
        
        setLayout(new BorderLayout());    
        setVisible(true);
        
        // New button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
 
        // add task button
        JButton addTaskBtn = new JButton("New Task");
        addTaskBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(addTaskBtn);
 
        buttonPanel.add(Box.createVerticalStrut(5));
 
        // delete task button
        JButton delTaskBtn = new JButton("Delete Task");
        delTaskBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(delTaskBtn);
        
        buttonPanel.add(Box.createVerticalStrut(5));
        
        // project details button
        JButton detailsBtn = new JButton("Details");
        detailsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(detailsBtn);
        
        buttonPanel.add(Box.createVerticalGlue());

        JButton checkDeletedTasksBtn = new JButton("Deleted Tasks");
        checkDeletedTasksBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(checkDeletedTasksBtn);
        

        add(buttonPanel, BorderLayout.EAST);
        
        // Creates the panel for the tasks
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.X_AXIS));
        
        // M
        JScrollPane scrollPane = new JScrollPane(taskListPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        add(scrollPane, BorderLayout.CENTER);
 
        // Listeners 
        
        // listener for add button
        addTaskBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Task name:");
            
            boolean exists = project.getTasks().stream().anyMatch(p -> p.getName().equalsIgnoreCase(name));
 
            if(exists){
                JOptionPane.showMessageDialog(this, "A task with that name already exists in this project.");
            } else if(name != null && !name.isBlank()) {
                project.addTask(new Task(name, "", LocalDate.now(), 1, false));
                updatePriorities(project);
                DataManager.save(pm);
                reloadTasks(pm, project);
            }
        });  
        
        // listener for delete button
        delTaskBtn.addActionListener(e -> {
            String[] task_names = project.getTasks().stream().map(Task::getName).toArray(String[]::new);
            if(project.getTasks().isEmpty()){
                JOptionPane.showMessageDialog(this, "No tasks to delete.");
                return;
            }
            JList<String> task_list = new JList<>(task_names);
            task_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            task_list.setSelectedIndex(0);
            int select_task = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(task_list),
                "Select Project to Delete",
                JOptionPane.OK_CANCEL_OPTION
            );
            if(select_task == JOptionPane.OK_OPTION){
                int double_check = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
                );
                if(double_check == JOptionPane.YES_OPTION){
                    int  index = task_list.getSelectedIndex();
                    project.removeTask(index);
                    reloadTasks(pm, project);
                    DataManager.save(pm); 
                }
            }
        });
        // listener for details button
        detailsBtn.addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.setTitle("Project Details - " + project.getName());
            dialog.setSize(400, 400);
            dialog.setLocationRelativeTo(this);
            dialog.setLayout(new BorderLayout());

            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
            detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel descLabel = new JLabel("Description: " + project.getDescription());
            descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            detailsPanel.add(descLabel);
            detailsPanel.add(Box.createVerticalStrut(5));

            JLabel createdLabel = new JLabel("Created: " + (project.getCreationDate() != null ? project.getCreationDate() : "Unknown"));
            createdLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            detailsPanel.add(createdLabel);
            detailsPanel.add(Box.createVerticalStrut(5));

            JLabel dueDateLabel = new JLabel("Due Date: " + (project.getDueDate() != null ? project.getDueDate() : "Not set"));
            dueDateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            detailsPanel.add(dueDateLabel);
            detailsPanel.add(Box.createVerticalStrut(5));

            JLabel taskCountLabel = new JLabel("Tasks: " + project.getTasks().size());
            taskCountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            detailsPanel.add(taskCountLabel);
            detailsPanel.add(Box.createVerticalStrut(10));

            if(!project.getTasks().isEmpty()){
                JLabel taskDetailsLabel = new JLabel("Task Details:");
                taskDetailsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                detailsPanel.add(taskDetailsLabel);
                detailsPanel.add(Box.createVerticalStrut(5));

                String[] taskNames = project.getTasks().stream()
                    .map(Task::getName)
                    .toArray(String[]::new);
                JComboBox<String> taskDropdown = new JComboBox<>(taskNames);
                taskDropdown.setMaximumSize(new Dimension(200, 25));
                taskDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);

                JPanel taskInfoPanel = new JPanel();
                taskInfoPanel.setLayout(new BoxLayout(taskInfoPanel, BoxLayout.Y_AXIS));
                taskInfoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                Runnable updateTaskInfo = () -> {
                    int index = taskDropdown.getSelectedIndex();
                    Task selected = project.getTasks().get(index);
                    taskInfoPanel.removeAll();

                    JLabel tdesc = new JLabel("Description: " + (selected.getDescription().isBlank() ? "None" : selected.getDescription()));
                    tdesc.setAlignmentX(Component.LEFT_ALIGNMENT);
                    taskInfoPanel.add(tdesc);

                    JLabel tdue = new JLabel("Due Date: " + (selected.getDueDate() != null ? selected.getDueDate() : "Not set"));
                    tdue.setAlignmentX(Component.LEFT_ALIGNMENT);
                    taskInfoPanel.add(tdue);

                    JLabel tpriority = new JLabel("Priority: " + selected.getPriority());
                    tpriority.setAlignmentX(Component.LEFT_ALIGNMENT);
                    taskInfoPanel.add(tpriority);

                    JLabel tcomplete = new JLabel("Complete: " + (selected.getCompletion() ? "Yes" : "No"));
                    tcomplete.setAlignmentX(Component.LEFT_ALIGNMENT);
                    taskInfoPanel.add(tcomplete);

                    JLabel tcreated = new JLabel("Created: " + (selected.getCreationDate() != null ? selected.getCreationDate() : "Unknown"));
                    tcreated.setAlignmentX(Component.LEFT_ALIGNMENT);
                    taskInfoPanel.add(tcreated);

                    taskInfoPanel.revalidate();
                    taskInfoPanel.repaint();
                };

                taskDropdown.addActionListener(de -> updateTaskInfo.run());
                updateTaskInfo.run();

                detailsPanel.add(taskDropdown);
                detailsPanel.add(Box.createVerticalStrut(5));
                detailsPanel.add(taskInfoPanel);
            } else {
                JLabel noTasksLabel = new JLabel("No tasks yet.");
                noTasksLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                detailsPanel.add(noTasksLabel);
            }

            dialog.add(new JScrollPane(detailsPanel), BorderLayout.CENTER);
            dialog.setVisible(true);
        });
        // listener for check deleted tasks button
        checkDeletedTasksBtn.addActionListener(e -> {
            if(project.getDeletedTasks().isEmpty()){
                JOptionPane.showMessageDialog(this, "No deleted tasks.");
                return;
            }
            String[] deletedTaskNames = project.getDeletedTasks().stream()
                .map(t -> t.getName() + "  |  Created: " + (t.getCreationDate() != null ? t.getCreationDate() : "Unknown"))
                .toArray(String[]::new);
            JDialog dialog = new JDialog();
            dialog.setTitle("Deleted Tasks");
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(this);
            dialog.setLayout(new BorderLayout());

            JList<String> deletedList = new JList<>(deletedTaskNames);
            dialog.add(new JScrollPane(deletedList), BorderLayout.CENTER);

            JButton restoreBtn = new JButton("Restore");
            restoreBtn.addActionListener(re -> {
                int index = deletedList.getSelectedIndex();
                if(index == -1){
                    JOptionPane.showMessageDialog(dialog, "Please select a task to restore.");
                    return;
                }
                Task restored = project.getDeletedTasks().get(index);
                project.getDeletedTasks().remove(index);
                project.addTask(restored);
                reloadTasks(pm, project);
                dialog.dispose();
                DataManager.save(pm);
            });

            JButton permDeleteBtn = new JButton("Delete Permanently");
            permDeleteBtn.addActionListener(pd -> {
                int index = deletedList.getSelectedIndex();
                if(index == -1){
                    JOptionPane.showMessageDialog(dialog, "Please select a task to delete.");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(
                    dialog,
                    "Are you sure? This cannot be undone.",
                    "Confirm Permanent Delete",
                    JOptionPane.YES_NO_OPTION
                );
                if(confirm == JOptionPane.YES_OPTION){
                    project.getDeletedTasks().remove(index);
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
        reloadTasks(pm, project);
    }
    
    private void reloadTasks(ProjectManager pm, Project project) {
        // removes all tasks to add from scratch
        updatePriorities(project);
        taskListPanel.removeAll();
        for(Task task : project.getTasks()){
            taskListPanel.add(new TaskPanel(pm, project, task));
        }
        taskListPanel.revalidate();
        taskListPanel.repaint();
    }
    
    // updates the priorities of each task(left to right)
    private void updatePriorities(Project project) {
        for(int i = 0; i < project.getTasks().size(); i++){
            project.getTasks().get(i).setPriority(i + 1);
        }
    }
}