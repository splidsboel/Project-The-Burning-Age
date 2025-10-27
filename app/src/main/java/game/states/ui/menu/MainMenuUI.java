package game.states.ui.menu;

import engine.core.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class MainMenuUI {

    private final double canvasWidth;
    private final double canvasHeight;
    private final Image spriteSheet;

    private Button play;
    private Button options;
    private Button quit;

    public interface MenuAction {
        void play();
        void options();
        void quit();
    }

    public MainMenuUI(Game game) {
        this.spriteSheet = new Image(getClass().getResource("/assets/ui/menu_buttons.png").toExternalForm());
        this.canvasWidth = game.getCanvas().getWidth();
        this.canvasHeight = game.getCanvas().getHeight();

        loadButtons();
    }

    // Called from MenuState.update()
    public void update(double delta) {
        // Example: handle animations, hover states, etc.
        // If nothing dynamic yet, leave empty.
    }

    // Called from MenuState.render(gc)
    public void render(GraphicsContext g) {
        g.setImageSmoothing(false);
         g.clearRect(0, 0, canvasWidth, canvasHeight);
        drawButtons(g);
    }

    private void loadButtons() {
        play = new Button(spriteSheet, 0, 3, (int)(canvasWidth / 2), 700);     // x, y example positions
        options = new Button(spriteSheet, 1, 3, (int)(canvasWidth / 2), 800);
        quit = new Button(spriteSheet, 2, 3,(int)(canvasWidth / 2), 900);

    }

    private void drawButtons(GraphicsContext g) {
        play.draw(g);
        options.draw(g);
        quit.draw(g);
    }
}
