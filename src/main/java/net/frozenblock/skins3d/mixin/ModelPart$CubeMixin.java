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

import java.util.List;

@Mixin(ModelPart.Cube.class)
public class ModelPart$CubeMixin {

    @Shadow @Final public ModelPart.Polygon[] polygons;

    @Shadow @Final public float maxX;

    @Shadow @Final public float maxZ;

    @Shadow @Final public float maxY;

    @Shadow @Final public float minY;

    @Shadow @Final public float minX;

    @Shadow @Final public float minZ;

    private static int function(int x, int y) {
        return (3*x + 2*y + 3)%4;
    }

    @Inject(method = "compile", at = @At("HEAD"), cancellable = true)
    private void compile(PoseStack.Pose pose, VertexConsumer buffer, int packedLight, int packedOverlay, int color, CallbackInfo ci) {
        Matrix4f matrix4f = pose.pose();
        Vector3f vector3f = new Vector3f();
        float lX = (maxX + minX) / 32f;
        float lY = (maxY + minY) / 32f;
        float lZ = (maxZ + minZ) / 32f;
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
            for(int i = 0; i < 4; i++) {
                var v = modelpart$polygon.vertices()[i];
                var vP = modelpart$polygon.vertices()[(i+2)%4];
                var v1 = modelpart$polygon.vertices()[(i+1)%4];
                var v1P = modelpart$polygon.vertices()[(i+3)%4];
                int st = 8;//(int) Mth.sqrt(Mth.square(v.pos().x - v1.pos().x) + Mth.square(v.pos().y-v1.pos().y) + Mth.square(v.pos().z-v1.pos().z));
                int st1 = 8;
                for(int s = 0; s < st; s++) {
                    for(int s1 = 0; s1 < st1; s1++) {

                        //render pre
                        for (int j = (i == 3 ? 1 : 0); j < i; j++) {
                            var vt = modelpart$polygon.vertices()[j];
                            var v3f2 = matrix4f.transformPosition(lX, lY, lZ, vector3f);
                            buffer.addVertex(v3f2.x, v3f2.y, v3f2.z, color,
                                    vt.u(), //TODO: While not important for player model, it might for other entities
                                    vt.v(), //TODO: While not important for player model, it might for other entities
                                    packedOverlay, packedLight, f, f1, f2);
                        }
                        float fx = (v.pos().x + ((float) s / st) * (v1.pos().x - v.pos().x)) / 16f;
                        float fy = (v.pos().y + ((float) s / st) * (v1.pos().y - v.pos().y)) / 16f;
                        float fz = (v.pos().z + ((float) s / st) * (v1.pos().z - v.pos().z)) / 16f;
                        float f1x = (v.pos().x + ((float) (s + 1) / st) * (v1.pos().x - v.pos().x)) / 16f;
                        float f1y = (v.pos().y + ((float) (s + 1) / st) * (v1.pos().y - v.pos().y)) / 16f;
                        float f1z = (v.pos().z + ((float) (s + 1) / st) * (v1.pos().z - v.pos().z)) / 16f;
                        var v3f2t = matrix4f.transformPosition(fx, fy, fz, vector3f);
                        buffer.addVertex(v3f2t.x, v3f2t.y, v3f2t.z, color,
                                (v.u() + ((float) s / st) * (v1.u() - v.u())),
                                (v.v() + ((float) s / st) * (v1.v() - v.v())),
                                packedOverlay, packedLight, f, f1, f2);
                        var v3f21t = matrix4f.transformPosition(f1x, f1y, f1z, vector3f);
                        buffer.addVertex(v3f21t.x, v3f21t.y, v3f21t.z, color,
                                (v.u() + ((float) (s + 1) / st) * (v1.u() - v.u())),
                                (v.v() + ((float) (s + 1) / st) * (v1.v() - v.v())),
                                packedOverlay, packedLight, f, f1, f2);
                        //render post
                        for (int j = i + 2; j < 4; j++) {
                            var vt = modelpart$polygon.vertices()[j];
                            var v3f2 = matrix4f.transformPosition(lX, lY, lZ, vector3f);
                            buffer.addVertex(v3f2.x, v3f2.y, v3f2.z, color,
                                    vt.u(), //TODO: While not important for player model, it might for other entities
                                    vt.v(), //TODO: While not important for player model, it might for other entities
                                    packedOverlay, packedLight, f, f1, f2);
                        }
                    }
                }
            }

            /*int k = 0;
            int j = (k+1)%4;
            var n = modelpart$polygon.normal();
            if(n.y!=0) {
                continue;
            }
            int w = (int)(n.x==0?(maxX - minX):(maxZ-minZ));
            for(int pX = 0; pX < w; pX++) {
                for (int i = 0; i < modelpart$polygon.vertices().length; i++) {
                    var modelpart$vertex = modelpart$polygon.vertices()[i];
                    boolean bl = i == k || i == j;
                    boolean bl1 = i!=0&&i!=3;
                    float f3 = (modelpart$vertex.pos().x()+(1*(pX-(bl1?0:(w-1))))) / 16.0F;
                    float f4 = modelpart$vertex.pos().y() / 16.0F;
                    float f5 = (modelpart$vertex.pos().z()+(0*(pX-(bl1?0:(w-1))))) / 16.0F;
                    float lX = (maxX + minX) / 32f;
                    float lY = (maxY + minY) / 32f;
                    float lZ = (maxZ + minZ) / 32f;
                    Vector3f vector3f2 = matrix4f.transformPosition(bl ? f3 : lX, bl ? f4 : lY, bl ? f5 : lZ, vector3f);
                    buffer.addVertex(
                            vector3f2.x(), vector3f2.y(), vector3f2.z(), color, modelpart$vertex.u()+(pX-(bl1?0:w-1))/64f, modelpart$vertex.v(), packedOverlay, packedLight, f, f1, f2
                    );
                }
            }*/
            /*
            for(int i = 0; i < 4; i++) {
                int k =(i+1)%4;
                var v1 = modelpart$polygon.vertices()[i];
                var v2 = modelpart$polygon.vertices()[k];
                for(int j = 0; j < 1; j++) {

                }
                for(var v : new ModelPart.Vertex[]{v1, v2}) {
                    float fX = v.pos().x;
                    float fY = v.pos().y;
                    float fZ = v.pos().z;
                    Vector3f v3f2 = matrix4f.transformPosition(fX, fY, fZ, vector3f);
                    buffer.addVertex(
                            v3f2.x, v3f2.y, v3f2.z, color, v.u(), v.v(), packedOverlay, packedLight, f, f1, f2
                    );
                }
                buffer.addVertex(0,0,0,color,v1.v(), v1.u(), packedOverlay, packedLight, f, f1, f2);
                buffer.addVertex(0,0,0,color,v2.v(), v2.u(), packedOverlay, packedLight, f, f1, f2);
            }*/


        }
        ci.cancel();
    }
}
