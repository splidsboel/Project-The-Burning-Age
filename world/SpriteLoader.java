package world;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import main.GamePanel;
import tools.UtilityTool;

public class SpriteLoader {
    
    private static final Map<String, BufferedImage[]> cachedAssets = new HashMap<>();

    public static BufferedImage[] getAssetFrames(GamePanel gp, int pixels, String path, int frameCount) {
        BufferedImage sheet = UtilityTool.importImg(path);
        BufferedImage[] frames = new BufferedImage[frameCount];

        for (int frame = 0; frame < frameCount; frame++) {
            frames[frame] = sheet.getSubimage(frame * pixels, 0, pixels, pixels);
        }
        return frames;
    }



    //It removes all key-value pairs from the map, effectively resetting it to an empty state.
    public static void clearCache() { 
        cachedAssets.clear();
    }
}
