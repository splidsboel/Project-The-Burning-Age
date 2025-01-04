package main;
import javax.swing.JFrame;

public class Main {
    public static JFrame window;
    public static void main(String[] args) {
        //JFRAME
        window = new JFrame();
        window.setTitle("2D Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(false);
 
        //GAMEPANEL
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setVisible(true);

        //SETUP GAME AND START GAME THREAD
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
