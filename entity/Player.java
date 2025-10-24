package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import tools.Constants.PlayerConstants;
import static tools.Constants.PlayerConstants.*;

public class Player extends Entity {
    GamePanel gp;
    BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 10; // 90 fps / 2 animationer
    public int playerAction = RUNNING_LEFT;


    public double cameraX = 0;
    public double cameraY = 0;
    public double screenX; 
    public double screenY; 

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
        updateCameraOnPlayer();
        updateAnimationTick();
        setAnimation();
        updatePosition();



    }

    public void render(Graphics2D g2) {  
        drawPlayerImage(g2);
        
        // DEBUG: show solidArea
        // g2.setColor(Color.GREEN);
        // g2.drawRect((int)(worldX + solidArea.x - cameraX),(int)(worldY + solidArea.y - cameraY),solidArea.width,solidArea.height);
    }

    public void drawPlayerImage(Graphics2D g2){
        if (this.moving) {
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
    setSolidArea();

    // run collision
    gp.cChecker.check(this);

    moving = false;

    // handle diagonal speed reduction
    boolean horizontalMove = left ^ right;
    boolean verticalMove   = up ^ down;
    boolean diagonalMove   = horizontalMove && verticalMove;
    double moveSpeed       = diagonalMove ? speed / Math.sqrt(2.0) : speed;

    double startX = worldX;
    double startY = worldY;

    // vertical first
    if (up && !down && !collisionUp) {
        worldY -= moveSpeed;
    } else if (down && !up && !collisionDown) {
        worldY += moveSpeed;
    }

    // horizontal next
    if (left && !right && !collisionLeft) {
        worldX -= moveSpeed;
    } else if (right && !left && !collisionRight) {
        worldX += moveSpeed;
    }

    // clamp to world bounds
    worldX = Math.max(0, Math.min(worldX, (gp.maxWorldCol * gp.tileSize) - gp.tileSize));
    worldY = Math.max(0, Math.min(worldY, (gp.maxWorldRow * gp.tileSize) - gp.tileSize));

    // set moving flag if position changed
    if (worldX != startX || worldY != startY) {
        moving = true;
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

    public void setSolidArea() {
        solidArea = new Rectangle((int)(gp.tileSize*0.34),(int)(gp.tileSize*0.85),(int)(gp.tileSize*0.28),(int)(gp.tileSize*0.11)); 
        // solidArea.x = 11;
        // solidArea.y = 23;
        // solidArea.width = 9;
        // solidArea.height = 7;
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

    
    
}
