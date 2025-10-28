package engine.map;

import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class TiledMap {
    public int width;
    public int height;
    public int tilewidth;
    public int tileheight;
    public Layer[] layers;
    public Tileset[] tilesets;

    public static class Layer {
        public String name;
        public String type;
        public int[] data; // for tile layers
        public MapObject[] objects; // for object layers
    }

    public static class Tileset {
        public int firstgid;
        public String image;
        public int imagewidth;
        public int imageheight;
    }

    public static class MapObject {
        public int gid;
        public String name;
        public String type;
        public double x;
        public double y;
        public double width;
        public double height;
    }

    public static TiledMap load(String path) throws IOException {
        try (FileReader reader = new FileReader(path)) {
            return new Gson().fromJson(reader, TiledMap.class);
        }
    }

}
