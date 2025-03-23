package gamestates;

import static tools.Constants.UI.dragonImages.img_width;


import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import main.GamePanel;
import main.UI.MenuButton;
import main.UI.MenuImage;

public class Menu extends State implements Statemethods {
    private MenuButton[] buttons = new MenuButton[3];
    private MenuImage img;
    private int aniTick = 0, aniSpeed =60, aniIndex;
    boolean aniActive = true;


    public Menu(GamePanel gp) {
        super(gp);
        loadButtons();
        loadImages();
        
    }

    public void loadButtons() {
        buttons[0] = new MenuButton(((gp.screenWidth / 2)/2), 240*gp.scale, 0, Gamestate.PLAYING);
        buttons[1] = new MenuButton(((gp.screenWidth / 2)/2),275*gp.scale , 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(((gp.screenWidth / 2)/2),310*gp.scale , 2, Gamestate.QUIT);
    }

    public void loadImages() {
        img = new MenuImage(((gp.screenWidth/2)/2) - ((img_width * gp.scale) / 2),0);
    }

    @Override
    public void update() {
        for (MenuButton mb : buttons) {
            mb.update();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        updateAnimationTick();

        img.draw(g2, aniIndex, gp.scale);
        
        for (MenuButton mb : buttons) {
            mb.draw(g2);
        }
    }

    
    public void updateAnimationTick() {
        if (aniActive) {
            aniTick++;
            int decreasedSpeed = 0;
            if (aniTick >= aniSpeed + decreasedSpeed) {
                decreasedSpeed += 1000;
                aniTick = 0;
                aniIndex++;
                if (aniIndex >= 2) {
                    aniActive = false;
                }
            }
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
    public void mouseWheelMoved(MouseWheelEvent e) {

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
