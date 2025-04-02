package entity;

import static tools.Constants.PlayerConstants.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;
import tools.Constants.PlayerConstants;

public class Player extends Entity {
    Graphics2D g2;
    GamePanel gp;
    BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 10; // 90 fps / 2 animationer
    public int playerAction = RUNNING_LEFT;
    private boolean up, right, left, down, left_up, left_down, right_up, right_down;
    private boolean moving = false;


    public double cameraX = 0;
    public double cameraY = 0;
    public double screenX; 
    public double screenY; 


    //BOOLEANS
    public boolean playerMoved = false;

    public Player(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setDefaultValues();

        importPlayerImage();
        loadAnimations();

    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 13;
        worldY = gp.tileSize * 15;
        defaultSpeed =(int) (2 * gp.scale);
        speed = defaultSpeed;
    }

    public void update() {
        
    }

    public void render(Graphics2D g2) {  
        updateCameraOnPlayer();
        updateAnimationTick();
        setAnimation();
        updatePosition();
        
        drawPlayerImage(g2);
    }


    public void drawPlayerImage(Graphics2D g2){
        if (moving) {
            g2.drawImage(animations[aniIndex][playerAction],(int)screenX,(int)screenY, (int)gp.tileSize,(int)gp.tileSize, null);   
        } else {
            g2.drawImage(animations[0][playerAction],(int)screenX,(int)screenY, (int)gp.tileSize,(int)gp.tileSize, null);   
        }
    }

    public void importPlayerImage() {
        img = importImg("images/world/player/orc/orc.png");
    }

    public void loadAnimations() {
        animations = new BufferedImage[3][4];

        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(i*32, j*32, 32, 32);
            }
        }
    }

    public void setAnimation() {
        if (moving) {
            if (up) {
                playerAction = RUNNING_UP;
            }
            if (down) {
                playerAction = RUNNING_DOWN;
            }
            if (left) {
                playerAction = RUNNING_LEFT;
            }
            if (right) {
                playerAction = RUNNING_RIGHT;
            }
            
         } //else {
        //     playerAction = IDLE;
        // }
    } 

    public void dash() {
        
    }

    public void updatePosition() {
        moving = false;
        boolean horizontalMove = left ^ right; // XOR: true if either is true, but not both
        boolean verticalMove = up ^ down;
        boolean diagonalMove = horizontalMove && verticalMove;
        float effectiveSpeed = diagonalMove ? (int)speed / (float) Math.sqrt(2) : (int)speed;
    
        // Update position
        if (up && !down) {
            worldY -= effectiveSpeed;
            moving = true;
        } else if (down && !up) {
            worldY += effectiveSpeed;
            moving = true;
        }
        if (left && !right) {
            worldX -= effectiveSpeed;
            moving = true;
        } else if (right && !left) {
            worldX += effectiveSpeed;
            moving = true;
        }

        if (moving) {
            // Stay in map bounds
            worldX = Math.max(0, Math.min(worldX, (gp.maxWorldCol * gp.tileSize) - gp.tileSize));
            worldY = Math.max(0, Math.min(worldY, (gp.maxWorldRow * gp.tileSize) - gp.tileSize));
        }
    }

    public void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= PlayerConstants.GetSpriteAmount(playerAction)) {
                aniIndex = 1;
            }
        }
    }

    public void updateCameraOnPlayer() {
        // Center the camera on the player:
        // Adjust by half the tile size so that the player sprite is centered.
        cameraX = (worldX - gp.screenWidth / 2 + gp.tileSize / 2);
        cameraY = (worldY - gp.screenHeight / 2 + gp.tileSize / 2);
    
        // Calculate the total world dimensions in pixels:
        double worldPixelWidth = gp.maxWorldCol * gp.tileSize;
        double worldPixelHeight = gp.maxWorldRow * gp.tileSize;
    
        // Clamp the camera so it doesn't go past the world edges:
        cameraX = Math.max(0, Math.min(cameraX, worldPixelWidth - gp.screenWidth));
        cameraY = Math.max(0, Math.min(cameraY, worldPixelHeight - gp.screenHeight));
    
        // Update the player's screen position (the position on the display where the player should be drawn)
        screenX = worldX - cameraX;
        screenY = worldY - cameraY;
    }
    
    

    public void playerLookDirection() {
        // if (mouseH.mouseX < screenX) {
        //     playerAction = RIGHT;
        // }
        // if (mouseH.mouseX >= screenX) {
        //     playerAction = LEFT;
        // }
    }

    //GETTERS
    public boolean isUp() {
        return up;
    }
    public boolean isRight() {
        return right;
    }
    public boolean isLeft() {
        return left;
    }
    public boolean isDown() {
        return down;
    }
    public boolean isLeft_up() {
        return left_up;
    }
    public boolean isLeft_down() {
        return left_down;
    }
    public boolean isRight_up() {
        return right_up;
    }
    public boolean isRight_down() {
        return right_down;
    }
    
    //SETTERS
    public void setUp(boolean up) {
        this.up = up;
    }
    public void setRight(boolean right) {
        this.right = right;
    }
    public void setLeft(boolean left) {
        this.left = left;
    }
    public void setDown(boolean down) {
        this.down = down;
    }
    public void setLeft_up(boolean left_up) {
        this.left_up = left_up;
    }
    public void setLeft_down(boolean left_down) {
        this.left_down = left_down;
    }
    public void setRight_up(boolean right_up) {
        this.right_up = right_up;
    }
    public void setRight_down(boolean right_down) {
        this.right_down = right_down;
    }

    
    //
    
    
}
