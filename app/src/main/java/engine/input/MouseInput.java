package engine.input;

import javafx.scene.input.MouseEvent;

public class MouseInput {
    private double mouseX;
    private double mouseY;
    private boolean mousePressed;
    private boolean mouseReleased;

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
