package net.moritz_htk.idle_boost.neoforge;

import net.moritz_htk.idle_boost.IdleBoost;
import net.moritz_htk.idle_boost.config.IBConfigScreen;
import net.moritz_htk.idle_boost.util.IBWindowActivityHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

/**
 * NeoForge client initializer for the Idle Boost mod.
 */
@Mod(value = IdleBoost.MOD_ID, dist = Dist.CLIENT)
public class IBNeoForge {
    /**
     * Constructs a new IdleBoostNeoForge instance and initializes the mod.
     */
    public IBNeoForge() {
        IdleBoost.init();
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> ((client, parent) -> new IBConfigScreen(parent)));
        NeoForge.EVENT_BUS.addListener(this::onClientTick);
    }

    /**
     * Handles the client tick event.
     *
     * @param event the client tick event
     */
    public void onClientTick(ClientTickEvent.Pre event) {
        IBWindowActivityHandler.handleClientTick();
    }
}