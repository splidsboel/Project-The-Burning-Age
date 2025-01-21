package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.GamePanel;
import tools.UtilityTool;

public class Entity {
    GamePanel gp;    
    BufferedImage img;


    //STATE
    public double worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;

    //COUNTER
    public int spriteCounter = 0;

    //TYPE
    public int type;
    public final int type_player = 0;

    //CHARACTER CLASS
    public int cClass;
    public final int cClass_warrior = 0;
    public final int cClass_hunter = 0;
    public final int cClass_mage = 0;

    //CHARACTER STATUS
    public String name;
    public int defaultSpeed;
    public double speed;
    public int maxLife;
    public int life;
    public int rage;
    public int maxRage;
    public int maxMana;
    public int mana; 
    public int level;
    public int attack;
    public int defense;
    public int strength;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    //HELPER METHODS

    public BufferedImage importImg(String filepath) {
        InputStream is = getClass().getResourceAsStream(filepath);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return img;
    }




    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool(gp);
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
            image = uTool.scaleImage(image, width, height);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
}
