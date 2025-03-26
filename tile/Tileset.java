package tile;

import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;

public class Tileset {
    private final Class<? extends Tile> tileClass;
    private final BufferedImage spriteSheet;
    private final int tileCount;
    private final int tileSize;

    public Tileset(Class<? extends Tile> tileClass, BufferedImage spriteSheet, int tileSize) {
        this.tileClass = tileClass;
        this.spriteSheet = spriteSheet;
        this.tileSize = tileSize;

        int tilesPerRow = spriteSheet.getWidth() / tileSize;
        int tileRows = spriteSheet.getHeight() / tileSize;
        this.tileCount = tilesPerRow * tileRows;
    }

    public boolean containsGlobalId(int globalId, int firstgid) {
        int relativeId = globalId - firstgid;
        return relativeId >= 0 && relativeId < tileCount; 
      
    }

    public Tile createTile(int globalId, int firstgid) {
        int relativeId = globalId - firstgid;

        // Bounds check to avoid subimage crash
        if (relativeId < 0 || relativeId >= tileCount) {
            System.err.println("Tileset: Invalid tile ID " + globalId + " (relative: " + relativeId + ")");
            return null;
        }

        int tilesPerRow = spriteSheet.getWidth() / tileSize;
        int x = (relativeId % tilesPerRow) * tileSize;
        int y = (relativeId / tilesPerRow) * tileSize;

        try {
            BufferedImage tileImage = spriteSheet.getSubimage(x, y, tileSize, tileSize);
            Constructor<? extends Tile> constructor = tileClass.getConstructor(BufferedImage.class);
            return constructor.newInstance(tileImage);
        } catch (Exception e) {
            System.err.println("Tileset: Failed to instantiate tile at " + x + "," + y);
            e.printStackTrace();
            return null;
        }
    }
}

