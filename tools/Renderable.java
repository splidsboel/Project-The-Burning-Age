package tools;

import java.awt.Graphics2D;


public interface Renderable {
    public double getBottomY();
    public void draw(Graphics2D g2, double x, double y);
}