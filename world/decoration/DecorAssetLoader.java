package world.decoration;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.GamePanel;
import tools.UtilityTool;

public class DecorAssetLoader {
    private static final Map<String, BufferedImage[]> cachedAssets = new HashMap<>();

    public static BufferedImage[] getGrassFrames(GamePanel gp) {
        return cachedAssets.computeIfAbsent("grass", key -> {
            int frameCount = 4; // how many frames per grass variant
            int variantCount = 1; // e.g. grass01.png, grass02.png

            List<BufferedImage> allFrames = new ArrayList<>();

            for (int i = 1; i <= variantCount; i++) {
                String path = String.format("images/world/decor/grass/grass01.png", i);
                BufferedImage sheet = UtilityTool.importImg(path);

                for (int frame = 0; frame < frameCount; frame++) {
                    BufferedImage frameImg = sheet.getSubimage(
                        frame * gp.originalTileSize, 0,
                        gp.originalTileSize, gp.originalTileSize
                    );
                    allFrames.add(UtilityTool.scaleImage(frameImg, gp.tileSize, gp.tileSize));
                }
            }

            return allFrames.toArray(new BufferedImage[0]);
        });
    }

    public static BufferedImage[] getTreeFrames(GamePanel gp, int scale) {
        return cachedAssets.computeIfAbsent("tree", key -> {
            int frameCount = 4; // how many frames per tree variant
            int variantCount = 1; // e.g. grass01.png, grass02.png

            List<BufferedImage> allFrames = new ArrayList<>();

            for (int i = 1; i <= variantCount; i++) {
                String path = String.format("images/world/decor/trees/tree01.png", i);
                BufferedImage sheet = UtilityTool.importImg(path);

                for (int frame = 0; frame < frameCount; frame++) {
                    BufferedImage frameImg = sheet.getSubimage(
                        frame * gp.originalTileSize*scale, 0,
                        gp.originalTileSize*scale, gp.originalTileSize*scale
                    );
                    allFrames.add(UtilityTool.scaleImage(frameImg, gp.tileSize*scale, gp.tileSize*scale));
                }
            }

            return allFrames.toArray(new BufferedImage[0]);
        });
    }

    // Add similar methods for other decor types, e.g., flowers, rocks, etc.


    //It removes all key-value pairs from the map, effectively resetting it to an empty state.
    public static void clearCache() { 
        cachedAssets.clear();
    }
}
