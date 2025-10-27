package game.states;

import engine.core.Game;
import engine.core.GameState;
import game.states.ui.menu.MainMenuUI;
import javafx.scene.canvas.GraphicsContext;

public class MenuState extends GameState {
    private MainMenuUI ui;

    public MenuState(Game game) {
        super(game);
    }

    @Override
    public void update(double delta) {
        ui.update(delta);
    }

    @Override
    public void render(GraphicsContext gc) {
        ui.render(gc);
    }

    @Override
    public void onEnter() {
    ui = new MainMenuUI(game); 
    }

    @Override
    public void onExit() {
    }
}
