package game.tiles;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Tile {
    protected Image texture;

    public Tile(Image texture) {
        this.texture = texture;
    }

    public void render(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(texture, x, y, size, size);
    }
    
}
