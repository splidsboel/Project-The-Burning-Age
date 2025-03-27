package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import entity.Player;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import input.KeyHandler;
import input.MouseHandler;
import tile.TileManager;
import tools.UtilityTool;
import world.DecorManager;
import world.decoration.DecorAssetLoader;

public class GamePanel extends JPanel implements Runnable {
    //THREAD
    Thread gameThread;

    //GAMESTATES
    public Playing playing;
    public Menu menu;

    //GRAPHICS
    private int targetTileSize = -1;
    private int zoomAnimationSpeed = 2; // pixels per frame
    private boolean zoomAnimating = false;

    //SYSTEMS
    public KeyHandler keyH; 
    public MouseHandler mouseH;
    public TileManager tileM;
    public DecorManager decorM;
    public UtilityTool uTool;

    //SCREEN SETTINGS
    public final int originalTileSize = 32;  
    public float scale;  // Scale factor for rendering
    public int tileSize = originalTileSize;
    public int initialTileSize;

    public int virtualWidth = 24 * originalTileSize; //768
    public int virtualHeight = 13 * originalTileSize; //432. amount of tile-pixels on screen. 16:0
    public int detectedScreenWidth;
    public int detectedScreenHeight;
    public int screenWidth; // display resolution
    public int screenHeight; 
    public int currentZoom = 0;

    protected final int FPS = 90;
    protected int currentFPS;
    protected final int UPS = 120;
    protected int currentUPS;
    public int frames;
    public int updates;
    BufferedImage screen;
    Graphics2D g2;

    //WORLD SETTINGS
    public int maxWorldCol = 25;
    public int maxWorldRow = 25;

    
    public GamePanel() {
        deviceScaleDetection();
        initializeClasses();

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
        this.addMouseWheelListener(mouseH);
        uTool = new UtilityTool(this);
        tileM = new TileManager(this);
        decorM = new DecorManager();
        
    }

    public void update() {

        //GRAPHICS
        if (zoomAnimating) {
            animateZoomStep();
        }
          
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
    
    public void deviceScaleDetection() {
        // Detect actual screen size
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        detectedScreenWidth = gd.getDisplayMode().getWidth();
        detectedScreenHeight = gd.getDisplayMode().getHeight();

        if (detectedScreenWidth > 2000) {
            screenWidth = detectedScreenWidth / 2;
            screenHeight = detectedScreenHeight / 2;
        } else {
            screenWidth = detectedScreenWidth ;
            screenHeight = detectedScreenHeight;
        }



        float scaleX = screenWidth/(float)virtualWidth;
        float scaleY = screenHeight/(float)virtualHeight;
        scale = Math.min(scaleX,scaleY);
        initialTileSize = (int)(tileSize * scale);
        tileSize = initialTileSize;

        //System.out.println("scaleX " + scaleX + ", scaleY " + scaleY + ", scale " + scale + ", detectedScreenWidth " + detectedScreenWidth + ", detectedHeight " +detectedScreenHeight + ", screenWidth " +screenWidth + ", screenHeight " +screenHeight);
    }
    
    //SETUP GAME AND START GAME THREAD METHODS
    public void setupGame() {
        //DRAW
        screen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) screen.getGraphics();
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
                currentFPS = frames;
                currentUPS = updates;
                //System.out.println("FPS: " + frames + " UPS: "+ updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    //HELPER METHODS
    public void debugText(Graphics2D g2) {
        g2.setColor(Color.black);
        int x = 10;
        int y = 400;
        int lineHeight = 20;
        g2.drawString("worldX: " + getPlaying().getPlayer().worldX, x, y); y += lineHeight;
        g2.drawString("worldY: " + getPlaying().getPlayer().worldY, x, y); y += lineHeight;
        g2.drawString("screenX: " + getPlaying().getPlayer().screenX, x, y); y += lineHeight;
        g2.drawString("screenY: " + getPlaying().getPlayer().screenY, x, y); y += lineHeight;
        g2.drawString("cameraX: " + getPlaying().getPlayer().cameraX, x, y); y += lineHeight;
        g2.drawString("cameraY: " + getPlaying().getPlayer().cameraY, x, y); y += lineHeight;
        g2.drawString("mouseX: " + mouseH.mouseX, x, y); y += lineHeight;
        g2.drawString("mouseY: " + mouseH.mouseY, x, y); y += lineHeight;
        g2.drawString("scale: " + scale, x, y); y += lineHeight;
        g2.drawString("tilesize: " + tileSize, x, y); y += lineHeight;
        g2.drawString("resolution: " + screenWidth + "x" + screenHeight, x, y); y += lineHeight;
        g2.drawString("Col: " + (getPlaying().getPlayer().worldX)/tileSize, x, y); y += lineHeight;
        g2.drawString("Row: " + (getPlaying().getPlayer().worldY)/tileSize, x, y); y += lineHeight;
        g2.drawString("FPS: " + currentFPS,x,y); y += lineHeight;
        g2.drawString("UPS: " + currentUPS,x,y); y += lineHeight;
        
    }


    //GETTERS 
    public Playing getPlaying() {
        return playing;
    }
    public Menu getMenu() {
        return menu;
    }

    public double getScale() {
        return scale;
    }


    //OTHER
    public void zoomInOut(int zoomChange) {
        int newTileSize = tileSize + zoomChange;
    
        if (newTileSize < initialTileSize || newTileSize > 250) return; // clamp
    
        targetTileSize = newTileSize;
        zoomAnimating = true;
    }
    
    private void animateZoomStep() {
        if (tileSize == targetTileSize) {
            zoomAnimating = false;
            return;
        }
    
        int oldTileSize = tileSize;
        int direction = targetTileSize > tileSize ? 1 : -1;
        tileSize += zoomAnimationSpeed * direction;
    
        // Clamp
        if ((direction == 1 && tileSize > targetTileSize) || (direction == -1 && tileSize < targetTileSize)) {
            tileSize = targetTileSize;
            zoomAnimating = false;
        }
    
        double multiplier = (double) tileSize / oldTileSize;
    
        Player player = getPlaying().getPlayer();
        player.speed *= multiplier;
        player.worldX *= multiplier;
        player.worldY *= multiplier;
    
        player.updateCameraOnPlayer();
        
        reloadWorld(); // world and decor gets placed with new tileSize
    }

    public void reloadWorld() {
        //Rebuild tiles
        tile.TiledMapLoader.loadTileLayer("images/world/world.tmj", tileM);
        getPlaying().tileM = tileM;
        
        //Rebuild decor
        DecorAssetLoader.clearCache();
        decorM = new world.DecorManager(); 
        tile.TiledMapLoader.loadDecorFromTiled("images/world/world.tmj", decorM, null, this);
        getPlaying().decorM = decorM; // update Playing's reference
        
    }
}
