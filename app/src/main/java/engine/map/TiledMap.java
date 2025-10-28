package engine.map;

import game.entities.*;
import game.tiles.*;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Converts LoadedMapData into instantiated Tile and Entity objects.
 */
public class TiledMap {

    private final int width;
    private final int height;
    private final int tileWidth;
    private final int tileHeight;
    private final Tile[][] tiles;
    private final List<Entity> entities = new ArrayList<>();

    public TiledMap(TiledMapLoader.LoadedMapData data) {
        this.width = data.width;
        this.height = data.height;
        this.tileWidth = data.tileWidth;
        this.tileHeight = data.tileHeight;
        this.tiles = new Tile[height][width];

        Map<Integer, Image> tileImages = data.tileImages;

        // Create Tile objects
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int gid = data.tileIds[y][x];
                Image img = tileImages.get(gid);
                tiles[y][x] = createTile(gid, img);
            }
        }

        // Create Entity objects
        for (MapObject mo : data.objects) {
            Image img = tileImages.get(mo.gid);
            Entity e = createEntity(mo, img);
            if (e != null) entities.add(e);
        }
    }

    private Tile createTile(int gid, Image img) {
        if (gid <= 0 || img == null) return null;
        if (isWater(gid)) return new WaterTile(img);
        if (isSand(gid)) return new SandTile(img);
        if (isGrass(gid)) return new GrassTile(img);
        else return null;
    }

    private Entity createEntity(MapObject mo, Image img) {
        if (mo == null || img == null) return null;
        //if (isTree(mo.gid)) return new Tree(mo.x, mo.y, img);
        //if (isRock(mo.gid)) return new Rock(mo.x, mo.y, img);
        return null;
    }

    // Replace with your actual GID mapping logic
    private boolean isWater(int gid) { return gid >= 1 && gid <= 36; }
        private boolean isGrass(int gid) { return gid >= 37 && gid <= 40; }
    private boolean isSand(int gid)  { return gid >= 41 && gid <= 57; }

    private boolean isTreeWide(int gid)  { return gid >= 57 && gid <= 60; }
    private boolean isTreeTall(int gid)  { return gid >= 61 && gid <= 64; }
    private boolean isRock(int gid)  { return gid >= 65 && gid <= 65; }

    public Tile getTile(int x, int y) { return tiles[y][x]; }
    public List<Entity> getEntities() { return entities; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getTileWidth() { return tileWidth; }
    public int getTileHeight() { return tileHeight; }

    // Object placed in object layers
    public static class MapObject {
        public final int gid;
        public final double x, y, w, h;

        public MapObject(int gid, double x, double y, double w, double h) {
            this.gid = gid;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
    }
}
