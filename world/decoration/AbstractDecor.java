package world.decoration;

import java.awt.image.BufferedImage;
import world.WorldEntity;


public abstract class AbstractDecor extends WorldEntity {

    protected boolean hasShadow = false;
    protected float alpha = 1.0f; // transparency for effects

    public AbstractDecor(double x, double y, BufferedImage[] frames, boolean animated) {
        super(x, y, frames, animated);
    }

    public void setShadow(boolean hasShadow) {
        this.hasShadow = hasShadow;
    }

    public boolean hasShadow() {
        return hasShadow;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getAlpha() {
        return alpha;
    }
}
