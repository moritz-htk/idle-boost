package net.moritz_htk.idle_boost.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.moritz_htk.idle_boost.IdleBoost;
import net.moritz_htk.idle_boost.util.IBWindowActivityHandler;

/**
 * Fabric client initializer for the Idle Boost mod.
 */
public class IBFabric implements ClientModInitializer {
    /**
     * Initializes the client-side components of the Idle Boost mod.
     */
    @Override
    public void onInitializeClient() {
        IdleBoost.init();
        ClientTickEvents.START_CLIENT_TICK.register(client -> IBWindowActivityHandler.handleClientTick());
    }
}