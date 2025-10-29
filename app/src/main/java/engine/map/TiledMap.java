package engine.map;

import game.entities.*;
import game.entities.decorations.trees.TreeWide;
import game.entities.decorations.rocks.RockMedium;
import game.entities.decorations.trees.TreeTall;
import game.tiles.*;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import engine.core.Game;

/**
 * Converts LoadedMapData into instantiated Tile and Entity objects.
 */
public class TiledMap {
    private final Game game;

    private final int mapWidth;
    private final int mapHeight;
    private final int tileWidth;
    private final int tileHeight;
    private final Tile[][] tiles;
    private final List<Entity> entities = new ArrayList<>();

    public TiledMap(Game game, TiledMapLoader.LoadedMapData data) {
        this.game = game;
        this.mapWidth = data.width;
        this.mapHeight = data.height;
        this.tileWidth = data.tileWidth;
        this.tileHeight = data.tileHeight;
        this.tiles = new Tile[mapHeight][mapWidth];

        Map<Integer, Image> tileImages = data.tileImages;

        // Create Tile objects
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                int gid = data.tileIds[y][x];
                Image img = tileImages.get(gid);
                tiles[y][x] = createTile(gid, img);
            }
        }

        // Create Entity objects
        for (MapObject mo : data.objects) {
            Image img = tileImages.get(mo.gid);
            Entity e = createEntity(mo, img, data);
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

    private Entity createEntity(MapObject mo, Image img, TiledMapLoader.LoadedMapData data) {
        if (mo == null || img == null) return null;

            // --- scale world coordinates based on actual screen tile size ---
            double scale = game.getTileSize() / tileWidth;

            double worldX = mo.x * scale;
            double worldY = (mo.y - mo.h) * scale;
            double width  = mo.w * scale;
            double height = mo.h * scale;

        if (isTreeWide(mo.gid)) {
            List<Image> frames = data.animatedTiles.get(mo.gid);
            List<Integer> durs  = data.animatedDurations.get(mo.gid);

            // fallback for static tiles
            if (frames == null || frames.isEmpty()) {
                frames = List.of(img);
                durs   = List.of(Integer.MAX_VALUE);
            }

            return new TreeWide(game, frames, durs, worldX, worldY, width, height);
        }
        if (isTreeTall(mo.gid)) {
            List<Image> frames = data.animatedTiles.get(mo.gid);
            List<Integer> durs  = data.animatedDurations.get(mo.gid);

            // fallback for static tiles
            if (frames == null || frames.isEmpty()) {
                frames = List.of(img);
                durs   = List.of(Integer.MAX_VALUE);
            }

            return new TreeTall(game, frames, durs, worldX, worldY, width, height);
        }
        if (isRock(mo.gid)) {
             List<Image> frames = data.animatedTiles.get(mo.gid);
             List<Integer> durs  = data.animatedDurations.get(mo.gid);

              //fallback for static tiles
            if (frames == null || frames.isEmpty()) {
                 frames = List.of(img);
                 durs   = List.of(Integer.MAX_VALUE);
            }

            return new RockMedium(game, frames, durs, worldX, worldY, width, height);
        }
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
    public int getMapWidth() { return mapWidth; }
    public int getMapHeight() { return mapHeight; }
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
