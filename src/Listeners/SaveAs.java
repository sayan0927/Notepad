package Listeners;

import GUI.Window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SaveAs implements ActionListener {

    Window window;

    public SaveAs(Window window) {
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        saveAs();
    }

    void saveAs()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(window.getWindow());

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());

            File file=new File(fileToSave.getAbsolutePath());
            try {
                file.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(window.getTextAreaContents());
                writer.close();


                window.setTitle(file.getAbsolutePath());
                window.setCurrentFileName(file.getAbsolutePath());
                window.setTextChanged(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
