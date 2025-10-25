package world;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class DecorManager {
    public List<Entity> decorList = new ArrayList<>();
    private List<Rectangle> decorSolidAreaList = new ArrayList<>();

    public void add(Entity entity) {
        decorList.add(entity);
        
        if (entity.solidArea != null) {
            decorSolidAreaList.add(entity.solidArea);
        }
        
        System.out.println(entity.solidArea);
    }

    public void update() {
        for (Entity d : decorList) {
            d.update();
        }
    }

    public void draw(Graphics2D g2, double cameraX, double cameraY) {
        for (Entity d : decorList) {
            d.draw(g2, cameraX, cameraY);
        }
    }

    public List<Entity> getDecorList() {
        return decorList;
    }

    public List<Rectangle> getDecorSolidAreaList() {
        return decorSolidAreaList;
    }

}
