package game.entities;

import engine.core.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Decoration extends Entity {
    protected final Image sprite;
    public Decoration(Game game, Image sprite, double x, double y, double width, double height) {
        super(game, x, y, width, height);
        this.sprite = sprite;
    }

    @Override
    public void render(GraphicsContext g) {
        g.drawImage(sprite, x, y);
    }

    @Override
    public void update(double delta) {

    }
}
