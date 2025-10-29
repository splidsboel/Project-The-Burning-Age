package game.entities.decorations.trees;

import java.util.List;

import engine.core.Game;
import javafx.scene.image.Image;

public class TreeTall extends Tree {

    public TreeTall(Game g, List<Image> frames, List<Integer> durations,double x, double y, double w, double h) {
        super(g, frames, durations, x, y, w, h);
        
        setSolidArea(
            (int) (w/game.getDeviceScale()* 0.46),
            (int) (h/game.getDeviceScale()* 0.86),
            (int) (w/game.getDeviceScale()* 0.07),
            (int) (h/game.getDeviceScale()* 0.04)
        );
    }

}