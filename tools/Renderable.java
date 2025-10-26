package tools;

import java.awt.Graphics2D;
import java.awt.Rectangle;


public interface Renderable {
    public void draw(Graphics2D g2, double x, double y);
    public double getBottomY();
    public Rectangle getSolidArea();
}