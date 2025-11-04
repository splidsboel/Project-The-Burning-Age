package game.entities.behavior;

import javafx.geometry.Rectangle2D;

public interface Hittable {
    Rectangle2D getHitbox();
    void onHit(Hittable other);
}
