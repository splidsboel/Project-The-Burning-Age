package world;

import java.util.*;
import main.GamePanel;
import world.actor.*;
import world.decoration.*;
import world.decoration.trees.TreeTall;
import world.item.*;

public class EntityManager {
    private final GamePanel gp;
    public final List<Actor> actors = new ArrayList<>();
    public final List<Decoration> decorations = new ArrayList<>();
    public final List<Item> items = new ArrayList<>();

    public EntityManager(GamePanel gp) {
        this.gp = gp;
    }

    public static Entity create(GamePanel gp, String type, double x, double y) {
        return switch (type.toLowerCase()) {
            case "tree_tall" -> new TreeTall(gp, x, y);
            default -> null;
        };
    }

    public void update() {
        actors.forEach(Entity::update);
        decorations.forEach(Entity::update);
        items.forEach(Entity::update);
    }

    public void draw(java.awt.Graphics2D g2, double camX, double camY) {
        List<Entity> all = new ArrayList<>();
        all.addAll(actors);
        all.addAll(decorations);
        all.addAll(items);
        all.sort(Comparator.comparingDouble(Entity::getBottomY));
        all.forEach(e -> e.draw(g2, camX, camY));
    }
}
