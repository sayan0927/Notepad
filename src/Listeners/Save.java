package Listeners;

import GUI.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Save implements ActionListener {

    Window window;
    public Save(Window window)
    {
        this.window = window;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        save();

    }

   public void save()
    {
        if(window.getCurrentFileName()==null)
            new SaveAs(window).saveAs();

        else
        {
            File file = new File(window.getCurrentFileName());

            System.out.println(window.getCurrentFileName());
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                bufferedWriter.write(window.getTextAreaContents());
                bufferedWriter.close();

                window.setTitle(window.getTitle().replaceAll("\\*",""));
                window.setTextChanged(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
