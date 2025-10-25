package input;

import gamestates.Gamestate;
import java.awt.GraphicsDevice;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;
import world.actor.Player;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    Player player;
    GraphicsDevice gd;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state) {
            case PLAYING:
                gp.getPlaying().keyPressed(e);
            case MENU:
                gp.getMenu().keyPressed(e);
                break;
            default:
        }
    }
    

    @Override
    public void keyReleased(KeyEvent e) {
        switch (Gamestate.state) {
            case PLAYING:
                gp.getPlaying().keyReleased(e);
                break;
            case MENU:
                gp.getMenu().keyReleased(e);
            default:
                break;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        //int code = e.getKeyCode();
    }

}
