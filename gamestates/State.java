package gamestates;

import java.awt.event.MouseEvent;
import main.GamePanel;
import ui.MenuButton;

public class State {
    public GamePanel gp;

    public State(GamePanel gp) {
        this.gp = gp;
    }
    
    public boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    public GamePanel getGame() {
        return gp;
    }
}
