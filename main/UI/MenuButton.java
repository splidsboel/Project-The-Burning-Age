package main.UI;

import static tools.Constants.UI.Buttons.*;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import tools.UtilityTool;

public class MenuButton {
    private int x, y, rowIndex, index;
    private int xOffsetCenter = b_width / 2;
    private Gamestate state;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed, mouseReleased;
    private Rectangle bounds;

    public MenuButton (int x, int y, int rowIndex, Gamestate state) {
        this.x = x;
        this.y = y;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        initializeBounds();
    }

    public void initializeBounds() {
        bounds = new Rectangle(x - xOffsetCenter, y, b_width, b_height);
    }
        
    private void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage temp = UtilityTool.importImg("/res/images/UI/buttons/menu_buttons.png");
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i * b_width_default, rowIndex * b_height_default, b_width_default, b_height_default);
        }
    }

    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
         if (mousePressed) {
            index = 2;
        } 
        if (mouseReleased) {
            index = 0;
        }
    }
    
    public void draw(Graphics2D g2) {
        g2.drawImage(imgs[index], x - xOffsetCenter, y, b_width, b_height, null);
    }


    //GETTERS AND SETTERS
    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }


    public boolean isMouseReleased() {
        return mouseReleased;
    }

    public void setMouseReleased(boolean mouseReleased) {
        this.mouseReleased = mouseReleased;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void applyGamestate() {
        Gamestate.state = state;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
        mouseReleased = false;
    }

    public void resetImg() {

    }

    
}
