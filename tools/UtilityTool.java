package tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.GamePanel;

public class UtilityTool {
    GamePanel gp;
    Graphics2D g2;

    public UtilityTool(GamePanel gp) {
        this.gp = gp;
    }


    public static BufferedImage importImg(String filepath) {
        BufferedImage img = null;
        InputStream is = UtilityTool.class.getResourceAsStream(filepath);
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

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();
        
        return scaledImage;
    }


    //HELPER

    
    //HELPER METHODS
    public int getXForCenteredText(String text, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }

    public Rectangle drawSubWindow(Graphics2D g2, int x, int y, int width, int height, int stroke, Color color, Color strokeColor, Color hoverColor, Rectangle subWindow, boolean hovered) {
        subWindow = new Rectangle(x,y,width,height);
        g2.setColor(hovered ? hoverColor : color);
        g2.fillRoundRect(x, y, width, height, 35,35);
        g2.setColor(strokeColor);
        g2.setStroke(new BasicStroke(stroke));
        g2.drawRoundRect(x, y, width, height, 35, 35);
        return subWindow;
    }

    public void drawTextCentered(Graphics2D g2, int y, Color color, Color hoverColor, Color shadow, String text, float fontSize, boolean hovered) {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, fontSize));
        int x = getXForCenteredText(text, g2);
        g2.setColor(shadow);
        g2.drawString(text,x+4,y+4); //Shadow
        g2.setColor(hovered ? hoverColor : color);
        g2.drawString(text, x, y);
    }

    //GETTERS

}