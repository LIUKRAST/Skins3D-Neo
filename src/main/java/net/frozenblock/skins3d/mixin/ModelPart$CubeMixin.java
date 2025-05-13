package net.frozenblock.skins3d.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelPart.Cube.class)
public class ModelPart$CubeMixin {

    @Shadow @Final public ModelPart.Polygon[] polygons;

    @Shadow @Final public float maxX;

    @Shadow @Final public float maxZ;

    @Shadow @Final public float maxY;

    @Shadow @Final public float minY;

    @Shadow @Final public float minX;

    @Shadow @Final public float minZ;

    @Inject(method = "compile", at = @At("HEAD"), cancellable = true)
    private void compile(PoseStack.Pose pose, VertexConsumer buffer, int packedLight, int packedOverlay, int color, CallbackInfo ci) {
        Matrix4f matrix4f = pose.pose();
        Vector3f vector3f = new Vector3f();

        for (ModelPart.Polygon modelpart$polygon : polygons) {
            Vector3f vector3f1 = pose.transformNormal(modelpart$polygon.normal(), vector3f);
            float f = vector3f1.x();
            float f1 = vector3f1.y();
            float f2 = vector3f1.z();

            for (ModelPart.Vertex modelpart$vertex : modelpart$polygon.vertices()) {
                float f3 = modelpart$vertex.pos().x() / 16.0F;
                float f4 = modelpart$vertex.pos().y() / 16.0F;
                float f5 = modelpart$vertex.pos().z() / 16.0F;
                Vector3f vector3f2 = matrix4f.transformPosition(f3, f4, f5, vector3f);
                buffer.addVertex(
                        vector3f2.x(), vector3f2.y(), vector3f2.z(), color, modelpart$vertex.u(), modelpart$vertex.v(), packedOverlay, packedLight, f, f1, f2
                );
            }
            /*for(int k = 0; k < 4; k++) {
                int j = (k+1)%4;
                for(int i = 0; i < modelpart$polygon.vertices().length; i++) {
                    var modelpart$vertex = modelpart$polygon.vertices()[i];
                    boolean bl = i == k || i == j;
                    float f3 = modelpart$vertex.pos().x() / 16.0F;
                    float f4 = modelpart$vertex.pos().y() / 16.0F;
                    float f5 = modelpart$vertex.pos().z() / 16.0F;
                    float lX = (maxX + minX)/32f;
                    float lY = (maxY + minY)/32f;
                    float lZ = (maxZ + minZ)/32f;
                    Vector3f vector3f2 = matrix4f.transformPosition(bl?f3:lX, bl?f4:lY, bl?f5:lZ, vector3f);
                    buffer.addVertex(
                            vector3f2.x(), vector3f2.y(), vector3f2.z(), color, modelpart$vertex.u(), modelpart$vertex.v(), packedOverlay, packedLight, f, f1, f2
                    );
                }
            }*/

            int k = 0;
            int j = (k+1)%4;
            int w = 1;//(int) (maxX - minX);
            for(int pX = 0; pX < w; pX++) {
                for (int i = 0; i < modelpart$polygon.vertices().length; i++) {
                    var modelpart$vertex = modelpart$polygon.vertices()[i];
                    boolean bl = i == k || i == j;
                    boolean bl1 = i % 2 == j % 2;
                    float f3 = (modelpart$vertex.pos().x()+pX-(bl1?0:(8-1))) / 16.0F;
                    float f4 = modelpart$vertex.pos().y() / 16.0F;
                    float f5 = modelpart$vertex.pos().z() / 16.0F;
                    float lX = (maxX + minX) / 32f;
                    float lY = (maxY + minY) / 32f;
                    float lZ = (maxZ + minZ) / 32f;
                    Vector3f vector3f2 = matrix4f.transformPosition(bl ? f3 : lX, bl ? f4 : lY, bl ? f5 : lZ, vector3f);
                    buffer.addVertex(
                            vector3f2.x(), vector3f2.y(), vector3f2.z(), color, modelpart$vertex.u()-(bl1?0:7)/64f, modelpart$vertex.v(), packedOverlay, packedLight, f, f1, f2
                    );
                }
            }


        }
        ci.cancel();
    }
}
