package GUI;

import Controller.WindowController;

import javax.swing.*;


public interface Window {

    WindowController getParentController();

   boolean setTextAreaContentsFromFile(String fileName) ;

    String getTextAreaContents();

   void setCurrentFileName(String filename);

    String getCurrentFileName();
    JFrame getWindow();

    void setTitle(String title);

    String getTitle();

    void setTextChanged(boolean changed);
    boolean getTextChanged();

    void showFileNotFoundError(String wrongFileName);
}
