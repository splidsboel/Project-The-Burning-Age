package world.actor.NPC;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;
import tools.UtilityTool;
import world.actor.Actor;

public class Orc extends Actor {
     private final GamePanel gp;

    // Animation
    private BufferedImage spriteSheet;
    private int aniSpeed; // animation frame speed

    // Animation states
    private int moveTimer;
    private final java.util.Random rand = new java.util.Random();
    public static final int runningDown = 0;
    public static final int runningUp = 1;
    public static final int runningLeft = 2;
    public static final int runningRight = 3;
    public int orcAction = runningLeft;

    public Orc(GamePanel gp, double x, double y) {
        super(gp, x, y, null, true);
        this.gp = gp;
        setDefaultValues();
        loadSpriteSheet();
        loadAnimations();
    }

    private void setDefaultValues() {
        pixels = 32; // base sprite size (one tile)
        speed = (int)(0.4 * (gp.deviceScale * gp.zoomScale));
        aniSpeed = 50;
        

        setHitBox(
            0.3,
            0.5
        );
        setSolidArea(
            (int)(pixels * 0.45),
            (int)(pixels * 0.85),
            (int)(pixels * 0.15),
            (int)(pixels * 0.08)
        );
    }


    @Override
    public void update() {
        updateAnimation();
        randomMovement();
        super.update();
    }

    @Override
    public void draw(Graphics2D g2, double cameraX, double cameraY) {
        BufferedImage frame = animations[aniIndex][orcAction];
        int drawX = (int) (x - cameraX);
        int drawY = (int) (y -cameraY);
        g2.drawImage(frame, drawX, drawY, gp.tileSize, gp.tileSize, null);
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
        setAnimationDirection();
        animation(aniSpeed);
    }

    private void setAnimationDirection() {
        if (up)    orcAction = runningUp;
        if (down)  orcAction = runningDown;
        if (left)  orcAction = runningLeft;
        if (right) orcAction = runningRight;
    }

    private void randomMovement() {
        gp.cChecker.check(this);
        moveTimer++;
        if (moveTimer >= UtilityTool.randomNumberInterval(500, 1000)) {
            moveTimer = 0;
            int dir = rand.nextInt(11);
            up = down = left = right = false;
            switch (dir) {
                case 0 -> up = true;
                case 1 -> { up = true; left = true; }
                case 2 -> { up = true; right = true; }
                case 3 -> down = true;
                case 4 -> { down = true; left = true; }
                case 5 -> { down = true; right = true; }
                case 6 -> moving = false;
                case 7 -> moving = false;
                case 8 -> moving = false;
                case 9 -> moving = false;
                case 10 -> moving = false;
            }
        }

        moving = up || down || left || right;
        if (up && !collisionUp) y -= speed; 
        if (down && !collisionDown) y += speed; 
        if (left && !collisionLeft) x -= speed;
        if (right && !collisionRight) x += speed;
    }


    @Override
    public double getBottomY() {
        if (solidArea != null) {
            return solidArea.y + solidArea.height; // bottom in world coords
        }
        return y; // fallback
    } 
}
