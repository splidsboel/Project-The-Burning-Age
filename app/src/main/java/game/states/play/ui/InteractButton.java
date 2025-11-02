package game.states.play.ui;

import engine.input.events.Hoverable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class InteractButton {
    public static final int pixelWidth = 16;
    public static final int pixelHeight = 16;
    private static final int buttonWidth = 33 * 3;
    private static final int buttonHeight = 16 * 3;

    private Image spriteSheet;
    private Image[] frames;
    private final int frameCount = 1;
    private final int rowIndex = 1;
    private int currentFrame;
    private final double x, y;

    public InteractButton(double x, double y) {
        this.x = x;
        this.y = y;
        this.spriteSheet = new Image(getClass().getResource("/assets/ui/e_button.png").toExternalForm());

        

    }

    public void render(GraphicsContext g) {
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

}
