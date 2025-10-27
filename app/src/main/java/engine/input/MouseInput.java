package engine.input;

import javafx.scene.input.MouseEvent;

public class MouseInput {
    private double mouseX;
    private double mouseY;
    private boolean mousePressed;

    public void onMouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void onMousePressed(MouseEvent e) {
        mousePressed = true;
    }

    public void onMouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    public double getMouseX() { return mouseX; }
    public double getMouseY() { return mouseY; }
    public boolean isMousePressed() { return mousePressed; }
}
