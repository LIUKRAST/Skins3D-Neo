package net.frozenblock.skins3d.model;

import net.frozenblock.skins3d.Skins3D;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class CustomPlayerModel {

    public static final String FACE = "face";
    private static final String EAR = "ear";
    private static final String CLOAK = "cloak";
    private static final String LEFT_SLEEVE = "left_sleeve";
    private static final String RIGHT_SLEEVE = "right_sleeve";
    private static final String LEFT_PANTS = "left_pants";
    private static final String RIGHT_PANTS = "right_pants";

    public static MeshDefinition getCustomPlayerBasicShape(CubeDeformation dilation, float pivotOffsetY) {
        final MeshDefinition MeshDefinition = new MeshDefinition();
        final PartDefinition PartDefinition = MeshDefinition.getRoot();

        PartDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation), PartPose.offset(0.0f, 0.0f + pivotOffsetY, 0.0f));
        final PartDefinition c = !Skins3D.configRes4 ? PartDefinition : PartDefinition.addOrReplaceChild("face_root", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f + pivotOffsetY, 0.0f));
        c.addOrReplaceChild(FACE,
                CubeListBuilder.create().texOffs(24, 0)
                        .addBox(-4.0f, -8.0f, 4.05f * (Skins3D.configRes4 ? 1 : -1), 8.0f, 8.0f, 0, dilation), PartPose.offset(0.0f, 0.0f + pivotOffsetY, 0.0f));

        PartDefinition.addOrReplaceChild("hat",
                layering(2, dilation, 32, 0, -4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f)
                , PartPose.offset(0.0f, 0.0f + pivotOffsetY, 0.0f));

        PartDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, dilation), PartPose.offset(0.0f, 0.0f + pivotOffsetY, 0.0f));
        PartDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), PartPose.offset(-5.0f, 2.0f + pivotOffsetY, 0.0f));
        PartDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), PartPose.offset(5.0f, 2.0f + pivotOffsetY, 0.0f));
        PartDefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), PartPose.offset(-1.9f, 12.0f + pivotOffsetY, 0.0f));
        PartDefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), PartPose.offset(1.9f, 12.0f + pivotOffsetY, 0.0f));
        return MeshDefinition;
    }

    public static MeshDefinition getCustomPlayerFinalShape(CubeDeformation dilation, boolean slim) {
        MeshDefinition MeshDefinition = getCustomPlayerBasicShape(dilation, 0.0f);
        PartDefinition PartDefinition = MeshDefinition.getRoot();
        PartDefinition.addOrReplaceChild(EAR, CubeListBuilder.create().texOffs(24, 0).addBox(-3.0f, -6.0f, -1.0f, 6.0f, 6.0f, 1.0f, dilation), PartPose.ZERO);
        PartDefinition.addOrReplaceChild(CLOAK, CubeListBuilder.create().texOffs(0, 0).addBox(-5.0f, 0.0f, -1.0f, 10.0f, 16.0f, 1.0f, dilation, 1.0f, 0.5f), PartPose.offset(0.0f, 0.0f, 0.0f));

        CubeListBuilder SLIM_LEFT_SLEEVE_3D = layering(1, dilation, 48, 48, -1.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f);
        CubeListBuilder SLIM_RIGHT_SLEEVE_3D = layering(1, dilation, 40, 32, -2.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f);

        CubeListBuilder LEFT_SLEEVE_3D = layering(1, dilation, 48, 48, -1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f);
        CubeListBuilder RIGHT_SLEEVE_3D = layering(1, dilation, 40, 32, -3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f);

        CubeListBuilder LEFT_PANTS_3D = layering(1, dilation, 0, 48, -2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f);
        CubeListBuilder RIGHT_PANTS_3D = layering(1, dilation, 0, 32, -2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f);
        CubeListBuilder JACKET_3D = layering(1, dilation, 16, 32, -4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f);

        if (slim) {
            PartDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, dilation), PartPose.offset(5.0f, 2.5f, 0.0f));
            PartDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-2.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, dilation), PartPose.offset(-5.0f, 2.5f, 0.0f));
            PartDefinition.addOrReplaceChild(LEFT_SLEEVE, SLIM_LEFT_SLEEVE_3D, PartPose.offset(5.0f, 2.5f, 0.0f));
            PartDefinition.addOrReplaceChild(RIGHT_SLEEVE, SLIM_RIGHT_SLEEVE_3D, PartPose.offset(-5.0f, 2.5f, 0.0f));
        } else {
            PartDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), PartPose.offset(5.0f, 2.0f, 0.0f));
            PartDefinition.addOrReplaceChild(LEFT_SLEEVE, LEFT_SLEEVE_3D, PartPose.offset(5.0f, 2.0f, 0.0f));
            PartDefinition.addOrReplaceChild(RIGHT_SLEEVE, RIGHT_SLEEVE_3D, PartPose.offset(-5.0f, 2.0f, 0.0f));
        }
        PartDefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), PartPose.offset(1.9f, 12.0f, 0.0f));
        PartDefinition.addOrReplaceChild(LEFT_PANTS, LEFT_PANTS_3D, PartPose.offset(1.9f, 12.0f, 0.0f));
        PartDefinition.addOrReplaceChild(RIGHT_PANTS, RIGHT_PANTS_3D, PartPose.offset(-1.9f, 12.0f, 0.0f));
        PartDefinition.addOrReplaceChild("jacket", JACKET_3D, PartPose.ZERO);
        return MeshDefinition;
    }

    public static CubeListBuilder layering(int amount, CubeDeformation dilation, int textureX, int textureY, float offsetX, float offsetY, float offsetZ, float sizeX, float sizeY, float sizeZ) {
        CubeListBuilder LAYER = CubeListBuilder.create();
        for (int i = amount * Skins3D.configRes3; i > 0; i--) {
            float dil = ((float) i / 100) * (25 / (float) Skins3D.configRes3);
            LAYER.texOffs(textureX, textureY).addBox(offsetX, offsetY, offsetZ, sizeX, sizeY, sizeZ, dilation.extend(dil));
        }
        return LAYER;
    }
}
