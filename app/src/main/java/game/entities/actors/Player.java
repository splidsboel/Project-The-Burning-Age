package game.entities.actors;

import engine.core.Game;
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

    public Player(Game game, double x, double y) {
        super(game, x, y, game.getOriginalTileSize(), game.getOriginalTileSize(), 200.0);
        this.x = x;
        this.y = y;
        this.spriteSheet = new Image(getClass().getResource("/assets/actors/player/orc.png").toExternalForm());
        this.pixels = 32;
        animations = new Image[3][4];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                PixelReader reader = spriteSheet.getPixelReader();
                animations[i][j] = new WritableImage(reader, i * pixels, j * pixels, pixels, pixels);
            }
        }

    }

    @Override
    public void update(double delta) {
        handleInput(delta);
    }

    @Override
    public void render(GraphicsContext g) {
        Image frame = animations[2][2];
        g.drawImage(frame, x, y, width, height);
    }

    @Override
    public void handleInput(double delta) {
        double dx = 0;
        double dy = 0;

        var keys = game.getKeyboardInput();
        if (keys.isKeyPressed(KeyCode.W)) dy -= 1;
        if (keys.isKeyPressed(KeyCode.S)) dy += 1;
        if (keys.isKeyPressed(KeyCode.A)) dx -= 1;
        if (keys.isKeyPressed(KeyCode.D)) dx += 1;

        move(dx * delta, dy * delta);
    }

    @Override
    public void move(double dx, double dy) {
        this.x += dx * speed;
        this.y += dy * speed;
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
