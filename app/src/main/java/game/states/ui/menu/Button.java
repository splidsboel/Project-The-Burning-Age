package game.states.ui.menu;

import engine.input.events.Hoverable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Button implements Hoverable {
    public static final int bWidthDefault = 33;
    public static final int bHeightDefault = 16;
    private static final int buttonWidth = 33 * 2;
    private static final int buttonHeight = 16 * 2;

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
                    i * bWidthDefault,
                    rowIndex * bHeightDefault,
                    bWidthDefault,
                    bHeightDefault);
        }
    }

    public void draw(GraphicsContext g) {
        g.drawImage(frames[currentFrame], x - (buttonWidth / 2), y, buttonWidth, buttonHeight);
    }

    public void setFrame(int index) {
        currentFrame = index;
    }
    

    @Override
    public boolean isHovered(double mouseX, double mouseY) {
        double width = buttonWidth;
        double height = buttonHeight;
        double left = x - (buttonWidth / 2);
        double top = y;
        return mouseX >= left && mouseX <= left + width &&
           mouseY >= top && mouseY <= top + height;
    }

}
