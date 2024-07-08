package net.moritz_htk.idle_boost.fabric.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.moritz_htk.idle_boost.config.IBConfigData;

/**
 * Implementation of the ModMenuApi for Idle Boost.
 * Provides a configuration screen factory for the mod's settings.
 */
public class IBModMenuImplementation implements ModMenuApi {
    /**
     * Returns the configuration screen factory for the Idle Boost mod.
     *
     * @return a ConfigScreenFactory that generates the configuration screen.
     */
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(IBConfigData.class, parent).get();
    }
}