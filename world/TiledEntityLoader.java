package world;

import java.io.FileReader;
import org.json.*;
import main.GamePanel;

public class TiledEntityLoader {

    public static void loadEntities(String path, EntityManager manager, GamePanel gp) {
        try {
            JSONObject root = new JSONObject(new JSONTokener(new FileReader(path)));
            JSONArray layers = root.getJSONArray("layers");

            for (int i = 0; i < layers.length(); i++) {
                JSONObject layer = layers.getJSONObject(i);
                if (!layer.has("objects")) continue;

                JSONArray objects = layer.getJSONArray("objects");
                for (int j = 0; j < objects.length(); j++) {
                    JSONObject obj = objects.getJSONObject(j);
                    String type = obj.optString("type", "");
                    double x = obj.getDouble("x");
                    double y = obj.getDouble("y");

                    Entity entity = EntityManager.create(gp, type, x, y);
                    if (entity == null) continue;

                    if (entity instanceof world.actor.Actor a) manager.actors.add(a);
                    else if (entity instanceof world.decoration.Decoration d) manager.decorations.add(d);
                    else if (entity instanceof world.item.Item it) manager.items.add(it);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
