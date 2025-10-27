package engine.core;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameState {
    protected Game game;

    public GameState(Game game) {
        this.game = game;

    }

    public abstract void onEnter();
    public abstract void onExit();
    public void update(double delta){};
    public void render(GraphicsContext gc){};
}
