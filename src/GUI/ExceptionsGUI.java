package GUI;

import view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExceptionsGUI implements GUI {

    private final BasicGUI basicGUI;
    private final String message;
    private GUI previousGUI;
    private View view;

    public ExceptionsGUI(BasicGUI basicGUI, GUI previousGUI, String message, View view) {
        this.basicGUI = basicGUI;
        this.previousGUI = previousGUI;
        this.message = message;
        this.view = view;
    }



    // Важно ! При запуске ExceptionsGUI не передавать во view.currentGUI на него ссылку, инача не получиться
    // вернуться на предыдущий GUI
    public void go(){
        basicGUI.clear();
        JTextArea textArea = new JTextArea(message);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new OKListener());
        basicGUI.mainPanel.add(BorderLayout.CENTER, textArea);
        basicGUI.buttonsPanel.add(BorderLayout.SOUTH, okButton);
        basicGUI.go();
    }


    private class OKListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            previousGUI.go();
        }
    }
}