package net.moritz_htk.idle_boost.neoforge;

import me.shedaniel.autoconfig.AutoConfig;
import net.moritz_htk.idle_boost.IdleBoost;
import net.moritz_htk.idle_boost.config.IBConfigData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/**
 * NeoForge-specific initialization class for the Idle Boost mod.
 * Registers the mod and its configuration screen factory.
 */
@Mod(value = IdleBoost.MOD_ID, dist = Dist.CLIENT)
public class IdleBoostNeoForge {
    /**
     * Constructs the Idle Boost NeoForge class.
     * Initializes the Idle Boost mod and registers the configuration screen factory.
     */
    public IdleBoostNeoForge() {
        IdleBoost.init();
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> ((client, parent) -> AutoConfig.getConfigScreen(IBConfigData.class, parent).get()));
    }
}