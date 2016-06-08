package com.apmcommunity.compuware;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class AWTAgent {
	private static long applicationInitTimeStamp = System.currentTimeMillis();
	private static boolean applicationIsStartingUp = true;
	private static int visitID = new Double(Math.random() * 10000000).intValue();
	
	public static void initialize() {
//		CompuwareUEM.startup(null, "applicationID", "http://127.0.0.1:80", true, null);
		// Replace AWT Event Queue
        EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        eventQueue.push(new DynaTraceEventQueue()); // <- Now we are in control of all AWT Events
    }

    private static class DynaTraceEventQueue extends EventQueue {
        @Override
        protected void dispatchEvent(AWTEvent theEvent) {
        	// Execute AWT Event
        	long startTime = System.currentTimeMillis();	// <- Before Event was processed
        	super.dispatchEvent(theEvent);					// <- Process AWT Event
        	long finishTime = System.currentTimeMillis();	// <- After Event has been processed
        	
        	// Calculate AWT Event execution time
        	long execTime = finishTime - startTime;

        	// Handle Window Events (In this example only needed to calculate application startup time)
        	if (applicationIsStartingUp) {
        		if (theEvent instanceof WindowEvent) {
        			WindowEvent windowEvent = (WindowEvent) theEvent;
        			switch (windowEvent.getID()) {
        				case WindowEvent.WINDOW_ACTIVATED: // First application window has been activated
        					applicationIsStartingUp = false;
        					long applicationStartupTime = System.currentTimeMillis() - applicationInitTimeStamp;
        					String actionName = "Application Startup (Time from JVM init until first Window is visible to the user)";
        					
        					// Console
               				System.out.println(applicationStartupTime + "[ms] " + actionName);            					

        					// UEM ADK
               				SimpleJavaUEMADK.sendPageActionToDynaTraceUEM(visitID, "Swing Example", actionName, applicationStartupTime);
        					break;
        			}
        		}
        	}
        	
       		// Handle Mouse Events
           	if (theEvent instanceof MouseEvent) {
           		// Get Window Title
           		String windowName = "?";
            		if (theEvent.getSource() instanceof Frame) {
               		windowName = ((Frame) theEvent.getSource()).getTitle();
           		}
           		// Handle MOUSE_RELEASED
           		MouseEvent mouseEvent = (MouseEvent) theEvent;
   				Component mouseEventComponent = SwingUtilities.getDeepestComponentAt((Component) mouseEvent.getSource(), mouseEvent.getX(), mouseEvent.getY());
           		switch (mouseEvent.getID()) {
           			case MouseEvent.MOUSE_RELEASED:
           				String actionName = "Mouse Click on " + getComponentText(mouseEventComponent)+ " in Window '" + windowName + "'";
           				
           				// Console
           				System.out.println(execTime + "[ms] " + actionName);            					
           				
           				// UEM ADK
           				SimpleJavaUEMADK.sendPageActionToDynaTraceUEM(visitID, "Swing Example", actionName, execTime);
                    break;
           		}
           	}
           	
       		// Handle Keyboard Events
           	if (theEvent instanceof KeyEvent) {
           		switch (theEvent.getID()) {
           			case KeyEvent.KEY_PRESSED:
                   		KeyEvent keyEvent = (KeyEvent) theEvent;
                   		Frame frame = (Frame) SwingUtilities.getWindowAncestor(keyEvent.getComponent());
                   		String actionName = "Keyboard input '" + KeyEvent.getKeyText(keyEvent.getKeyCode()) + "' for control '" + keyEvent.getComponent().getName() + "' in window '" + frame.getTitle() + "'";

                   		// Console
           				System.out.println(execTime + "[ms] " + actionName);            					
           				
           				// UEM ADK
           				SimpleJavaUEMADK.sendPageActionToDynaTraceUEM(visitID, "Swing Example", actionName, execTime);
           				break;
           		}
           	}

        }
    }
    
    // =================================================================================================
    // HELPER METHODS (To correctly fetch button labels etc.)
    // =================================================================================================
    private static String getComponentText(Component theComponent) {
    	StringBuffer componentText = new StringBuffer();
    	
    	// Toggle Button
		if (theComponent instanceof JToggleButton) {
			componentText.append("Toggle Button (");
			JToggleButton toggleButton = (JToggleButton) theComponent;
			if ((toggleButton.getText() != null) && (!toggleButton.getText().isEmpty())) {
				componentText.append("'");
				componentText.append(toggleButton.getText().trim());
				componentText.append("'");
				if (toggleButton.isSelected()) {
					componentText.append(", selected");
				} else {
					componentText.append(", deselected");
				}
			}
			else if (toggleButton.getAction() != null) {
				componentText.append(getComponentText(toggleButton.getAction()));
			}
			else if ((toggleButton.getActionCommand() != null) && (!toggleButton.getActionCommand().isEmpty())) {
				componentText.append(toggleButton.getActionCommand());
			}
			componentText.append(")");
		}    	
		
		// Standard Button
		else if (theComponent instanceof JButton) {
			componentText.append("Button (");
			JButton button = (JButton) theComponent;
			if ((button.getText() != null) && (!button.getText().isEmpty())) {
				componentText.append("'");
				componentText.append(button.getText().trim());
				componentText.append("'");
			}
			else if (button.getAction() != null) {
				componentText.append(getComponentText(button.getAction()));
			}
			else if ((button.getActionCommand() != null) && (!button.getActionCommand().isEmpty())) {
				componentText.append(button.getActionCommand());
			}
			else if (button.getIcon() != null) {
				componentText.append(new File(button.getIcon().toString()).getName());
			}
			componentText.append(")");
		}    	
		
		// Tabbed Pane
		else if (theComponent instanceof JTabbedPane) {
			componentText.append("Tab (");
			JTabbedPane tabbedPane = (JTabbedPane) theComponent;
			componentText.append("'");
			componentText.append(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).trim());
			componentText.append("'");
			componentText.append(")");
		}

		// Menu
		else if (theComponent instanceof JMenu) {
			componentText.append("Menu (");
			JMenu menu = (JMenu) theComponent;
			componentText.append("'");
			componentText.append(menu.getText());
			componentText.append("'");
			componentText.append(")");
		}
		
		// Internal Frame
		else if (theComponent instanceof BasicInternalFrameTitlePane) {
			componentText.append("Internal Frame (");
			BasicInternalFrameTitlePane internalFrameTitlePane = (BasicInternalFrameTitlePane) theComponent;
			JInternalFrame internalFrame = (JInternalFrame) internalFrameTitlePane.getParent();
			componentText.append("'");
			componentText.append(internalFrame.getTitle().trim());
			componentText.append("'");
			componentText.append(")");
		}

		// Not implemented components
		else {
			componentText.append("<component analysis for " + theComponent.getClass() + " not implemented in AWTAgent yet>");
		}

		return componentText.toString();
    }
    
    private static String getComponentText(Action theAction) {
    	String actionText = theAction.toString();
    	if (theAction.getValue(Action.SMALL_ICON) != null) {
    		ImageIcon icon = (ImageIcon) theAction.getValue(Action.SMALL_ICON);
    		actionText = new File(icon.getDescription()).getName();
    	}
    	else if (theAction.getValue(Action.LARGE_ICON_KEY) != null) {
    		ImageIcon icon = (ImageIcon) theAction.getValue(Action.LARGE_ICON_KEY);
    		actionText = new File(icon.getDescription()).getName();
    	} else {
    		actionText = theAction.toString();
    	}
    	
    	return actionText;
    }
    
}