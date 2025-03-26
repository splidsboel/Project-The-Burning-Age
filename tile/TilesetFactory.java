package tile;

import java.util.Map;
import java.util.TreeMap;

public class TilesetFactory {

    private static final TreeMap<Integer, Tileset> tilesets = new TreeMap<>();

    public static void registerTileset(int firstgid, Tileset tileset) {
        tilesets.put(firstgid, tileset);
    }

    public static Tile createTile(int globalId) {
        for (Map.Entry<Integer, Tileset> entry : tilesets.descendingMap().entrySet()) {
            int firstgid = entry.getKey();
            Tileset tileset = entry.getValue();

            if (tileset.containsGlobalId(globalId, firstgid)) {
                return tileset.createTile(globalId, firstgid);
            } else {
                //System.out.println("GlobalID error. GlobalID: " + globalId + ", firstgid: " + firstgid);
            }
        }
        return null;
    }
}
