package game.states.menu.ui;

import engine.input.events.Hoverable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Button implements Hoverable {
    public static final int pixelWidth = 33;
    public static final int pixelHeight = 16;
    private static final int buttonWidth = 33 * 3;
    private static final int buttonHeight = 16 * 3;

    private Image spriteSheet;
    private Image[] frames;
    private final int frameCount;
    private final int rowIndex;
    private int currentFrame;
    private final double x, y;

    public Button(Image spriteSheet, int rowIndex, int frameCount, double x, double y) {
        this.x = x;
        this.y = y;
        this.spriteSheet = spriteSheet;
        this.rowIndex = rowIndex;
        this.frameCount = frameCount;
        
        loadAnimations();
    }

    public void draw(GraphicsContext g) {
        g.drawImage(frames[currentFrame], x - (buttonWidth / 2), y, buttonWidth, buttonHeight);
    }

    private void loadAnimations() {
        frames = new Image[frameCount];
        PixelReader reader = spriteSheet.getPixelReader();
        for (int i = 0; i < frameCount; i++) { 
            frames[i] = new WritableImage(reader,
                    i * pixelWidth,
                    rowIndex * pixelHeight,
                    pixelWidth,
                    pixelHeight);
        }
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
