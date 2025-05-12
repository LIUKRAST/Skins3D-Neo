package net.frozenblock.skins3d;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Skins3DConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.BooleanValue APPLY_CUSTOM_MODEL = BUILDER
            .comment("Whether the modified 3D model should be applied to players")
            .define("apply_player_model", true);
    public static final ModConfigSpec.BooleanValue APPLY_HEAD_MODEL = BUILDER
            .comment("Whether the modified 3D model should be applied to player heads")
            .define("apply_head_model", true);
    public static final ModConfigSpec.IntValue MODEL_RESOLUTION = BUILDER
            .comment("The resolution of model effect")
            .defineInRange("resolution", 25, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.BooleanValue BLINKING = BUILDER
            .comment("Enables the blinking function")
            .define("blinking", true);
    static final ModConfigSpec SPEC = BUILDER.build();
}
