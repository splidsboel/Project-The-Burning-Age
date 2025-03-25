package world.decoration;

import java.awt.image.BufferedImage;
import world.WorldEntity;


public abstract class Decor extends WorldEntity {

    public Decor(double x, double y, BufferedImage[] frames, boolean animated) {
        super(x, y, frames, animated);
    }
}
