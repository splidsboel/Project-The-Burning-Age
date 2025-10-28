package game.states;

import engine.core.Game;
import engine.core.GameState;
import game.states.menu.MainMenu;
import javafx.scene.canvas.GraphicsContext;

public class MenuState extends GameState {
    private MainMenu state;

    public MenuState(Game game) {
        super(game);
        System.out.println("MenuState initialized.");
    }

    @Override
    public void update(double delta) {
        state.update(delta);
    }

    @Override
    public void render(GraphicsContext gc) {
        state.render(gc);
    }

    @Override
    public void onEnter() {
    state = new MainMenu(game); 
    }

    @Override
    public void onExit() {
    }
}
