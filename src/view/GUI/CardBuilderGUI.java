package view.GUI;
import basicClasses.Dictionary;
import model.facade.ViewFacade;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardBuilderGUI extends BasicGUI{

    private Dictionary dictionary;
    private JTextArea frontSide;
    private JTextArea backSide;

    public CardBuilderGUI(ViewFacade facade, Dictionary dictionary) {
        super(facade);
        this.dictionary = dictionary;
    }

    public void go(){
        frame.setTitle("Card Builder");

        frontSide = new JTextArea(6,20);
        frontSide.setLineWrap(true);
        frontSide.setWrapStyleWord(true);
        frontSide.setFont(bigFont);
        JScrollPane frontScroller = new JScrollPane(frontSide);
        frontScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        frontScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        backSide = new JTextArea(6 ,20);
        backSide.setLineWrap(true);
        backSide.setWrapStyleWord(true);
        backSide.setFont(bigFont);
        JScrollPane backScroller = new JScrollPane(backSide);
        backScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        backScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JLabel frontLabel = new JLabel("Question: ");
        JLabel backLabel = new JLabel("Answer: ");

        mainPanel.add(frontLabel);
        mainPanel.add(frontScroller);
        mainPanel.add(backLabel);
        mainPanel.add(backScroller);

        JButton createCardButton = new JButton("Create Card");
        JButton editCardButton = new JButton("Edit Card");
        JButton deleteCardButton = new JButton("Delete Card");
        JButton cancelButton = new JButton("Cancel");

        createCardButton.addActionListener(new CreateCardListener());
        editCardButton.addActionListener(new EditCardListener());
        deleteCardButton.addActionListener(new DeleteCardListener());
        cancelButton.addActionListener(new CancelListener());

        buttonsPanel.add(createCardButton);
        buttonsPanel.add(editCardButton);
        buttonsPanel.add(deleteCardButton);
        buttonsPanel.add(cancelButton);

        this.frameGo();
    }


    private void clearCard(){
        frontSide.setText("");
        backSide.setText("");
        frontSide.requestFocus();
    }


    private static class CreateCardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }


    private static class EditCardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }


    private static class DeleteCardListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }


    private static class CancelListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}

/*
package view.GUI;
import basicClasses.Dictionary;
import model.Model;
import javax.swing.*;
import java.awt.*;

public class CardBuilderGUI {

    private Model model;
    private Dictionary dictionary;
    private JTextArea frontSide;
    private JTextArea backSide;
    private JFrame frame;
    private JPanel buttonsPanel;


    public void go(){
        // формируем и выводим на экран GUI включая создание и регистрацию
        // слушателей для событий

        frame = new JFrame("Card Builder");
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif", Font.BOLD, 24);


        frontSide = new JTextArea(6,20);
        frontSide.setLineWrap(true);
        frontSide.setWrapStyleWord(true);
        frontSide.setFont(bigFont);
        JScrollPane frontScroller = new JScrollPane(frontSide);
        frontScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        frontScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        backSide = new JTextArea(6 ,20);
        backSide.setLineWrap(true);
        backSide.setWrapStyleWord(true);
        backSide.setFont(bigFont);
        JScrollPane backScroller = new JScrollPane(backSide);
        backScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        backScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JLabel frontLabel = new JLabel("Question: ");
        JLabel backLabel = new JLabel("Answer: ");

        JMenuBar menuBar = new JMenuBar();
        JMenu mainMenu = new JMenu("Main menu");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        mainMenu.add(exitMenuItem);
        menuBar.add(mainMenu);

        mainPanel.add(frontLabel);
        mainPanel.add(frontScroller);
        mainPanel.add(backLabel);
        mainPanel.add(backScroller);

        JButton createCardButton = new JButton("Create Card");
        JButton editCardButton = new JButton("Edit Card");
        JButton deleteCardButton = new JButton("Delete Card");
        JButton cancelButton = new JButton("Cancel");

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.add(createCardButton);
        buttonsPanel.add(editCardButton);
        buttonsPanel.add(deleteCardButton);
        buttonsPanel.add(cancelButton);

        frame.getContentPane().add(BorderLayout.EAST, buttonsPanel);
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(580,500);
        frame.setVisible(true);
    }


    private void clearCard(){
        frontSide.setText("");
        backSide.setText("");
        frontSide.requestFocus();
    }
}
}*/