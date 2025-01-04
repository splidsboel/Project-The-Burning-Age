package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gamestates.Gamestate;
import main.GamePanel;

public class MouseHandler implements MouseListener, MouseMotionListener {
    GamePanel gp;
    public int mouseX;
    public int mouseY;


    public MouseHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        switch (Gamestate.state) {
            case PLAYING:
                gp.getPlaying().mouseClicked(e);
                break;
            case MENU:
                gp.getMenu().mouseClicked(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        switch (Gamestate.state) {
            case PLAYING:
                gp.getPlaying().mouseMoved(e);
                break;
            case MENU:
                gp.getMenu().mouseMoved(e);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        switch (Gamestate.state) {
            case PLAYING:
                gp.getPlaying().mousePressed(e);
                break;
            case MENU:
                gp.getMenu().mousePressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        switch (Gamestate.state) {
            case PLAYING:
                gp.getPlaying().mouseReleased(e);
                break;
            case MENU:
                gp.getMenu().mouseReleased(e);
                break;
            default:
                break;
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) { 

    }
    @Override
    public void mouseExited(MouseEvent e) {
   
    }
    @Override
    public void mouseDragged(MouseEvent e) {

    }
}
