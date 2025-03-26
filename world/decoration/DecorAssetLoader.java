package world.decoration;

import main.GamePanel;
import tools.UtilityTool;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecorAssetLoader {
    private static final Map<String, BufferedImage[]> cachedAssets = new HashMap<>();

    public static BufferedImage[] getGrassFrames(GamePanel gp) {
        return cachedAssets.computeIfAbsent("grass", key -> {
            int frameCount = 4; // or however many frames per grass variant
            int variantCount = 1; // e.g. grass01.png, grass02.png

            List<BufferedImage> allFrames = new ArrayList<>();

            for (int i = 1; i <= variantCount; i++) {
                String path = String.format("/res/images/world/decor/grass/grass01.png", i);
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

    // Add similar methods for other decor types, e.g., flowers, rocks, etc.


    //It removes all key-value pairs from the map, effectively resetting it to an empty state.
    public static void clearCache() { 
        cachedAssets.clear();
    }
}
