package Listeners;

import GUI.Window;

import javax.swing.*;

public class OpenAndNewCommon {

    Window parentWindow;



    public OpenAndNewCommon(Window parentWindow) {
        this.parentWindow = parentWindow;
    }

   public int checkIfSaved() {
        String[] options = new String[]{"Save", "Dont Save", "Cancel"};
        String fileName = parentWindow.getCurrentFileName() == null ? "Untitled" : parentWindow.getCurrentFileName();
        int response = JOptionPane.showOptionDialog(parentWindow.getWindow(), "Do you want to save changes to " + fileName + " ?", "My Notepad",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        return response;
    }

}
