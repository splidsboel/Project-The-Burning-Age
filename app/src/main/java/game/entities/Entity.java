package game.entities;

import engine.core.Game;
import game.entities.behavior.Renderable;
import game.entities.behavior.Updateable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Entity implements Renderable, Updateable {
    protected Game game;
    protected Image image;
    protected double x, y;
    protected double width, height;

    //Animation
    private double aniTimer;
    private int aniIndex;
    private double aniSpeed = 0.1; // seconds per frame (~7.7 FPS)

    public Entity (Game game, double x, double y, double width, double height) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void update(double delta) { }

    @Override
    public void render(GraphicsContext g) { }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    public Rectangle2D setBounds() {
        return new Rectangle2D(x, y, width, height);
    }
    
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

}
