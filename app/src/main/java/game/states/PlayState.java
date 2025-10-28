package game.states;

import engine.core.Game;
import engine.core.GameState;
import game.states.play.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class PlayState extends GameState {
    private World state;

    public PlayState(Game game) {
        super(game);
        System.out.println("PlayState initialized.");
    }

    @Override
    public void update(double delta) {
        state.update(delta);
        handleInput();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, game.getCanvas().getWidth(), game.getCanvas().getHeight()); // clear canvas on state initialization
        if (state != null) {
            state.render(gc);
        }
    }

    @Override
    public void onEnter() {
        state = new World(game); 
    }

    @Override
    public void onExit() {
    }

    private void handleInput() {
        if (game.getKeyboardInput().isKeyPressed(KeyCode.ESCAPE)) {
            game.changeState(new MenuState(game));
        }
    }
}
