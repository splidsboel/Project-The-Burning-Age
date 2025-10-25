package world;

import java.io.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import main.GamePanel;
import tile.TiledMapLoader;
import world.decoration.*;
import world.decoration.grass.Grass;
import world.decoration.trees.TreeTall;
import world.decoration.trees.TreeWide;

public class TiledDecorLoader {

    // --- 1. Parse JSON file ---
    private static JsonObject parseTiledJson(String path) throws IOException {
        InputStream is = TiledMapLoader.class.getClassLoader().getResourceAsStream(path);

        if (is == null) {
            System.out.println("TMJ file not found in JAR, checking filesystem: " + path);
            File file = new File("res/" + path);
            if (file.exists()) {
                is = new FileInputStream(file);
            } else {
                throw new IOException("Error: TMJ file not found -> " + file.getAbsolutePath());
            }
        }

        try (JsonReader reader = new JsonReader(new InputStreamReader(is))) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    // --- 2. Main entry: load decor from Tiled map ---
    public static void loadDecorFromTiled(String path, DecorManager decoM, GamePanel gp) {
        try {
            JsonObject root = parseTiledJson(path);
            JsonArray layers = root.getAsJsonArray("layers");

            for (JsonElement layerElement : layers) {
                JsonObject layer = layerElement.getAsJsonObject();
                String type = layer.get("type").getAsString();
                String name = layer.get("name").getAsString();

                if (!"objectgroup".equals(type)) continue;
                if (!name.toLowerCase().contains("decor")) continue;

                JsonArray objects = layer.getAsJsonArray("objects");
                for (JsonElement objElement : objects) {
                    JsonObject obj = objElement.getAsJsonObject();

                    String objName = obj.get("name").getAsString();
                    double x = obj.get("x").getAsDouble();
                    double y = obj.get("y").getAsDouble();

                    x = x / gp.originalTileSize * gp.tileSize;
                    y = y / gp.originalTileSize * gp.tileSize;

                    createDecorObject(objName, x, y, gp, decoM);
                }
            }

        } catch (Exception e) {
            System.err.println("Error loading decor from Tiled:");
            e.printStackTrace();
        }
    }

    // --- 3. Handle decor object creation ---
    private static void createDecorObject(String objName, double x, double y, GamePanel gp, DecorManager decoM) {
        switch (objName) {
            case "grass" -> decoM.add(new Grass(x, y, gp, randomAniSpeed()));
            case "stoneSmall" -> {
                y -= 32 / gp.originalTileSize * gp.tileSize;
                decoM.add(new Stone(x, y, gp));
            }
            case "treeWide" -> {
                y -= 64 / gp.originalTileSize * gp.tileSize;
                decoM.add(new TreeWide(x, y, gp, randomAniSpeed()));
            }
            case "treeTall" -> {
                y -= 64 / gp.originalTileSize * gp.tileSize;
                decoM.add(new TreeTall(x, y, gp, randomAniSpeed()));
            }
            default -> System.out.println("Unhandled decor type: " + objName);
        }
    }

    // --- 4. Utility for randomized animation speed ---
    private static int randomAniSpeed() {
        return (int) (Math.random() * (110 - 90 + 1)) + 90;
    }
}
