package net.frozenblock.skins3d.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.vertex.PoseStack;
import net.frozenblock.skins3d.Skins3D;
import net.frozenblock.skins3d.Skins3DUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<S extends LivingEntityRenderState> {
    @Unique
    private ResourceLocation skins3D$storedTexture;

    @ModifyExpressionValue(
            method = "getRenderType",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getTextureLocation(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;)Lnet/minecraft/resources/ResourceLocation;"
            )
    )
    private ResourceLocation getRenderType(ResourceLocation original) {
        //We update the texture only if there was a change, to reduce useless computation
        if(!original.equals(skins3D$storedTexture)) {
            skins3D$storedTexture = original;
            Skins3DUtils.LAST_SAVED = original;
            Skins3DUtils.CHANGED = true;
        }
        return original;
    }

    @Inject(
            method = "render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V",
                    shift = At.Shift.BEFORE
            )
    )
    private void render(S state, PoseStack ms, MultiBufferSource buffer, int i, CallbackInfo ci) {
        Skins3DUtils.FLAGGED = true;
    }

    @Inject(
            method = "render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V",
                    shift = At.Shift.AFTER
            )
    )
    private void render$1(S state, PoseStack ms, MultiBufferSource buffer, int i, CallbackInfo ci) {
        Skins3DUtils.FLAGGED = false;
    }
}
