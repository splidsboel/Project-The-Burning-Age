package game.states.ui.menu;

import engine.core.Game;
import engine.input.MouseInput;
import game.states.MenuState;
import game.states.PlayState;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class MainMenu extends MenuState{
    private final Game game;
    private final double canvasWidth;
    private final double canvasHeight;
    private final Image spriteSheet;

    private Button play;
    private Button options;
    private Button quit;

    public MainMenu(Game game) {
        super(game);
        this.game = game;
        this.spriteSheet = new Image(getClass().getResource("/assets/ui/menu_buttons.png").toExternalForm());
        this.canvasWidth = game.getCanvas().getWidth();
        this.canvasHeight = game.getCanvas().getHeight();
        System.out.println("Menu initialized");

        loadButtons();
    }

    // Called from MenuState.update()
    public void update(double delta) {
        // Example: handle animations, hover states, etc.
        // If nothing dynamic yet, leave empty.
        handleMouse();
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

    private void handleMouse() {
        MouseInput mouse = game.getMouseInput();
        double mx = mouse.getMouseX();
        double my = mouse.getMouseY();

        // --- PLAY ---
        if (play.isHovered(mx, my)) {
            play.setFrame(mouse.isMousePressed() ? 2 : 1);
            if (mouse.consumeRelease()) {
                play.setFrame(1);
                game.changeState(new PlayState(game)); 
                System.out.println("Play clicked!");
            }
        } else {
            play.setFrame(0);
        }

        // --- OPTIONS ---
        if (options.isHovered(mx, my)) {
            options.setFrame(mouse.isMousePressed() ? 2 : 1);
            if (mouse.consumeRelease()) {
                options.setFrame(1);
                // TODO: add options logic later
            }
        } else {
            options.setFrame(0);
        }

        // --- QUIT ---
        if (quit.isHovered(mx, my)) {
            quit.setFrame(mouse.isMousePressed() ? 2 : 1);
            if (mouse.consumeRelease()) {
                quit.setFrame(1);
                Platform.runLater(() -> {
                    game.stopRunning();
                    game.getStage().close();
                    System.out.println("Quit clicked!");
                });
            }
        } else {
            quit.setFrame(0);
        }
    }


    private void drawButtons(GraphicsContext g) {
        play.draw(g);
        options.draw(g);
        quit.draw(g);
    }
}
