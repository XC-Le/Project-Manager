package projects.uah.project_manager.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import projects.uah.project_manager.model.Project;
import projects.uah.project_manager.manager.ProjectManager;

/**
 * All code for the Draggable tabbed pane. Allows the user
 * to drag the tabs to change the priority of each project.
 *
 * @author XC-Le
 * @version 1.0
 */

public class DraggableTabbedPane extends JTabbedPane {
    
    private int draggedTabIndex = -1;
    private boolean draggingEnabled = false;
    
    /**
     * Toggles whether dragging tabs is enabled
     */
    public void toggleDraggingEnabled(){
        draggingEnabled = !draggingEnabled;
    }
    
    /**
     * Returns current Boolean status of draggingEnabled
     * 
     * @return draggingEnabled status 
     */
    public boolean isDraggingEnabled(){
        return draggingEnabled;
    }
    
    /**
     * Overrides the MouseEvents to enable the dragging of tabs
     * 
     * @param pm 
     */
    public DraggableTabbedPane (ProjectManager pm){
        MouseAdapter mouseHandler = new MouseAdapter() {
            
            // mousePressed changed to get the index of current tab pressed
            @Override
            public void mousePressed(MouseEvent e){
                draggedTabIndex = indexAtLocation(e.getX(), e.getY());
            }
            
            // mouseReleased changed to reset the draggedTabIndex
            @Override
            public void mouseReleased(MouseEvent e){
                draggedTabIndex = -1;
            }
            
            // mouseDragged changed to update tabs as they are being dragged along the tabbed pane
            @Override
            public void mouseDragged(MouseEvent e){
                // checks if toggled and if a tab is selected
                if(!draggingEnabled || draggedTabIndex<0) return;
                
                // index of current mouse position
                int targetIndex = indexAtLocation(e.getX(), e.getY());
                
                if(targetIndex<0 || targetIndex==draggedTabIndex)return;
                
                // cursor updates
                setCursor(targetIndex >= 0
                    ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                    : Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                
                // information of initial tab
                Component comp = getComponentAt(draggedTabIndex);
                String title = getTitleAt(draggedTabIndex);
                Icon icon = getIconAt(draggedTabIndex);
                String tooltip = getToolTipTextAt(draggedTabIndex);
                boolean enabled = isEnabledAt(draggedTabIndex);
                
                // moves project in project manager to reflect tab order changes
                Project targetProject = pm.getProject(draggedTabIndex);
                pm.moveProject(targetProject, draggedTabIndex, targetIndex);
                
                // updates tab bar
                remove(draggedTabIndex);
                insertTab(title, icon, comp, tooltip, targetIndex);
                setEnabledAt(targetIndex, enabled);
                setSelectedIndex(targetIndex);
                
                draggedTabIndex = targetIndex; 
            }
        };
        
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }
    
}