package net.frozenblock.skins3d;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.util.Objects;

@Mod("skins3d")
public class Skins3D {

    public static boolean configRes1;
    public static boolean configRes2;
    public static int configRes3;
    public static final boolean configRes4 = false;

    public static final Path CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve("liukrast3dskins.json");

    public Skins3D(IEventBus modEventBus, ModContainer modContainer) {
        Skins3D.configRes1 = Boolean.parseBoolean((String) Config.getConfig("player"));
        Skins3D.configRes2 = Boolean.parseBoolean((String) Config.getConfig("player.heads"));
        Skins3D.configRes3 = Integer.parseInt((String) Objects.requireNonNull(Config.getConfig("resolution")));
    }
}
