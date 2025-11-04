package game.entities.behavior;

import javafx.scene.canvas.GraphicsContext;

public interface Renderable {
    double getBottomY();  
    void render(GraphicsContext g);
}