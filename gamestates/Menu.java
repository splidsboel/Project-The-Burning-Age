package gamestates;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import main.GamePanel;
import main.UI.MenuButton;

public class Menu extends State implements Statemethods {
    private MenuButton[] buttons = new MenuButton[3];

    public Menu(GamePanel gp) {
        super(gp);
        loadButtons();
        
    }

    public void loadButtons() {
        buttons[0] = new MenuButton(gp.screenWidth / 2, (int)(300*GamePanel.scale), 0, Gamestate.PLAYING);
        buttons[1] = new MenuButton(gp.screenWidth / 2, (int)(330*GamePanel.scale), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(gp.screenWidth / 2, (int)(375*GamePanel.scale), 2, Gamestate.QUIT);
    }

    @Override
    public void update() {
        for (MenuButton mb : buttons) {
            mb.update();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        for (MenuButton mb : buttons) {
            mb.draw(g2);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
                break;
            }
            resetButtons();
        }  
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                if (mb.isMousePressed()) {
                    mb.setMouseReleased(true);
                    mb.applyGamestate();
                    resetButtons();   
                    break;
                } 
            }
            
        }
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons) {
            mb.setMouseOver(false);
        }
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
   
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Gamestate.state = Gamestate.PLAYING;
        }
    }

    public void resetButtons() {
        for (MenuButton mb : buttons) {
            mb.resetBools();
        }
    }
    
}
