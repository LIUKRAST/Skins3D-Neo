package net.frozenblock.skins3d.mixins;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.HttpTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HttpTexture.class)
public class PlayerSkinTextureMixin {

    @Inject(at = @At("HEAD"), method = "setNoAlpha", cancellable = true)
    private static void onStrip(NativeImage image, int x1, int y1, int x2, int y2, CallbackInfo ci) {
        if(x1 == 0 && y1 == 0) {
            ci.cancel();
        }

    }
}
