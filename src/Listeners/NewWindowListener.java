package Listeners;

import GUI.Window;
import GUI.WindowConcrete;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewWindowListener extends OpenAndNewCommon implements ActionListener {



    public NewWindowListener(Window parentWindow) {
       super(parentWindow);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if(parentWindow.getTextChanged())
        {
            if(checkIfSaved()==2)
                return;

            newWindow();
        }
        else
            newWindow();
    }

    void newWindow()
    {
        parentWindow.getParentController().addWindow();
    }
}
