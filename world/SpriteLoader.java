package world;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.GamePanel;
import tools.UtilityTool;

public class SpriteLoader {
    private static final Map<String, BufferedImage[]> cachedAssets = new HashMap<>();

    public static BufferedImage[] getAssetFrames(GamePanel gp, int scale, String path) {
            int frameCount = 4; // how many frames per tree variant

            List<BufferedImage> allFrames = new ArrayList<>();

            BufferedImage sheet = UtilityTool.importImg(path);

            for (int frame = 0; frame < frameCount; frame++) {
                BufferedImage frameImg = sheet.getSubimage(frame * gp.originalTileSize*scale, 0,gp.originalTileSize*scale, gp.originalTileSize*scale);
                allFrames.add(UtilityTool.scaleImage(frameImg, gp.tileSize*scale, gp.tileSize*scale));
            }
    
            return allFrames.toArray(new BufferedImage[0]);
    }


    //It removes all key-value pairs from the map, effectively resetting it to an empty state.
    public static void clearCache() { 
        cachedAssets.clear();
    }
}
