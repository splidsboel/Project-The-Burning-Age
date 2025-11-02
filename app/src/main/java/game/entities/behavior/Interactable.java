package game.entities.behavior;

import game.entities.Entity;
import javafx.geometry.Rectangle2D;

public interface Interactable {
    Rectangle2D getInteractArea();
    void onInteract(Entity other);
    boolean canInteract();
}