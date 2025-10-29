package game.entities.actors;

import javafx.geometry.Rectangle2D;

import engine.core.Game;
import engine.input.KeyboardInput;
import game.entities.Actor;
import game.entities.behavior.Collidable;
import game.entities.behavior.Controllable;
import game.entities.behavior.Damageable;
import game.entities.behavior.Moveable;
import game.states.PlayState;
import game.states.play.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Player extends Actor implements Collidable, Controllable, Moveable, Damageable {
    private World world;
    private final Image spriteSheet;
    private Image[][] animations;
    private int pixels; 
    private int playerWidth;
    private int playerHeight;

    private boolean up, down, left, right = false;
    private boolean collisionUp, collisionDown, collisionLeft, collisionRight;
    private boolean moving;
    private double aniTimer;
    private int aniIndex;
    private final double aniSpeed = 0.2; // seconds per frame 
    private final int runDown = 0, runUp = 1, runLeft = 2, runRight = 3;
    private int playerAction = runLeft;


    public Player(Game game, World world, double x, double y) {
        super(game, game.getTileSize()*13, game.getTileSize()*13, game.getTileSize(), game.getTileSize(), 500);
        this.world = world;
        this.spriteSheet = new Image(getClass().getResource("/assets/actors/player/orc.png").toExternalForm());
        this.pixels = 32;
        loadAnimations();
        
        setSolidArea(
            (int)(pixels * 0.45),
            (int)(pixels * 0.85),
            (int)(pixels * 0.15),
            (int)(pixels * 0.08)
        );
        
    }

    @Override
    public void update(double delta) {
        handleInput();
        move(delta);
        updateAnimation(delta);
        setAnimationDirection();
    }

    @Override
    public void render(GraphicsContext g) {
        Image frame = animations[aniIndex][playerAction];
        g.drawImage(frame, x, y, width, height);
       
       
    }

    @Override
    public void handleInput() {
        up = down = left = right = false;
        KeyboardInput keys = game.getKeyboardInput();
        if (keys.isKeyPressed(KeyCode.W)) {
            up = true;
        }
        if (keys.isKeyPressed(KeyCode.S)) {
            down = true;
        }
        if (keys.isKeyPressed(KeyCode.A)) {
            left = true;
        }
        if (keys.isKeyPressed(KeyCode.D)) {
            right = true;
        }
    }

    @Override
    public void move(double delta) {
        updateSolidArea();
        //game.getCollisionChecker().check(this);
        moving = false;
        boolean horizontal = left ^ right;
        boolean vertical = up ^ down;
        double moveSpeed = (horizontal && vertical) ? (speed / Math.sqrt(2.0)) * delta : speed * delta;
        
        double nextX = x;
        double nextY = y;

        if (up && !down) {
            nextY -= moveSpeed; 
            moving = true;
        }
        if (down && !up){
            nextY += moveSpeed;
            moving = true;
        } 
        if (left && !right){
            nextX -= moveSpeed;
            moving = true;
        }
        if (right && !left){
            nextX += moveSpeed;
            moving = true;
        } 

            // Predictive solid collision check
        if (!game.getCollision().willCollideWithSolid(game.getTiledMap(), this,
            nextX - x, nextY - y, (int)game.getTileSize(), world.getEntities())) {
            x = nextX;
            y = nextY;
        }

        // Clamp player inside world bounds (in pixels)
        double maxX = (game.getTiledMap().getMapWidth()  * game.getTileSize()) - width;
        double maxY = (game.getTiledMap().getMapHeight() * game.getTileSize()) - height;

        x = Math.max(0, Math.min(x, maxX));
        y = Math.max(0, Math.min(y, maxY));
    }

    private void updateAnimation(double delta) {
        if (moving) {
                aniTimer += delta;
                if (aniTimer >= aniSpeed) {
                    aniTimer = 0;
                    aniIndex++;
                    if (aniIndex >= animations.length) {
                    aniIndex = 1;
                    }
                }
        } else {
            aniIndex = 0;
        }
    }

    private void setAnimationDirection() {
        if (up)    playerAction = runUp;
        if (down)  playerAction = runDown;
        if (left)  playerAction = runLeft;
        if (right) playerAction = runRight;
    }

    @Override
    public double getBottomY() {
        if (solidArea != null) {
            return solidArea.getMaxY(); // already equals y + height
        }
        return y + height; // fallback
    }


    private void loadAnimations() {
        animations = new Image[3][4];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                PixelReader reader = spriteSheet.getPixelReader();
                animations[i][j] = new WritableImage(reader, i * pixels, j * pixels, pixels, pixels);
            }
        }
    }

    @Override
    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    @Override
    public Rectangle2D getHitbox() {
        return solidArea; // or a slightly larger area if you want pickup overlap
    }

    @Override
    public Rectangle2D getSolidArea() {
        return solidArea;
    }

    @Override
    public boolean isSolid() {
        return true; // blocks movement for other entities
    }

    @Override
    public void onCollide(Collidable other) {
        // Example reactions:
        // if (other instanceof Enemy) takeDamage(1);
        // if (other instanceof ItemDrop) pickup((ItemDrop) other);
    }




}
