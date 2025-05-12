package net.frozenblock.skins3d;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@Mod(Skins3D.MOD_ID)
public class Skins3D {

    public static final String MOD_ID = "skins3d";
    public Skins3D(IEventBus modEventBus, ModContainer container) {
        modEventBus.register(this);
        container.registerConfig(ModConfig.Type.CLIENT, Skins3DConfig.SPEC);
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

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
