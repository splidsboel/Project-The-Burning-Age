package ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import tools.UtilityTool;

public class MenuImage {
    public static final int img_width_default = 128;
    public static final int img_height_default = 120;
    public static final int img_width = (int)(img_width_default);
    public static final int img_height = (int)(img_height_default);
    private int x, y;
    private BufferedImage[] imgs;

    
    public MenuImage(int x, int y) {
        this.x = x;
        this.y = y;
        loadImgs();
    }


    private void loadImgs() {   
        imgs = new BufferedImage[3];
        BufferedImage temp = UtilityTool.importImg("images/UI/logo/dragon_logo.png");
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i * img_width_default, 0, img_width_default, img_height_default);
        }
    }

    public void draw(Graphics2D g2, int aniIndex, float scale) {
        int xOffsetCenter = (int)(img_width * scale) / 2;
        g2.drawImage(imgs[aniIndex],x - xOffsetCenter, y, (int)(img_width * scale), (int)(img_height * scale), null);
    }
}