package game.entities.actors.npc;

import engine.core.Game;
import game.entities.Actor;
import game.entities.behavior.Collidable;
import game.entities.behavior.Damageable;
import game.entities.behavior.Hittable;
import game.entities.behavior.Moveable;
import game.states.play.World;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Orc extends Actor implements Collidable, Damageable, Moveable, Hittable {
    private World world;
    private final Image spriteSheet;
    private Image[][] animations;
    private int pixels; 

    private boolean up, down, left, right = false;
    private boolean moving;
    private double aniTimer;
    private int aniIndex;
    private final double aniSpeed = 0.2; // seconds per frame 
    private final int moveDown = 0, moveUp = 1, moveLeft = 2, moveRight = 3;
    private int playerAction = moveLeft;

    public Orc(Game game, World world, double x, double y) {
        super(game, game.getTileSize()*247, game.getTileSize()*250, game.getTileSize()*2, game.getTileSize(), 50);
        this.world = world;
        this.spriteSheet = new Image(getClass().getResource("/assets/actors/player/orc.png").toExternalForm());
        this.pixels = 32;

        loadAnimations();
        setSolidArea(pixels * 0.42,pixels * 0.85,pixels * 0.15,pixels * 0.08);
        setHitbox(0.3,0.5);
    }

    @Override
    public void update(double delta) {
        move(delta);
        updateAnimation(delta);
        setAnimationDirection();
    }

    @Override
    public void render(GraphicsContext g) {
        Image frame = animations[aniIndex][playerAction];
        // Draw the player
        g.drawImage(frame, x, y, width, height);

        g.restore();
    }

    @Override
    public void move(double delta) {
        updateSolidArea();
        updateHitbox();
        moving = false;

       
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
        if (up)    playerAction = moveUp;
        if (down)  playerAction = moveDown;
        if (left)  playerAction = moveLeft;
        if (right) playerAction = moveRight;
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
        return hitbox; // or a slightly larger area if you want pickup overlap
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
