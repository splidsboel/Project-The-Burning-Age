package game.entities.decorations.trees;

import engine.core.Game;
import game.entities.Decoration;
import javafx.scene.image.Image;

public abstract class Tree extends Decoration {

    public Tree(Game game, Image sprite, double x, double y, double width, double height) {
        super(game, sprite, x, y, width, height);
    }
    
    @Override
    public void update(double delta) {

    }

}
