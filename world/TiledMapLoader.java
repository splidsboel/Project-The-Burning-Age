package world;

import com.google.gson.*;

import main.GamePanel;
import world.decoration.Grass;

import java.awt.image.BufferedImage;
import java.io.InputStreamReader;

public class TiledMapLoader {


    public static void loadDecorFromTiled(String path, DecorManager decoM,
                                          BufferedImage[] grassFrames, GamePanel gp) {
        try {
            // Step 1: Load and parse JSON
            InputStreamReader reader = new InputStreamReader(TiledMapLoader.class.getResourceAsStream(path));
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

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

                int counter = 0;
                for (JsonElement objElement : objects) {
                    JsonObject obj = objElement.getAsJsonObject();
                    
                    String objName = obj.get("name").getAsString();
                    double x = obj.get("x").getAsDouble();
                    double y = obj.get("y").getAsDouble();

                    x *= gp.scale;
                    y *= gp.scale;

                    
                    System.out.println("TileMapLoader. " + "object: " + name + " coordinate: " + x + "," + y + ". count:"+counter++);

                    switch (objName.toLowerCase()) {
                        case "grass" -> {
                            decoM.add(new Grass(x, y, grassFrames));
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
