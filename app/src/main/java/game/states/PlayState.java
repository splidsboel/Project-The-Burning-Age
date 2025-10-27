package game.states;

import engine.core.Game;
import engine.core.GameState;
import game.states.play.World;
import javafx.scene.canvas.GraphicsContext;

public class PlayState extends GameState {
    private World state;

    public PlayState(Game game) {
        super(game);
        System.out.println("PlayState initialized");
    }

    @Override
    public void update(double delta) {
        state.update(delta);
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
}
