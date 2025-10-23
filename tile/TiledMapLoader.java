package tile;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import main.GamePanel;
import world.DecorManager;
import world.decoration.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TiledMapLoader {

    public static void loadTileLayer(String path, TileManager tileManager) {
        try {
            InputStream is = TiledMapLoader.class.getClassLoader().getResourceAsStream(path);

            if (is == null) {
                System.out.println("TMJ file not found in JAR, checking filesystem: " + path);
                File file = new File("res/" + path);
                if (file.exists()) {
                    try {
                        is = new FileInputStream(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new IOException("Error: TMJ file not found -> " + file.getAbsolutePath());
                }
            }

            JsonReader jsonReader = new JsonReader(new InputStreamReader(is));
            JsonObject root = JsonParser.parseReader(jsonReader).getAsJsonObject();

    
            // Get tile layer data
            JsonArray layers = root.getAsJsonArray("layers");
            for (JsonElement layerElement : layers) {
                JsonObject layer = layerElement.getAsJsonObject();
    
                String type = layer.get("type").getAsString();
                String name = layer.get("name").getAsString();
    
                if (!"tilelayer".equals(type)) continue;
                if (!"groundLayer".equalsIgnoreCase(name)) continue;
    
                int width = layer.get("width").getAsInt();
                int height = layer.get("height").getAsInt();
                JsonArray data = layer.getAsJsonArray("data");
    
                int index = 0;
                for (int row = 0; row < height; row++) {
                    for (int col = 0; col < width; col++) {
                        int globalTileId = data.get(index).getAsInt();
                        if (globalTileId > 0) {
                            Tile tile = TilesetFactory.createTile(globalTileId);
                            tileManager.setTile(col, row, tile);
                        } 
                        index++;
                    }
                }
    
                break; // Only parse the first matching tilelayer
            }
    
        } catch (Exception e) {
            System.err.println("Error loading tile layer from TMJ:");
            e.printStackTrace();
        }
    }
    
}
