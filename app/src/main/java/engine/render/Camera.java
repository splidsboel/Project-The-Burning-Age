package engine.render;

import game.entities.Entity;
import javafx.scene.canvas.GraphicsContext;

public class Camera {
    private double x, y;
    private final double viewportWidth;
    private final double viewportHeight;

    public Camera(double viewportWidth, double viewportHeight) {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
    }

    public void follow(Entity target, double worldWidth, double worldHeight) {
        x = target.getX() + target.getWidth() / 2 - viewportWidth / 2;
        y = target.getY() + target.getHeight() / 2 - viewportHeight / 2;

        // clamp to world edges
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x + viewportWidth > worldWidth) x = worldWidth - viewportWidth;
        if (y + viewportHeight > worldHeight) y = worldHeight - viewportHeight;
    }


    public void apply(GraphicsContext g) {
        g.translate(-x, -y);
    }

    public void reset(GraphicsContext g) {
        g.translate(x, y);
    }

    public double getX() { return x; }
    public double getY() { return y; }
}
