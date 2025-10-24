package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import main.GamePanel;
import tools.Renderable;
import tools.UtilityTool;

public class Entity implements Renderable {
    GamePanel gp;    
    BufferedImage img;


    //STATE
    public double worldX, worldY;
    public int spriteNum = 1;
    public Rectangle entityArea;
    public Rectangle solidArea;
    public boolean collisionOn = false;
    public boolean moving = false;
    public boolean up, right, left, down, left_up, left_down, right_up, right_down;
    public boolean collisionUp, collisionDown, collisionLeft, collisionRight;
    

    //COUNTER
    public int spriteCounter = 0;

    //CHARACTER STATUS
    public String name;
    public int defaultSpeed;
    public double speed;


    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    //HELPER METHODS

    public BufferedImage importImg(String filepath) {
        BufferedImage img = null;

        // First, try loading as a resource from inside the JAR
        InputStream is = getClass().getClassLoader().getResourceAsStream(filepath);
        
        if (is != null) {
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
        } 
        // If running in VS Code (file not found in JAR), try loading from the filesystem
        else {
            File file = new File("res/" + filepath);  // Adjust path based on your structure
            if (file.exists()) {
                try {
                    img = ImageIO.read(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Error: Image file not found -> " + file.getAbsolutePath());
            }
        }

        return img;
    }



    public BufferedImage setup(String imagePath, int width, int height) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath+".png"));
            image = UtilityTool.scaleImage(image, width, height);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    @Override public double getBottomY() {
        return worldY + solidArea.y + solidArea.height;
    }

    @Override
    public void draw(Graphics2D g2, double x, double y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
