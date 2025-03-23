package world;

import com.google.gson.*;
import world.decoration.Grass;

import java.awt.image.BufferedImage;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TiledMapLoader {

    public static void loadDecorFromTiled(String path, DecorManager manager,
                                          BufferedImage[] grassFrames,
                                          BufferedImage[] rockFrames) {
        try {
            JsonObject root = JsonParser.parseReader(
                new InputStreamReader(TiledMapLoader.class.getResourceAsStream(path))
            ).getAsJsonObject();

            JsonArray layers = root.getAsJsonArray("layers");

            for (JsonElement layerElement : layers) {
                JsonObject layer = layerElement.getAsJsonObject();

                if (!"objectgroup".equals(layer.get("type").getAsString())) continue;
                if (!"DecorLayer".equals(layer.get("name").getAsString())) continue;

                JsonArray objects = layer.getAsJsonArray("objects");

                for (JsonElement objElement : objects) {
                    JsonObject obj = objElement.getAsJsonObject();

                    String name = obj.get("name").getAsString();
                    double x = obj.get("x").getAsDouble();
                    double y = obj.get("y").getAsDouble();

                    switch (name.toLowerCase()) {
                        case "grass" -> manager.add(new Grass(x, y, grassFrames));

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
