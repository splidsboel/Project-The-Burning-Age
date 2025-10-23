package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import main.GamePanel;
import tile.TiledMapLoader;
import world.decoration.Grass;
import world.decoration.Tree;

public class TiledDecorLoader {

    public static void loadDecorFromTiled(String path, DecorManager decoM, BufferedImage[] frames, GamePanel gp) {
        try {
            // Step 1: Load and parse JSON
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
           
            // Step 2: Get layers array
            JsonArray layers = root.getAsJsonArray("layers");

            // Step 3: Loop through each layer
            for (JsonElement layerElement : layers) {
                JsonObject layer = layerElement.getAsJsonObject();

                String type = layer.get("type").getAsString();
                String name = layer.get("name").getAsString();

                if (!"objectgroup".equals(type)) continue;
                if (!name.toLowerCase().contains("decor")) continue;

                // Step 4: Parse decor objects
                JsonArray objects = layer.getAsJsonArray("objects");

                //int counter = 0; 
                for (JsonElement objElement : objects) {
                    JsonObject obj = objElement.getAsJsonObject();
                    
                    String objName = obj.get("name").getAsString();
                    double x = obj.get("x").getAsDouble();
                    double y = obj.get("y").getAsDouble();

                    x = x / gp.originalTileSize * gp.tileSize;
                    y = y / gp.originalTileSize * gp.tileSize; 
                    
                    //System.out.println("TileMapLoader. " + "object: " + name + " coordinate: " + x + "," + y + ". count:"+counter++);

                    switch (objName.toLowerCase()) {  // <----- Det er her det sker!
                        case "grass" -> {
                            decoM.add(new Grass(x, y, gp));
                        }
                        case "tree" -> {
                            y = y - 64 / gp.originalTileSize * gp.tileSize; //y er rettet da sprite er 64x64
                            decoM.add(new Tree(x, y, gp));
                            decoM.add(new Tree(x, y, gp));
                        }
                        default -> System.out.println("Unhandled decor type: " + objName);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error loading decor from Tiled:");
            e.printStackTrace();
        }
    }
}
