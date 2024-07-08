package net.moritz_htk.idle_boost.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.moritz_htk.idle_boost.IdleBoost;

/**
 * Fabric-specific initialization class for the Idle Boost mod.
 * Implements the ClientModInitializer interface to initialize the mod on the client side.
 */
public class IdleBoostFabric implements ClientModInitializer {
    /**
     * Initializes the Idle Boost mod on the client side.
     */
    @Override
    public void onInitializeClient() {
        IdleBoost.init();
    }
}