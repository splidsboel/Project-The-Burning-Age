package game.entities;

import engine.core.Game;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Actor extends Entity {
    protected double speed;
    protected double dx, dy;

    //Stats
    protected int health;

    // Solid area if needed (for obstacles)
    protected Rectangle2D solidArea;
    private int solidOffsetX, solidOffsetY;
    private int solidBaseWidth, solidBaseHeight;


    public Actor(Game game, double x, double y, double width, double height, double speed) {
        super(game, x, y, width, height);
        this.speed = speed;
        
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(GraphicsContext g) {
        
    }

        public void setSolidArea(int offsetX, int offsetY, int width, int height) {
        solidOffsetX = offsetX;
        solidOffsetY = offsetY;
        solidBaseWidth = width;
        solidBaseHeight = height;
        updateSolidArea();
    }

    public void updateSolidArea() {
        double scale = game.getTileSize() / game.getOriginalTileSize();
        solidArea = new Rectangle2D(
            (int)(x + solidOffsetX * scale),
            (int)(y + solidOffsetY * scale),
            (int)(solidBaseWidth * scale),
            (int)(solidBaseHeight * scale)
        );
    }

    public Rectangle2D getSolidArea() {
        return solidArea;
    }

        // For depth sorting: the Y-coordinate of the “feet” or base
    @Override
    public double getBottomY() {
        if (solidArea != null) {
            return solidArea.getMinY() + solidArea.getHeight(); // bottom in world coords
        }
        return y; // fallback
    } 
}
