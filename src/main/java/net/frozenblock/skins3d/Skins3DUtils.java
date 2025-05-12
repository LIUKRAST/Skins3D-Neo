package net.frozenblock.skins3d;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.joml.Vector2f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Skins3DUtils {
    private Skins3DUtils() {}

    public static boolean FLAGGED = false;
    public static boolean CHANGED = false;
    public static ResourceLocation LAST_SAVED = null;

    public static List<Vector2f> getBorderUVs(ResourceLocation texture, float u, float v, float w, float h) {
        List<Vector2f> transparentUVs = new ArrayList<>();
        try {
            Resource resource = Minecraft.getInstance().getResourceManager().getResource(texture).orElseThrow();
            NativeImage image = NativeImage.read(resource.open());

            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();

            int fU = (int)(u * imgWidth);
            int fV = (int)(v * imgHeight);
            int fW = (int)(w * imgWidth);
            int fH = (int)(h * imgHeight);

            for (int x = fU; x < fU + fW; x++) {
                for (int y = fV; y < fV + fH; y++) {
                    if (x >= 0 && x < imgWidth && y >= 0 && y < imgHeight) {
                        int argb = image.getPixel(x, y);
                        int alpha = (argb >> 24) & 0xFF;

                        if (alpha == 0) {
                            float uCoord = (float) x / imgWidth;
                            float vCoord = (float) y / imgHeight;
                            transparentUVs.add(new Vector2f(uCoord, vCoord));
                        }
                    }
                }
            }

            image.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transparentUVs;
    }
}
