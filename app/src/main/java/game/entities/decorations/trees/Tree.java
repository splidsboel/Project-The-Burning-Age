package game.entities.decorations.trees;

import java.util.List;

import engine.core.Game;
import game.entities.Decoration;
import javafx.scene.image.Image;

public class Tree extends Decoration {
    public Tree(Game g, List<Image> frames, List<Integer> durations,
                    double x, double y, double w, double h) {
        super(g, frames, durations, x, y, w, h);
    }
}
