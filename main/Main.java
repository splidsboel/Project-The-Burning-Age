package main;


import java.awt.*;
import javax.swing.*;

/**
 * Entry point of the game.
 * Handles display mode and GamePanel initialization.
 */
public class Main
 {

    public static JFrame window;

    public static void main(String[] args) {
        System.out.println("Game starting...");

        // Create window
        window = new JFrame("The Burning Age");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setUndecorated(true);

        // Create and attach game panel
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // Setup display mode
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        // Fullscreen fallback safety
        try {
            gd.setFullScreenWindow(window);
        } catch (Exception e) {
            System.err.println("Fullscreen not supported. Falling back to windowed mode.");
            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        // Enable hardware acceleration + double buffering
        System.setProperty("sun.java2d.opengl", "true");
        gamePanel.setDoubleBuffered(true);

        // Show window
        window.setVisible(true);

        // Setup game and start main loop
        gamePanel.startGameThread();
    }
}
