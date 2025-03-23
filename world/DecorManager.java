package world;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class DecorManager {
    private List<WorldEntity> decorList = new ArrayList<>();

    public void add(WorldEntity entity) {
        decorList.add(entity);
    }

    public void update() {
        for (WorldEntity d : decorList) {
            d.update();
        }
    }

    public void draw(Graphics2D g2, double cameraX, double cameraY) {
        for (WorldEntity d : decorList) {
            d.draw(g2, cameraX, cameraY);
        }
    }

    public List<WorldEntity> getDecorList() {
        return decorList;
    }
}
