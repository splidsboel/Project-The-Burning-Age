package game.states.play;

import engine.core.Game;
import game.states.PlayState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;

public class World extends PlayState {
    private final double canvasWidth;
    private final double canvasHeight;

    private double x,y;

    public World(Game game) {
        super(game);
        this.canvasWidth = game.getCanvas().getWidth();
        this.canvasHeight = game.getCanvas().getHeight();
        System.out.println("World initialized");
    }

    @Override
    // Called from MenuState.update()
    public void update(double delta) {
        // Example: handle animations, hover states, etc.
        // If nothing dynamic yet, leave empty.
    }

    @Override
    // Called from MenuState.render(gc)
    public void render(GraphicsContext g) {

    }

}
