package net.frozenblock.skins3d.mixins;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.frozenblock.skins3d.Skins3D;
import net.frozenblock.skins3d.model.CustomPlayerModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerModel.class)
public class PlayerEntityModelMixin {
    @Unique
    private ModelPart skins3D_Neo$root;
    @Unique
    private ModelPart face_root;
    @Unique
    private ModelPart skins3D_Neo$face;


    /**
     * We want to store all the variables we will use later for animations*/
    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(ModelPart root, boolean thinArms, CallbackInfo ci) {
        this.skins3D_Neo$root = root;
        if(Skins3D.configRes4) this.face_root = root.getChild("face_root");
        skins3D_Neo$face = Skins3D.configRes4 ? face_root.getChild("face") : root.getChild("face");
    }

    @Inject(at = @At("RETURN"), method = "createMesh", cancellable = true)
    private static void newModel(CubeDeformation cubeDeformation, boolean slim, CallbackInfoReturnable<MeshDefinition> cir) {
        if (Skins3D.configRes1) {
            cir.setReturnValue(CustomPlayerModel.getCustomPlayerFinalShape(cubeDeformation, slim));
        }
    }

    @Inject(at = @At("TAIL"), method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V")
    private void render(LivingEntity livingEntity, float f, float g, float time, float i, float j, CallbackInfo ci) {
        skins3D_Neo$face.skipDraw = Math.cos(time/5f + livingEntity.getUUID().variant()) < 0.95f;
        if(Skins3D.configRes4) {
            face_root.copyFrom(skins3D_Neo$root.getChild("head"));
            skins3D_Neo$face.yRot = (float) Math.PI;
        } else {
            skins3D_Neo$face.copyFrom(skins3D_Neo$root.getChild("head"));
        }
    }

    @Inject(at = @At("RETURN"), method = "bodyParts", cancellable = true)
    private void getBodyParts(CallbackInfoReturnable<Iterable<ModelPart>> cir) {
        cir.setReturnValue(
                Iterables.concat(
                        cir.getReturnValue(),
                        ImmutableList.of(Skins3D.configRes4 ? this.face_root : this.skins3D_Neo$face)
                )
        );
    }
}
