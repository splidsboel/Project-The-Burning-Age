package main;

import gamestates.*;
import gamestates.Menu;
import input.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import tile.*;
import tools.UtilityTool;
import world.*;

public class GamePanel extends JPanel implements Runnable {

    // Core systems
    private Thread gameThread;
    public Playing playing;
    public Menu menu;
    public KeyHandler keyH;
    public MouseHandler mouseH;
    public UtilityTool uTool;
    public CollisionChecker cChecker;
    public TileManager tileM;
    public EntityManager entityM;

    // Graphics & scaling
    private BufferedImage screen;
    private Graphics2D g2;

    public final int originalTileSize = 32;
    public int tileSize = 64;
    public int initialTileSize;
    public float deviceScale = 1.0f;
    public float zoomScale = 1.0f;

    private int targetTileSize = -1;
    private int zoomSpeed = 40;
    private boolean zoomAnimating = false;

    // Screen & resolution
    public int screenWidth;
    public int screenHeight;
    public int virtualWidth = 24 * originalTileSize;
    public int virtualHeight = 13 * originalTileSize;

    // Frame timing
    private static final int FPS = 90;
    private static final int UPS = 120;
    private int frames;
    private int updates;
    private int currentFPS;
    private int currentUPS;

    // World limits
    public int maxWorldCol = 100;
    public int maxWorldRow = 100;

    public GamePanel() {
        detectDeviceScale();
        initSystems();
        setupListeners();
    }

    /** Initializes all subsystems */
    private void initSystems() {
        // Game states
        playing = new Playing(this);
        menu = new Menu(this);

        // Input and utilities
        keyH = new KeyHandler(this);
        mouseH = new MouseHandler(this);
        uTool = new UtilityTool(this);
        cChecker = new CollisionChecker(this);

        // World systems
        tileM = new TileManager(this);
        entityM = new EntityManager(this);

        // Initial world load
        TiledMapLoader.loadTileLayer("images/world/world.tmj", tileM);
        EntityTiledLoader.loadEntities("images/world/world.tmj", entityM, this);

        // Prepare render buffer
        screen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) screen.getGraphics();
    }

    private void setupListeners() {
        addKeyListener(keyH);
        addMouseListener(mouseH);
        addMouseMotionListener(mouseH);
        addMouseWheelListener(mouseH);
        setFocusable(true);
        requestFocus();
    }

    /** Detects screen resolution and sets device scale */
    private void detectDeviceScale() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        int detectedWidth = gd.getDisplayMode().getWidth();
        int detectedHeight = gd.getDisplayMode().getHeight();

        if (detectedWidth > 2000) {
            screenWidth = detectedWidth / 2;
            screenHeight = detectedHeight / 2;
        } else {
            screenWidth = detectedWidth;
            screenHeight = detectedHeight;
        }

        float scaleX = screenWidth / (float) virtualWidth;
        float scaleY = screenHeight / (float) virtualHeight;
        deviceScale = Math.min(scaleX, scaleY);

        initialTileSize = (int) (originalTileSize * deviceScale);
        tileSize = initialTileSize;
    }

    // =====================
    //   GAME LOOP
    // =====================
    @Override
    public void run() {
        double timePerFrame = 1_000_000_000.0 / FPS;
        double timePerUpdate = 1_000_000_000.0 / UPS;

        long previousTime = System.nanoTime();
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0, deltaF = 0;
        frames = updates = 0;

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
                currentFPS = frames;
                currentUPS = updates;
                frames = updates = 0;
                lastCheck = System.currentTimeMillis();
            }
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // =====================
    //   UPDATE & RENDER
    // =====================
    public void update() {
        switch (Gamestate.state) {
            case PLAYING -> {
                playing.update();
                if (zoomAnimating) animateZoomStep();
            }
            case MENU -> menu.update();
            case OPTIONS, QUIT -> System.exit(0);
        }
    }

    public void render() {
        switch (Gamestate.state) {
            case PLAYING -> playing.draw(g2);
            case MENU -> menu.draw(g2);
        }

        Graphics g = getGraphics();
        g.drawImage(screen, 0, 0, screenWidth, screenHeight, null);
        g.dispose();
    }

    // =====================
    //   ZOOM SYSTEM
    // =====================
    public void zoomInOut(int zoomChange) {
        int newTileSize = tileSize + zoomChange;
        if (newTileSize < initialTileSize || newTileSize > 256) return;

        targetTileSize = newTileSize;
        zoomAnimating = true;
    }

    private void animateZoomStep() {
        if (tileSize == targetTileSize) {
            zoomAnimating = false;
            return;
        }

        int dir = Integer.compare(targetTileSize, tileSize);
        tileSize += zoomSpeed * dir;

        if ((dir > 0 && tileSize > targetTileSize) || (dir < 0 && tileSize < targetTileSize)) {
            tileSize = targetTileSize;
            zoomAnimating = false;
        }

        zoomScale = (float) tileSize / originalTileSize;
        //tileM.onZoomChange(); // clear tile caches
        getPlaying().getPlayer().updateCamera();
    }

    // =====================
    //   DEBUG OVERLAY
    // =====================
    public void debugText(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        int x = 10, y = 400, lh = 20;
        var p = getPlaying().getPlayer();
        g2.drawString("worldX: " + p.x, x, y += lh);
        g2.drawString("worldY: " + p.y, x, y += lh);
        g2.drawString("cameraX: " + p.getCameraX(), x, y += lh);
        g2.drawString("cameraY: " + p.getCameraY(), x, y += lh);
        g2.drawString("tileSize: " + tileSize, x, y += lh);
        g2.drawString("deviceScale: " + deviceScale, x, y += lh);
        g2.drawString("zoomScale: " + zoomScale, x, y += lh);
        g2.drawString("FPS: " + currentFPS, x, y += lh);
        g2.drawString("UPS: " + currentUPS, x, y += lh);
    }

    // =====================
    //   GETTERS
    // =====================
    public Playing getPlaying() { return playing; }
    public Menu getMenu() { return menu; }
    public TileManager getTileM() { return tileM; }
    public EntityManager getEntityM() { return entityM; }
    public CollisionChecker getCollisionChecker() { return cChecker; }
    public float getZoomScale() { return zoomScale; }
    public float getDeviceScale() { return deviceScale; }
}
