package world;

import main.GamePanel;
import tools.UtilityTool;

import java.awt.image.BufferedImage;

public class DecorLoader {

    public static void loadExampleDecor(DecorManager decorManager, GamePanel gp) {
        BufferedImage[] grassAnim = new BufferedImage[] {
            UtilityTool.scaleImage(UtilityTool.importImg("/res/world/grass/grass01.png"), gp.tileSize, gp.tileSize)
            , UtilityTool.scaleImage(UtilityTool.importImg("/res/world/grass/grass02.png"), gp.tileSize, gp.tileSize),
        };

        

        for (int i = 0; i < 100; i++) {
            double x = Math.random() * gp.maxWorldCol * gp.tileSize;
            double y = Math.random() * gp.maxWorldRow * gp.tileSize;
            decorManager.add(new WorldEntity(x, y, grassAnim, true));
        }
    }
}

