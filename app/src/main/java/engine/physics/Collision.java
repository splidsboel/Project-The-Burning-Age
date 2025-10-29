package engine.physics;

import engine.map.TiledMap;
import game.entities.Entity;
import game.entities.behavior.Collidable;
import javafx.geometry.Rectangle2D;
import java.util.List;

public class Collision {
    public static boolean checkCollision(Collidable a, Collidable b) {
        return a.getSolidArea().intersects(b.getSolidArea());
    }

    public static void handleCollisions(List<? extends Collidable> objects) {
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                var a = objects.get(i);
                var b = objects.get(j);
                if (checkCollision(a, b)) {
                    a.onCollide(b);
                    b.onCollide(a);
                }
            }
        }
    }

    public boolean willCollideWithSolid(TiledMap map, Collidable mover, double dx, double dy, int tileSize, List<Entity> entities) {
        Rectangle2D next = new Rectangle2D(mover.getSolidArea().getMinX() + dx, mover.getSolidArea().getMinY() + dy, mover.getSolidArea().getWidth(), mover.getSolidArea().getHeight());

        // --- Tile collision ---
        // int left = (int)(next.getMinX() / tileSize);
        // int right = (int)(next.getMaxX() / tileSize);
        // int top = (int)(next.getMinY() / tileSize);
        // int bottom = (int)(next.getMaxY() / tileSize);

        // for (int y = top; y <= bottom; y++) {
        //     for (int x = left; x <= right; x++) {
        //         Tile tile = map.getTile(x, y);
        //         if (tile != null && tile.isSolid()) return true;
        //     }
        // }

        // --- Entity collision (trees etc.) ---
        for (Entity e : entities) {
            if (e == mover) continue;
            if (e instanceof Collidable c && c.isSolid() && next.intersects(c.getSolidArea()))
                return true;
        }
        return false;
    }
}
