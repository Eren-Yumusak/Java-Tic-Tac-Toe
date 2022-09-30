import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;

import java.io.File;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Write a description of class TicTacToe here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TicTacToe
{
    // instance variables
    private int x;
    private JFrame frame;
    private JLabel filenameLabel;
    private JLabel textLabel;
    private JLabel statusLabel;
    private JButton smallerButton;
    private JButton largerButton;
    private boolean noughtOrCross;
    private boolean canStart;
    private String username;
    private ArrayList<JButton> jButtonGrid;
    private JTextField t;
    private JPanel topText;
    private int count = 0;
    private boolean playedOnce;

    /**
     * Constructor for objects of class TicTacToe
     */
    public TicTacToe()
    {
        // initialise instance variables
        frame = new JFrame("Tic-Tac-Toe");
        Container contentPane = frame.getContentPane();

        contentPane.setLayout(new BorderLayout(6, 6));

        //creating the game board

        jButtonGrid = new ArrayList<JButton>();
        JButton jb1 = new JButton("");
        JButton jb2 = new JButton("");
        JButton jb3 = new JButton("");
        JButton jb4 = new JButton("");
        JButton jb5 = new JButton("");
        JButton jb6 = new JButton("");
        JButton jb7 = new JButton("");
        JButton jb8 = new JButton("");
        JButton jb9 = new JButton("");
        jButtonGrid.add(jb1);
        jButtonGrid.add(jb2);
        jButtonGrid.add(jb3);
        jButtonGrid.add(jb4);
        jButtonGrid.add(jb5);
        jButtonGrid.add(jb6);
        jButtonGrid.add(jb7);
        jButtonGrid.add(jb8);
        jButtonGrid.add(jb9);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3,3)); 
        for(JButton b: jButtonGrid){
            gridPanel.add(b);
            b.addActionListener(e-> changeButton(b));
        }

        //creating the buttons

        JPanel leftButtons = new JPanel();
        leftButtons.add(Box.createVerticalStrut(15));
        leftButtons.setLayout(new GridLayout(0,1));
        JButton start = new JButton("Start");
        leftButtons.add(start);
        leftButtons.add(Box.createVerticalStrut(30));
        JButton replay = new JButton("Re-play");
        leftButtons.add(replay);
        leftButtons.add(Box.createVerticalStrut(15));

        JPanel bottomText = new JPanel();
        //JTextField t;
        t = new JTextField("Please put your name here");
        t.addActionListener(ev-> username());
        bottomText.add(t);

        //JPanel 
        topText = new JPanel();
        textLabel = new JLabel("Welcome! What's your Name?");
        topText.add(textLabel);

        //placing the attributes in the correct areas

        contentPane.add(gridPanel, BorderLayout.CENTER);
        contentPane.add(leftButtons, BorderLayout.EAST);
        contentPane.add(bottomText, BorderLayout.SOUTH);
        contentPane.add(topText, BorderLayout.NORTH);

        final int SHORTCUT_MASK =
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);

        JMenu menu;
        JMenuItem item;

        // create the menu
        menu = new JMenu("Menu");
        menubar.add(menu);

        //giving the buttons a method to call when pressed

        item = new JMenuItem("Reset");
        item.addActionListener(ev -> reset());
        //
        menu.add(item);

        item = new JMenuItem("Quit");
        item.addActionListener(ev -> System.exit(0));
        //
        menu.add(item);
        menu.addSeparator();

        start.addActionListener(ev -> start());
        replay.addActionListener(ev -> replay());

        //making all of it visible

        frame.pack();
        frame.setVisible(true);
    }

    private void changeButton(JButton jb)
    {
        //checks if the user is allowed to play the game before changing the buttons to cross
        if(canStart){
            if(jb.getText().equals("")){
                jb.setText("X");
                randomAIPick();
                count = count + 2;
                playedOnce = true;
                checkWinner();
            }
        }
    }

    private void randomAIPick()
    {
        //The ai selects a random button and places a nought on it when it has found an empty button it places the nought and breaks the loop so it only picks one button
        Random rand = new Random();
        for(int i = 0; i < 9; i++){
            int z = rand.nextInt(9);
            if(jButtonGrid.get(z).getText().equals("")){
                jButtonGrid.get(z).setText("O");
                break;
            }
        }

    }

    private void username()
    {
        // sets the username and updates the top text
        username = t.getText();
        textLabel.setText("Welcome to the game " + username + ",please place a cross, the AI will place a nought after you");
        //topText.add(textLabel);
    }

    private void checkWinner()
    {
        //loops through the entire board's array and adds it to a string, if the string results are winning conditions it updates the toptext to say who has won
        for(int i = 0; i < 8; i++){
            String line = "";

            switch(i){
                case 0:
                    line = jButtonGrid.get(0).getText() + jButtonGrid.get(1).getText() + jButtonGrid.get(2).getText();
                    break;
                case 1:
                    line = jButtonGrid.get(3).getText() + jButtonGrid.get(4).getText() + jButtonGrid.get(5).getText();
                    break;
                case 2:
                    line = jButtonGrid.get(6).getText() + jButtonGrid.get(7).getText() + jButtonGrid.get(8).getText();
                    break;
                case 3:
                    line = jButtonGrid.get(0).getText() + jButtonGrid.get(3).getText() + jButtonGrid.get(6).getText();
                    break;
                case 4:
                    line = jButtonGrid.get(1).getText() + jButtonGrid.get(4).getText() + jButtonGrid.get(7).getText();
                    break;
                case 5:
                    line = jButtonGrid.get(2).getText() + jButtonGrid.get(5).getText() + jButtonGrid.get(8).getText();
                    break;
                case 6:
                    line = jButtonGrid.get(0).getText() + jButtonGrid.get(4).getText() + jButtonGrid.get(8).getText();
                    break;
                case 7:
                    line = jButtonGrid.get(2).getText() + jButtonGrid.get(4).getText() + jButtonGrid.get(6).getText();
                    break;
            }
            if(line.equals("XXX")){
                textLabel.setText(username + " has won");
                canStart = false;
            }
            else if(line.equals("OOO")){
                textLabel.setText("The AI has won");
                canStart = false;
            }
        }
        if(count >= 9){
            textLabel.setText("Draw");
            canStart = false;
        }
    }

    private void reset()
    {
        //resets the entire game to default values
        username = "";
        textLabel.setText("Welcome! What's your Name?");
        t.setText("Please put your name here");
        canStart = false;
        count = 0;
        for(int i =0; i < 9; i++){
            jButtonGrid.get(i).setText("");
        }
    }

    private void start()
    {
        //checks if there is a username inputed to allow the game to start
        if(!canStart){   
            if(username.equals("")){
                canStart = false;
            } else{
                canStart = true;
            }
        }
    }

    private void replay()
    {
        //resets all values to default except username to replay the game with the same user
        if(playedOnce){
            for(int i =0; i < 9; i++){
                jButtonGrid.get(i).setText("");
            }
            canStart = true;
            count = 0;
            textLabel.setText("Welcome to the game " + username + ",please place a cross, the AI will place a nought after you");
        }

    }
}
