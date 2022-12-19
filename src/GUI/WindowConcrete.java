package GUI;

import Controller.WindowController;
import Listeners.*;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WindowConcrete extends Thread implements Window, WindowListener,ActionListener,KeyListener {

    WindowController parentController;
    JMenuBar topMenu;
    JFrame frame;
    JPanel panel;


    JMenu file,edit;

    //menuitems in file menu
    JMenuItem newWindowMenuItem,openMenuitem, saveAsMenuItem, saveMenuItem,exitMenuItem;

    //menuitems in edit menu
    JMenuItem undoMenuItem,redoMenuItem;

    JTextArea textArea;

    ScrollPane scrollPane;

    String currentFileName;

    boolean textChanged=false;

    UndoManager undoManager;

    public WindowConcrete(WindowController windowController)
    {
        this.parentController=windowController;
        this.start();
    }

    @Override
    public void run()
    {
        setup();
    }


    public void setup()
    {
        System.out.println(Thread.currentThread());

        currentFileName=null;
        frame =new JFrame("My Notepad");
        panel =new JPanel();
        panel.setLayout(new BorderLayout());

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(this);




        setupMenu();
        setupTextArea();
        frame.add(panel);
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(500,500));
        frame.pack();
    }


    private void setupMenu()
    {
        topMenu=new JMenuBar();
        file=new JMenu("File");

        newWindowMenuItem =new JMenuItem("New Window     CTRL+N");
        newWindowMenuItem.addActionListener(new NewWindowListener(this));


        openMenuitem =new JMenuItem("Open                   CTRL+O");
        openMenuitem.addActionListener(new OpenFileListener(this));

        saveMenuItem =new JMenuItem("Save                    CTRL+S");
        saveMenuItem.addActionListener(new Save(this));

        saveAsMenuItem =new JMenuItem("Save As               CTRL+SHIFT+S");
        saveAsMenuItem.addActionListener(new SaveAs(this));

        exitMenuItem=new JMenuItem("Exit");
        exitMenuItem.addActionListener(this);

        file.add(newWindowMenuItem);
        file.add(openMenuitem);
        file.add(saveMenuItem);
        file.add(saveAsMenuItem);
        file.add(exitMenuItem);


        edit=new JMenu("Edit");

        undoMenuItem=new JMenuItem("Undo        CTRL+Z");
        undoMenuItem.addActionListener(this);

        redoMenuItem=new JMenuItem("Redo        CTRL+Y");
        redoMenuItem.addActionListener(this);


        edit.add(undoMenuItem);
        edit.add(redoMenuItem);



        topMenu.add(file);
        topMenu.add(edit);
        frame.setJMenuBar(topMenu);

    }



    private void setupTextArea()
    {
        //by default word wrap is true
        textArea=new JTextArea(100,50);
        scrollPane=new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        setLineWrap(true);
        scrollPane.add(textArea);
        panel.add(scrollPane);

        undoManager=new UndoManager();
        textArea.getDocument().addUndoableEditListener(undoManager);

        textArea.addKeyListener(this);
    }


    @Override
    public WindowController getParentController() {
        return parentController;
    }

    @Override
    public boolean setTextAreaContentsFromFile(String filename) {

        StringBuilder buffer=new StringBuilder();
        File file=new File(filename);
        try {
            Scanner scanner=new Scanner(file);
            while (scanner.hasNextLine()) {

                buffer.append(scanner.nextLine()+"\n");
            }
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
           return false;
        }

        textArea.setText("");
        textArea.setText(buffer.toString());
        return true;

    }

    @Override
    public void setCurrentFileName(String filename)
    {
        this.currentFileName=filename;
        setTitle(filename);
    }

    @Override
    public JFrame getWindow() {
        return frame;
    }

    @Override
    public String getTextAreaContents() {
        return textArea.getText();
    }

    @Override
    public void setTitle(String title) {
        frame.setTitle(title);
    }

    @Override
    public String getTitle() {
        return frame.getTitle();
    }

    @Override
    public String getCurrentFileName() {
        return currentFileName;
    }

    @Override
    public void setTextChanged(boolean changed) {
        textChanged=changed;
    }

    @Override
    public boolean getTextChanged() {
        return textChanged;
    }

    @Override
    public void showFileNotFoundError(String wrongFileName) {
        ImageIcon icon = null;
        try {
            icon = new ImageIcon("assets\\filenotfound.jpg");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
       
        JLabel label = new JLabel(icon);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(label);
        JPanel textPanel = new JPanel(new GridLayout(5, 3));
        textPanel.add(new JLabel(wrongFileName));
        textPanel.add(new JLabel("File Not Found"));
        textPanel.add(new JLabel("Check The file name and try again"));
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.add(textPanel);
        panel2.add(panel, BorderLayout.EAST);
        JOptionPane.showMessageDialog(null, panel2,"Open",JOptionPane.DEFAULT_OPTION);
    }


    private void setLineWrap(boolean wrap)
    {
        textArea.setLineWrap(wrap);
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

        if(!textChanged)
        {
            frame.dispose();
            parentController.removeWindow(this);
        }

        if(textChanged)
        {
            int response=new OpenAndNewCommon(this).checkIfSaved();

            System.out.println(response);

            if(response==0)
            {
                new Save(this).save();
                frame.dispose();
                parentController.removeWindow(this);
            }

            if(response==1)
            {
                frame.dispose();
                parentController.removeWindow(this);
            }
        }







    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(undoMenuItem))
            undoEdit();

        if(e.getSource().equals(redoMenuItem))
            redoEdit();

        if(e.getSource().equals(exitMenuItem))
            windowClosing(null);


    }

    private void undoEdit()
    {
        undoManager.undo();
    }

    private void redoEdit()
    {
        undoManager.redo();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        textChanged=true;
        frame.setTitle("*"+frame.getTitle().replaceAll("\\*",""));
    }



    @Override
    public void keyPressed(KeyEvent e) {



        if (e.isControlDown()  && e.getKeyCode() == (int)'Z')
            undoEdit();

        if (e.isControlDown()  && e.getKeyCode() == (int)'Y')
            redoEdit();

        if (e.isControlDown()  && e.getKeyCode() == (int)'N')
            newWindowMenuItem.getActionListeners()[0].actionPerformed(null);

        if (e.isControlDown()  && e.getKeyCode() == (int)'S')
            saveMenuItem.getActionListeners()[0].actionPerformed(null);

        if (e.isControlDown()  && e.getKeyCode() == (int)'O')
            openMenuitem.getActionListeners()[0].actionPerformed(null);



    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
