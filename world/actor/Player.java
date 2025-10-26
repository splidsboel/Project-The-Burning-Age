package world.actor;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;
import tools.UtilityTool;

/**
 * Player represents the controllable actor in the world.
 * Handles input, animation, camera movement, and collision.
 */
public class Player extends Actor {
    private final GamePanel gp;

    // Animation
    private BufferedImage[][] animations;
    private BufferedImage spriteSheet;
    private int aniTick;
    private int aniIndex;
    private int aniSpeed = 13; // animation frame speed

    // Animation states
    public static final int runningDown = 0;
    public static final int runningUp = 1;
    public static final int runningLeft = 2;
    public static final int runningRight = 3;
    public int playerAction = runningDown;

    // Camera
    private double cameraX, cameraY;
    public double screenX, screenY;

    public Player(GamePanel gp, double x, double y) {
        super(gp, x, y, null, true);
        this.gp = gp;
        setDefaultValues();
        loadSpriteSheet();
        loadAnimations();
    }

    private void setDefaultValues() {
        x = gp.tileSize * 13;
        y = gp.tileSize * 15;

        pixels = 32; // base sprite size (one tile)
        defaultSpeed = (int)(2 * gp.deviceScale);
        speed = defaultSpeed;

        // Define hitbox using base sprite pixel units — NOT scaled
        setSolidArea(
            (int)(pixels * 0.45),
            (int)(pixels * 0.85),
            (int)(pixels * 0.15),
            (int)(pixels * 0.08)
        );
    }


    @Override
    public void update() {
        handleInput();
        updateCamera();
        updatePosition();
        updateAnimation();
        super.update();
    }

    @Override
    public void draw(Graphics2D g2, double cameraX, double cameraY) {
        if(moving) {
            BufferedImage frame = animations[aniIndex][playerAction];
            int drawX = (int) (screenX);
            int drawY = (int) (screenY);
            g2.drawImage(frame, drawX, drawY, gp.tileSize, gp.tileSize, null);
        } else {
            BufferedImage frame = animations[0][playerAction];
            int drawX = (int) (screenX);
            int drawY = (int) (screenY);
            g2.drawImage(frame, drawX, drawY, gp.tileSize, gp.tileSize, null);
        }

    }

    private void handleInput() {
        //up = gp.keyH.upPressed;
        //down = gp.keyH.downPressed;
        //left = gp.keyH.leftPressed;
        //right = gp.keyH.rightPressed;
        //moving = up || down || left || right;
    }

    private void loadSpriteSheet() {
        spriteSheet = UtilityTool.importImg("images/world/player/orc/orc.png");
    }

    private void loadAnimations() {
        animations = new BufferedImage[3][4];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = spriteSheet.getSubimage(i * pixels, j * pixels, pixels, pixels);
            }
        }
    }

    private void updateAnimation() {
        if (moving) {
            setAnimationDirection();
            aniTick++;
            if (aniTick >= aniSpeed) {
                aniTick = 0;
                aniIndex++;
                if (aniIndex >= animations.length) {
                    aniIndex = 1;
                }
            }
        }
    }

    private void setAnimationDirection() {
        if (up)    playerAction = runningUp;
        if (down)  playerAction = runningDown;
        if (left)  playerAction = runningLeft;
        if (right) playerAction = runningRight;
    }

    private void updatePosition() {
        updateSolidArea();
        gp.cChecker.check(this);
        moving = false;
        boolean horizontal = left ^ right;
        boolean vertical = up ^ down;
        double moveSpeed = (horizontal && vertical) ? speed / Math.sqrt(2.0) : speed;

        // vertical first
        if (up && !down && !collisionUp) {
            y -= moveSpeed;
            moving = true;
        } else if (down && !up && !collisionDown) {
            y += moveSpeed;
            moving = true;
        }
        // horizontal next
        if (left && !right && !collisionLeft) {
            x -= moveSpeed;
            moving = true;
        } else if (right && !left && !collisionRight) {
            x += moveSpeed;
            moving = true;
        }
        // Clamp position within world bounds
        x = Math.max(0, Math.min(x, gp.maxWorldCol * gp.tileSize - gp.tileSize));
        y = Math.max(0, Math.min(y, gp.maxWorldRow * gp.tileSize - gp.tileSize));
        updateSolidArea();
    }

    public void updateCamera() {
        cameraX = x - gp.screenWidth / 2.0 + gp.tileSize / 2.0;
        cameraY = y - gp.screenHeight / 2.0 + gp.tileSize / 2.0;
        double worldWidth = gp.maxWorldCol * gp.tileSize;
        double worldHeight = gp.maxWorldRow * gp.tileSize;
        cameraX = Math.max(0, Math.min(cameraX, worldWidth - gp.screenWidth));
        cameraY = Math.max(0, Math.min(cameraY, worldHeight - gp.screenHeight));
        screenX = x - cameraX;
        screenY = y - cameraY;
    }

    @Override
    public double getBottomY() {
        if (solidArea != null) {
            return solidArea.y + solidArea.height; // bottom in world coords
        }
        return y; // fallback
    } 

    //GETTERS
    public double getCameraX() {
        return cameraX;
    }

    public double getCameraY() {
        return cameraY;
    }
    
}
