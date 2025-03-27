package main;

import javax.swing.JFrame;

public class Main {
    public static JFrame window;
    public static void main(String[] args) {
        System.out.println("Game starting...");

        //JFRAME
        window = new JFrame();
        window.setTitle("The Burning Age");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //GAME WINDOW
        window.setSize(1280, 720);
        window.setLocationRelativeTo(null); // Center the window
        window.setVisible(true);
        
        //GAMEPANEL
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        //SETUP GAME AND START GAME THREAD
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
