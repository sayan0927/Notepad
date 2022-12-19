package Listeners;

import GUI.Window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class OpenFileListener extends OpenAndNewCommon implements ActionListener {




    public OpenFileListener(Window parentWindow) {
        super(parentWindow);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

       if(parentWindow.getTextChanged())
       {
           if(checkIfSaved()==2)
               return;

           open();
       }
       else
           open();

    }




    void open()
    {
        JFileChooser fileChooser = new JFileChooser();
        int i = fileChooser.showOpenDialog(parentWindow.getWindow());
        if (i == JFileChooser.APPROVE_OPTION)
        {


            File file= fileChooser.getSelectedFile();
                String filepath = file.getAbsolutePath();
              if(!parentWindow.setTextAreaContentsFromFile(filepath))
              {
                  parentWindow.showFileNotFoundError(file.getName());
                  return;
              }
                parentWindow.setCurrentFileName(file.getAbsolutePath());
                parentWindow.setTextChanged(false);


        }
    }
}
