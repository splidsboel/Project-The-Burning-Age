package main.UI;

import static tools.Constants.UI.Images.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import tools.UtilityTool;

public class MenuImage {
    private int x, y;
    private int xOffsetCenter = img_width / 2;
    private BufferedImage[] imgs;


    public MenuImage(int x, int y) {
        this.x = x;
        this.y = y;
        loadImgs();

    }


    private void loadImgs() {   
        imgs = new BufferedImage[3];
        BufferedImage temp = UtilityTool.importImg("/res/images/UI/logo/dragon_logo.png");
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i * img_width_default, 0, img_width_default, img_height_default);
        }
    }

    public void draw(Graphics2D g2, int aniIndex) {
        g2.drawImage(imgs[aniIndex], x - xOffsetCenter, y, img_width, img_height, null);
    }

}