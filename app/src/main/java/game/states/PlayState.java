package game.states;

import engine.core.Game;
import engine.core.GameState;
import game.states.play.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class PlayState extends GameState {
    private World world;

    public PlayState(Game game) {
        super(game);
        System.out.println("PlayState initialized.");
    }

    @Override
    public void load() {
        // Load the world once when this state starts
        world = new World(game);
    }

    @Override
    public void update(double delta) {
        handleInput();
        if (world != null) {
            world.update(delta);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, game.getCanvas().getWidth(), game.getCanvas().getHeight());
        if (world != null) {
            world.render(gc);
        }
    }

    @Override
    public void unload() {
        world = null;
    }

    private void handleInput() {
        if (game.getKeyboardInput().isKeyPressed(KeyCode.ESCAPE)) {
            game.changeState(new MenuState(game));
        }
        double scroll = game.getMouseInput().consumeScrollDeltaY();
        if (scroll != 0) {
            if (scroll > 0) world.getCamera().zoomIn(0.1);
            else world.getCamera().zoomOut(0.1);
        }
    }
}
