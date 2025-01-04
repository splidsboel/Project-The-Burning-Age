package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import input.KeyHandler;
import input.MouseHandler;
import tile.TileManager;
import tools.UtilityTool;

public class GamePanel extends JPanel implements Runnable {
    //THREAD
    Thread gameThread;

    //GAMESTATES
    public Playing playing;
    public Menu menu;


    //SYSTEMS
    public KeyHandler keyH; 
    public MouseHandler mouseH;
    public TileManager tileM;
    public UtilityTool uTool;


    //SCREEN SETTINGS
    public static final int originalTileSize = 32;
    public static final double scale = 2.5;
    public final int tileSize = (int)(originalTileSize * scale); // 80x80 tile
    public final int maxScreenCol = 24;
    public final double maxScreenRow = 13.5; // 16:9
    public final int screenWidth = tileSize * maxScreenCol; // 1920 pixels
    public final int screenHeight = (int)(tileSize * maxScreenRow); // 1080 pixels
    public final int localScreenWidth = Main.window.getWidth();
    public final int localScreenHeight = Main.window.getHeight();

    protected final int FPS = 90;
    protected final int UPS = 120;
    public int frames;
    public int updates;
    BufferedImage screen;
    Graphics2D g2;

    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    
    public GamePanel() {
        initializeClasses();

        //GAME WINDOW
        this.setPreferredSize(new Dimension(localScreenWidth,localScreenWidth));
        this.setDoubleBuffered(true);
    
        //MOUSE AND KEY LISTENER
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    } 

    public void initializeClasses() {
        //GAMESTATE
        playing = new Playing(this);
        menu = new Menu(this);

        //SYSTEMS
        keyH = new KeyHandler(this);
        mouseH = new MouseHandler(this);
        tileM = new TileManager(this);
        uTool = new UtilityTool();
    }

    public void update() {
        switch (Gamestate.state) {
            case PLAYING: 
                playing.update();
                break;
            case MENU:
                menu.update();
                break;
            case OPTIONS:
            case QUIT: 
                System.exit(0);
            default:       
        }
    }
    public void render() {
        switch (Gamestate.state) {
            case PLAYING: 
                playing.draw(g2);
                break;
            case MENU:
                menu.draw(g2);
                break;
            default:       
        }
    
        //DRAW TO SCREEN
        Graphics g = getGraphics();
        g.drawImage(screen, 0, 0, screenWidth, screenHeight,null);
        g.dispose();
    }

    
    //SETUP GAME AND START GAME THREAD METHODS
    public void setupGame() {
        // Center the camera on the player
        getPlaying().getPlayer().cameraX = getPlaying().getPlayer().worldX - screenWidth / 2 + tileSize / 2;
        getPlaying().getPlayer().cameraY = getPlaying().getPlayer().worldY - screenHeight / 2 + tileSize / 2;
        getPlaying().getPlayer().screenX = getPlaying().getPlayer().worldX - getPlaying().getPlayer().cameraX;
        getPlaying().getPlayer().screenY = getPlaying().getPlayer().worldY - getPlaying().getPlayer().cameraY;
        //SET START OBJECTS
        tileM.loadMap("/res/maps/world01.txt");
               
        //DRAW
        screen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
            g2 = (Graphics2D) screen.getGraphics();
        
    
        //SET FULLSCREEN
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);
    }   

    public void startGameThread() {
            gameThread = new Thread(this);
            gameThread.start();
        }
    
    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS;
        double timePerUpdate = 1000000000.0 / UPS;
        
        long previousTime = System.nanoTime();

        frames = 0;
        updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;
        while (gameThread != null) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }
            if (deltaF >= 1) {
                render();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                //System.out.println("FPS: " + frames + " UPS: "+ updates);
                frames = 0;
                updates = 0;
            }
        }
    }


    //HELPER METHODS
    public void windowFocusLost() {
        // if (Gamestate.state == Gamestate.PLAYING) {
        //     playing.getPlayer().resetDirectionBooleans();
        // }
    }

    //GETTERS 
    public Playing getPlaying() {
        return playing;
    }
    public Menu getMenu() {
        return menu;
    }

}
