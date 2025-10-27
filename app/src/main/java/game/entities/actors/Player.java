package game.entities.actors;

import engine.core.Game;
import engine.input.KeyboardInput;
import game.entities.Actor;
import game.entities.behavior.Controllable;
import game.entities.behavior.Damageable;
import game.entities.behavior.Moveable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;

public class Player extends Actor implements Controllable, Moveable, Damageable {
    private Image spriteSheet;
    private Image[][] animations;
    private int pixels; 
    private int playerWidth;
    private int playerHeight;

    private boolean up, down, left, right = false;
    private boolean collisionUp, collisionDown, collisionLeft, collisionRight;
    private boolean moving;
    private double aniTimer;
    private int aniIndex;
    private double aniSpeed = 0.1; // seconds per frame (~7.7 FPS)
    private int runDown = 0, runUp = 1, runLeft = 2, runRight = 3;
    private int playerAction = runLeft;





    public Player(Game game, double x, double y) {
        super(game, x, y, game.getOriginalTileSize(), game.getOriginalTileSize(), 150);
        this.spriteSheet = new Image(getClass().getResource("/assets/actors/player/orc.png").toExternalForm());
        this.pixels = 32;
        loadAnimations();
    }

    @Override
    public void update(double delta) {
        handleInput();
        move(delta);
        updateAnimation(delta);
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
        //game.getCollisionChecker().check(this);
        moving = false;
        boolean horizontal = left ^ right;
        boolean vertical = up ^ down;
        double moveSpeed = (horizontal && vertical) ? (speed / Math.sqrt(2.0)) * delta : speed * delta;

        // Vertical
        if (up && !down && !collisionUp) {
            y -= moveSpeed;
            moving = true;
        } else if (down && !up && !collisionDown) {
            y += moveSpeed;
            moving = true;
        }

        // Horizontal
        if (left && !right && !collisionLeft) {
            x -= moveSpeed;
            moving = true;
        } else if (right && !left && !collisionRight) {
            x += moveSpeed;
            moving = true;
        }

        // Clamp to world
        double maxX = game.getWorldWidth() - width;
        double maxY = game.getWorldHeight() - height;
        x = Math.max(0, Math.min(x, maxX));
        y = Math.max(0, Math.min(y, maxY));
    }

    private void updateAnimation(double delta) {
        if (moving) {
            setAnimationDirection();
                aniTimer += delta;
                if (aniTimer >= aniSpeed) {
                    aniTimer = 0;
                    aniIndex = (aniIndex + 1) % animations.length;
                }
        } else {
            aniIndex = 0;
        }
    }

    private void setAnimationDirection() {
        if (up) {
            playerAction = runUp;
        } 
        if (down) {
            playerAction = runDown;
        }
        if (left) {
            playerAction = runLeft;
        } 
        if (right) {
            playerAction = runRight;
        }
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



}
