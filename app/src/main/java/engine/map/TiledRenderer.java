package engine.map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TiledRenderer {
    private final TiledMap map;
    private final Image tilesetImage;
    private final int tilesPerRow;

    public TiledRenderer(TiledMap map) {
        this.map = map;
        String tilesetPath = map.tilesets[0].image;
        this.tilesetImage = new Image(getClass().getResource(tilesetPath).toExternalForm());
        tilesPerRow = map.tilesets[0].imagewidth / map.tilewidth;
    }

    public void render(GraphicsContext g) {
        for (TiledMap.Layer layer : map.layers) {
            if (!"tilelayer".equals(layer.type)) continue;
            drawTileLayer(g, layer);
        }
    }

    private void drawTileLayer(GraphicsContext g, TiledMap.Layer layer) {
        for (int y = 0; y < map.height; y++) {
            for (int x = 0; x < map.width; x++) {
                int index = y * map.width + x;
                int gid = layer.data[index];
                if (gid == 0) continue; // empty tile

                gid--; // Tiled is 1-based
                int srcX = (gid % tilesPerRow) * map.tilewidth;
                int srcY = (gid / tilesPerRow) * map.tileheight;

                g.drawImage(
                    tilesetImage,
                    srcX, srcY, map.tilewidth, map.tileheight,
                    x * map.tilewidth, y * map.tileheight, map.tilewidth, map.tileheight
                );
            }
        }
    }
}
