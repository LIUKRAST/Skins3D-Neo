package net.frozenblock.skins3d.mixins;

import net.frozenblock.skins3d.Config;
import net.frozenblock.skins3d.Skins3D;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ReloadableResourceManager.class)
public class ReloadableResourceManagerImplMixin {
    @Inject(at = @At("HEAD"), method = "createReload")
    private void reload(Executor prepareExecutor, Executor applyExecutor, CompletableFuture<Unit> initialStage, List<PackResources> packs, CallbackInfoReturnable<PackResources> cir) {
        Skins3D.configRes1 = Boolean.parseBoolean((String) Config.getConfig("player"));
        Skins3D.configRes2 = Boolean.parseBoolean((String) Config.getConfig("player.heads"));
        Skins3D.configRes3 = Integer.parseInt((String) Objects.requireNonNull(Config.getConfig("resolution")));
    }
}
