package Controller;

import GUI.Window;
import GUI.WindowConcrete;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;


import static javax.swing.SwingUtilities.updateComponentTreeUI;

public class WindowController implements  ActionListener {



    Set<Window> windowList;


    public WindowController()
    {
        windowList=new HashSet<>();

    }

    public void removeWindow(Window window)
    {
        windowList.remove(window);
    }

   public void start()
    {
        //starting with one window
        System.out.println(Thread.currentThread());
        Window firstWindow=new WindowConcrete(this);
        windowList.add(firstWindow);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            updateComponentTreeUI(firstWindow.getWindow());
            UIManager.put("OptionPane.background",new ColorUIResource(255,255,255));


        } catch (Exception exception )
        {
            exception.printStackTrace();
        }
    }

    public void addWindow()
    {
        windowList.add(new WindowConcrete(this));
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
