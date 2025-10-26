package world;

import java.awt.Rectangle;
import java.util.*;
import main.GamePanel;
import tools.Renderable;
import world.actor.*;
import world.decoration.*;
import world.decoration.rocks.*;
import world.decoration.trees.*;
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

        int pixels = 32;
        double offsetY = 0;

        switch (type) {
            case "stoneSmall" -> {
                System.out.println("Stone!");
                pixels = 32;
                offsetY = pixels / gp.originalTileSize * gp.tileSize; // 32px tall
                return new Stone(gp, x, y - offsetY);
            }
            case "treeTall" -> {
                pixels = 64;
                offsetY = pixels / gp.originalTileSize * gp.tileSize; // 64px tall
                return new TreeTall(gp, x, y - offsetY);
            }
            case "treeWide" -> {
                pixels = 64;
                offsetY = pixels / gp.originalTileSize * gp.tileSize; // 64px tall
                return new TreeWide(gp, x, y - offsetY);
            }
            default -> {
                System.out.println("Unknown entity type: " + type);
                return null;
            }
        }
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

    public List<Renderable> getVisibleEntities(double camX, double camY, int viewW, int viewH) {
        List<Renderable> visible = new ArrayList<>();
        double margin = gp.tileSize * 2;
        double minX = camX - margin, maxX = camX + viewW + margin;
        double minY = camY - margin, maxY = camY + viewH + margin;

        for (Decoration d : decorations) {
            if (d.x + d.solidArea.width > minX && d.x < maxX && d.y + d.solidArea.height > minY && d.y < maxY)
                visible.add(d);
        }
        // add actors, items later if needed
        return visible;
    }

    public List<Rectangle> getDecorSolidAreaList(double camX, double camY, int viewW, int viewH) {
        List<Rectangle> result = new ArrayList<>();
        double margin = gp.tileSize * 2; // small buffer around view
        double minX = camX - margin;
        double maxX = camX + viewW + margin;
        double minY = camY - margin;
        double maxY = camY + viewH + margin;

        for (Decoration d : decorations) {
            Rectangle r = d.solidArea;
            if (r.x + r.width > minX && r.x < maxX && r.y + r.height > minY && r.y < maxY) {
                result.add(r);
            }
        }
        return result;
    }



}
