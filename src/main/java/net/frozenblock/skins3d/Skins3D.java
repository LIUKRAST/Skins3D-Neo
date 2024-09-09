package net.frozenblock.skins3d;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.nio.file.Path;
import java.util.Objects;

@Mod(Skins3D.MOD_ID)
public class Skins3D {

    public static final String MOD_ID = "skins3d";

    public static boolean configRes1;
    public static boolean configRes2;
    public static int configRes3;
    public static final boolean configRes4 = false;

    public static final Path CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve("liukrast3dskins.json");

    public Skins3D(IEventBus modEventBus, ModContainer ignored) {
        Skins3D.configRes1 = Boolean.parseBoolean((String) Config.getConfig("player"));
        Skins3D.configRes2 = Boolean.parseBoolean((String) Config.getConfig("player.heads"));
        Skins3D.configRes3 = Integer.parseInt((String) Objects.requireNonNull(Config.getConfig("resolution")));

        modEventBus.register(this);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void addPackFinders(final AddPackFindersEvent event) {
        event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "resourcepacks/blink"),
                PackType.CLIENT_RESOURCES,
                Component.literal("Blinking Default"),
                PackSource.BUILT_IN,
                false,
                Pack.Position.TOP
        );
    }
}
