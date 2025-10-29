package game.entities.behavior;

import javafx.geometry.Rectangle2D;

public interface Collidable {
    Rectangle2D getHitbox();
    Rectangle2D getSolidArea();
    boolean isSolid();
    default void onCollide(Collidable other) {}
}
