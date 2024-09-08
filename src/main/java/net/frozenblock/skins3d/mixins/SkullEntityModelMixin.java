package net.frozenblock.skins3d.mixins;

import net.frozenblock.skins3d.Skins3D;
import net.frozenblock.skins3d.model.CustomPlayerModel;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkullModel.class)
public class SkullEntityModelMixin {

    @Inject(at = @At("RETURN"), method = "createHumanoidHeadLayer", cancellable = true)
    private static void getHead(CallbackInfoReturnable<LayerDefinition> cir) {
        MeshDefinition modelData = SkullModel.createHeadModel();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.getChild("head")
                .addOrReplaceChild("hat",
                        CustomPlayerModel.layering(1, new CubeDeformation(0), 32, 0, -4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f)
                        , PartPose.ZERO);

        if (Skins3D.configRes2) {
            cir.setReturnValue(LayerDefinition.create(modelData, 64, 64));
        }
    }
}
