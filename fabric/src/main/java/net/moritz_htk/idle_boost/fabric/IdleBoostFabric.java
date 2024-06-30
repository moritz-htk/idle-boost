package net.moritz_htk.idle_boost.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.moritz_htk.idle_boost.IdleBoost;

public class IdleBoostFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        IdleBoost.init();
    }
}