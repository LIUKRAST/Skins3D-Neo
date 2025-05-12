package net.frozenblock.skins3d.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.skins3d.Skins3DUtils;
import net.minecraft.client.model.geom.ModelPart;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelPart.Cube.class)
public class ModelPart$CubeMixin {

    @Inject(method = "compile", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;addVertex(FFFIFFIIFFF)V"))
    private void compile(PoseStack.Pose pose, VertexConsumer buffer, int packedLight, int packedOverlay, int color, CallbackInfo ci,
            @Local ModelPart.Vertex modelpart$vertex, @Local(ordinal = 1) Vector3f vector3f2, @Local ModelPart.Polygon polygon
    ) {
        if(Skins3DUtils.FLAGGED) {
            if(Skins3DUtils.CHANGED) {
                var vertices = Skins3DUtils.getBorderUVs(Skins3DUtils.LAST_SAVED, polygon.vertices()[0].u(), polygon.vertices()[0].v(), polygon.vertices()[1].u(), polygon.vertices()[1].v());
                System.err.println(vertices);
            }
        }
    }
}
