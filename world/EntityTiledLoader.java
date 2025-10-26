package world;

import java.io.*;
import com.google.gson.*;
import main.GamePanel;

public class EntityTiledLoader {

    public static void loadEntities(String path, EntityManager entityM, GamePanel gp) {
        try {
            // --- Load TMJ JSON file (from JAR or file system) ---
            InputStream is = EntityTiledLoader.class.getClassLoader().getResourceAsStream(path);
            if (is == null) {
                System.out.println("TMJ file not found in JAR, checking filesystem: " + path);
                is = new FileInputStream("res/" + path);
            }

            JsonObject root = JsonParser.parseReader(new InputStreamReader(is)).getAsJsonObject();
            JsonArray layers = root.getAsJsonArray("layers");

            for (JsonElement layerElement : layers) {
                JsonObject layer = layerElement.getAsJsonObject();
                if (!layer.has("objects")) continue;

                JsonArray objects = layer.getAsJsonArray("objects");
                for (JsonElement objElement : objects) {
                    JsonObject obj = objElement.getAsJsonObject();

                    // Use "name" or "type" depending on your Tiled object setup
                    String type = obj.has("name") ? obj.get("name").getAsString() :
                                  obj.has("type") ? obj.get("type").getAsString() : "";
                    double x = obj.get("x").getAsDouble();
                    double y = obj.get("y").getAsDouble();

                    // Convert from Tiled pixels to in-game tile units
                    x = x / gp.originalTileSize * gp.tileSize;
                    y = y / gp.originalTileSize * gp.tileSize;

                    Entity entity = EntityManager.create(gp, type, x, y);
                    if (entity == null) continue;

                    if (entity instanceof world.actor.Actor a) entityM.actors.add(a);
                    else if (entity instanceof world.decoration.Decoration d) entityM.decorations.add(d);
                    else if (entity instanceof world.item.Item it) entityM.items.add(it);
                }
            }

        } catch (Exception e) {
            System.err.println("Error loading entities from Tiled:");
            e.printStackTrace();
        }
    }
}
