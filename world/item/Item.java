package world.item;

import java.awt.image.BufferedImage;
import main.GamePanel;
import world.Entity;

public class Item extends Entity {

    public Item(GamePanel gp, double x, double y, BufferedImage[] frames) {
        super(gp, x, y, frames);
        //TODO Auto-generated constructor stub
    }

    @Override
    public double getBottomY() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBottomY'");
    }
    
}
