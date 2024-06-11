/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rhythmgame_likevin;

//imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_UP;
import static java.awt.GraphicsDevice.WindowTranslucency.*;

import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class MainGame {
        
        //Home Screen init
        public static final JFrame scrMain = new JFrame();
    
        //note declaration and initialization
        public static final JPanel note = new JPanel();
        public static int noteX = 400;
        public static int noteY = -60;
        public static int noteSpeed = 4;
        
        //circling clockwise:
        //1 > top to bottom
        //2 > right to left
        //3 > bottom to top
        //4 > left to right
        public static int[] noteState = {1, 1, 1, 1, 1, 2, 3, 4, 2, 3, 4, 4, 1, 2, 2, 3, 3, 4, 2, 2, 2, 3, 2, 2, 1, 2, 4, 2, 4, 2, 4 , 1, 2, 3, 2, 1, 2, 3, 4, 1 , 1, 1, 1, 3, 4};
        public static int current = 0;
        
        public static final JLabel lblScore = new JLabel();
        public static int score = 0;
        
        public static final JPanel key = new JPanel();
        
        //player controllers
        public static final JPanel arrLeft = new JPanel();
        public static final JPanel arrRight = new JPanel();
        public static final JPanel arrDown = new JPanel();
        public static final JPanel arrUp = new JPanel();
        
        //screens
        public static final JPanel scrGame = new JPanel();
        public static final JPanel scrHome = new JPanel();
        public static final JButton btnPlay = new JButton("Play");
        public static final JLabel lblTitle = new JLabel("Title Text");
        
//        public static InputMap inputMap = new InputMap();
        
        public static Timer timerMain = new Timer(1, new MyActionListener());
        
    public static void home() {//would it be preferable to put this in its own java class? might cause issues as I would have to spawn a new JFrame per class right? or can I parse objects across classes?
            
        //sets new JFrame / "main screen"
        scrMain.getContentPane().setLayout(null);
        scrMain.setSize(800,640);
        scrMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scrMain.setFocusable(true);
        
        scrMain.add(scrHome);
        //set layout to null prior to setting bounds, or else default layout will be used
        scrHome.setLayout(null);
        scrHome.setSize(800, 640);
        scrHome.setBackground(Color.PINK);
        scrHome.setVisible(true);
        scrHome.setFocusable(true);
        
        scrHome.add(btnPlay);
        btnPlay.setSize(new Dimension(100,100));
        //> sets button border to be transparent - a label that is clickable
        btnPlay.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        btnPlay.setBorderPainted(false);
        btnPlay.setContentAreaFilled(false);
        btnPlay.setFocusPainted(false);  
        //< could potentially use this as the inputs instead of using keypress if keypress fails to work
        btnPlay.setFont(new Font("Jokerman", Font.BOLD, 40));
        btnPlay.setLocation(350, 200);
//        btnPlay.setPreferredSize(new Dimension(50, 50));
//        btnPlay.setBackground(Color.GREEN);
        btnPlay.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                scrHome.setVisible(false);
                
                javax.swing.SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run(){
                        game();

                    }
                });
                
            }
            
            
        });

        //If I want to make it so that the keyListener works within this method, I would have to create a new timer.

//        timerMain.start();
//
//        scrHome.addKeyListener(new KeyAdapter() {
//            public void keyPressed(KeyEvent ke) {
//                if (ke.getKeyCode() == KeyEvent.VK_E) {
//                    createAndShowGUI();
//                }       
//            }
//            
//        });
        
        scrHome.add(lblTitle);
        lblTitle.setSize(new Dimension(400, 200));
