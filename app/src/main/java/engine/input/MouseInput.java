package engine.input;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class MouseInput {
    private double mouseX;
    private double mouseY;
    private boolean mousePressed;
    private boolean mouseReleased;
    private double scrollDeltaY; // scroll wheel delta

    public void onMouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void onMousePressed(MouseEvent e) {
        mousePressed = true;
        mouseReleased = false;
    }

    public void onMouseReleased(MouseEvent e) {
        mouseReleased = true;
        mousePressed = false;
    }

    public void onScroll(ScrollEvent e) {
        scrollDeltaY = e.getDeltaY();
    }

    public double consumeScrollDeltaY() {
        double val = scrollDeltaY;
        scrollDeltaY = 0;
        return val;
    }

    public boolean consumePressed() {
        if (mouseReleased) {
            mouseReleased = false;
            return true;
        }
        return false;
    }

    public boolean consumeRelease() {
    if (mouseReleased) {
        mouseReleased = false;
        return true;
    }
    return false;
}



    public double getMouseX() { return mouseX; }
    public double getMouseY() { return mouseY; }
    public boolean isMousePressed() { return mousePressed; }
    public boolean isMouseReleased() { return mouseReleased; }
}
