package net.moritz_htk.idle_boost.fabric.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.moritz_htk.idle_boost.config.IBConfigScreen;

/**
 * Implementation of the ModMenuApi for the Idle Boost mod.
 */
public class IBModMenuImplementation implements ModMenuApi {
    /**
     * Returns the factory for creating the configuration screen.
     *
     * @return the configuration screen factory
     */
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return IBConfigScreen::new;
    }
}