//        lblTitle.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));  
        lblTitle.setFont(new Font("Jokerman", Font.BOLD, 50));
        lblTitle.setLocation(275, 100);
        

        
        scrMain.setVisible(true);
        
        
        
        
    }

    public static void game(){
//        timerMain.stop();
        scrMain.add(scrGame);
        
        
        scrGame.setLayout(null);
        scrGame.setSize(800, 640);
        scrGame.setBackground(Color.CYAN);
        scrGame.setVisible(true);
        scrGame.setFocusable(true);

        scrGame.add(lblScore);
        lblScore.setText(Integer.toString(score));
        lblScore.setBounds(740, 20, 30, 30);
        lblScore.setFont(new Font("Jokerman", Font.BOLD, 20));
        
        //adds "rhythm note" JPanel from constructor
        scrGame.add(note);
        note.setBounds(noteX,noteY,20,20);
        note.setBackground(Color.RED);

        scrGame.add(arrLeft);
        scrGame.add(arrUp);
        scrGame.add(arrRight);
        scrGame.add(arrDown);
        
        

        //Starts a timer, using an action listener
        Timer timerNote = new Timer(1,new MyActionListener());
        //change this delay value to change the speed that the note slides down the screen
        timerNote.start();
        
        //inside this class because the timer is in here -> this will run every 6ms detecting a key press! 
                scrMain.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
               int key = ke.getKeyCode();   
                if (key == KeyEvent.VK_SPACE) {
                    
                    System.out.println("The SpaceBar Key Was Pressed.");
                    if (note.getY() >= 500 && note.getY() < 640 || note.getY() > 0 && note.getY() <= 140 || note.getX() <= 140 && note.getX() > 0 || note.getX() >= 500 && note.getX() < 800) {
                    score++;
                    current++;
                    
                        
//                    yDown = -60;
                    timerNote.restart();
                    } else {
                    score--;
                    current++;
                //make the note die, next index in array ... 
                    }
                    switch (current) {
                        case 4 -> noteSpeed++;
                        case 10 -> noteSpeed++;
                        case 16 -> noteSpeed++;
                        case 20 -> noteSpeed++;
                        case 25 -> noteSpeed++;
                        case 27 -> noteSpeed++;
                        case 30 -> noteSpeed++;
                        //etc and etc depending on how fast I want it to go to
                    }
                    
                    switch (noteState[current]) {
                            case 1 -> {
                                noteX = 400;
                                noteY = -60;
                            } case 2 -> {
                                noteX = 860;
                                noteY = 220;
                            } case 3 -> {
                                noteX = 400;
                                noteY = 700;
                            } case 4 -> {
                                noteX = -60;
                                noteY = 220;
                            }
                        }
                        note.setLocation(noteX, noteY);
               }
               

//    switch(key){
//       case KeyEvent.VK_RIGHT: 
//           System.out.println("The right arrow key is pressed");
//       // Other operations
//       break;
//       case KeyEvent.VK_LEFT: 
//            System.out.println("The left arrow key is pressed");
//            if (note.getY() >= 500 && note.getY() < 640) {
//            score++;
////            noteOnOrOff[current] = noteOnOrOff[current++];
//            yDown = -60;
//            timerNote1.restart();
//            } else {
//                score--;
//                //make the note die, next index in array ... 
//            }
//            
//       // Other operations
//       break;
//       case KeyEvent.VK_UP: 
//           System.out.println("The up arrow key is pressed");
//       // Other operations
//       break;
//       case KeyEvent.VK_DOWN: 
//           System.out.println("The down arrow key is pressed");
//       // Other operations
//       break;
//    }             
            }
        
        });
        scrMain.setVisible(true);
    }

    
    //new class that checks for action update, on update, change the y value of the note by "noteSpeed" each ~1ms delay
    //not actually 1ms as threads can "lag"
    public static class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            switch (noteState[current]) {
                case 1 -> noteY += noteSpeed;
                case 2 -> noteX -= noteSpeed;
                case 3 -> noteY -= noteSpeed;
                case 4 -> noteX += noteSpeed;
                
            }
            note.setLocation(noteX, noteY);
            lblScore.setText(Integer.toString(score));
        }

    }

//    public static void main() {
//        javax.swing.SwingUtilities.invokeLater(new Runnable(){
//            @Override
//            public void run(){
//                createAndShowGUI();
//
//            }
//        });


//    }
}
