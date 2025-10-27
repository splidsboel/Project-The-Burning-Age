package game.states;

import engine.core.Game;
import engine.core.GameState;
import javafx.scene.text.Text;

public class PlayState extends GameState {
    public PlayState(Game game) {
        super(game);
    }

    @Override
    public void onEnter() {
        Text info = new Text("Game Started!");
        info.setLayoutX(100);
        info.setLayoutY(100);

    }

    @Override
    public void onExit() {

    }
}
