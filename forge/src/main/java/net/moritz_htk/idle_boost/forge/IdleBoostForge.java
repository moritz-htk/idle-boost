package net.moritz_htk.idle_boost.forge;

import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.moritz_htk.idle_boost.IdleBoost;
import net.moritz_htk.idle_boost.config.IBConfigScreen;
import net.moritz_htk.idle_boost.util.IBWindowActivityHandler;

@Mod(value = IdleBoost.MOD_ID)
public class IdleBoostForge {
    /**
     * Constructs a new IdleBoostNeoForge instance and initializes the mod.
     */
    public IdleBoostForge() {
        IdleBoost.init();
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new IBConfigScreen(screen)));
        MinecraftForge.EVENT_BUS.addListener(this::onClientTick);
    }

    /**
     * Handles the client tick event.
     *
     * @param event the client tick event
     */
    public void onClientTick(TickEvent.ClientTickEvent event) {
        IBWindowActivityHandler.handleClientTick();
    }
}