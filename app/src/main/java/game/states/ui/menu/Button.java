package game.states.ui.menu;

import engine.input.events.Hoverable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Button implements Hoverable {
    private static final int buttonWidth = 33;
    private static final int buttonHeight = 16;

    private final Image[] frames;
    private int currentFrame;
    private final double x, y;

    public Button(Image spriteSheet, int rowIndex, int frameCount, double x, double y) {
        this.x = x;
        this.y = y;
        frames = new Image[frameCount];
        PixelReader reader = spriteSheet.getPixelReader();
        for (int i = 0; i < frameCount; i++) { 
            frames[i] = new WritableImage(reader,
                    i * buttonWidth,
                    rowIndex * buttonHeight,
                    buttonWidth,
                    buttonHeight);
        }
    }

    public void draw(GraphicsContext g) {
        g.drawImage(frames[currentFrame], x - (buttonWidth / 2), y, buttonWidth * 3, buttonHeight * 3);
    }

    public void setFrame(int index) {
        currentFrame = index;
    }

    @Override
    public boolean isHovered(double mouseX, double mouseY) {
        double width = buttonWidth * 3;
        double height = buttonHeight * 3;
        double left = x - (buttonWidth / 2);
        double top = y;
        return mouseX >= left && mouseX <= left + width &&
           mouseY >= top && mouseY <= top + height;
    }
}
