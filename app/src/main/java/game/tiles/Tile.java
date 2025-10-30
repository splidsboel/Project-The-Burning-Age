package game.tiles;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Tile {
    protected Image image;

    public Tile(Image image) {
        this.image = image;
    }

    public void render(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(image, x, y, size, size);
    }

    public boolean isSolid() {
        return false;
    }
 
}
