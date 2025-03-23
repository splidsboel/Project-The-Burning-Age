package main;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class Main {
    public static JFrame window;
    public static void main(String[] args) {
        //JFRAME
        window = new JFrame();
        window.setTitle("The Burning Age");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(true);
 
        //GAMEPANEL
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        //GAME WINDOW
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(window);
        gamePanel.setDoubleBuffered(true);
        window.setVisible(true);

        //SETUP GAME AND START GAME THREAD
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
