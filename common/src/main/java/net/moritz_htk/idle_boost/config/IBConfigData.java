package net.moritz_htk.idle_boost.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.moritz_htk.idle_boost.IdleBoost;

/**
 * Config data class for Idle Boost, implementing IBConfig.
 */
@Config(name = IdleBoost.MOD_ID)
public class IBConfigData implements ConfigData, IBConfig {
    @ConfigEntry.Gui.Tooltip
    public boolean fpsToggle = IBConfigDefaults.DEFAULT_FPS_TOGGLE;

    @ConfigEntry.Gui.Tooltip
    public boolean renderDistanceToggle = IBConfigDefaults.DEFAULT_RENDER_DISTANCE_TOGGLE;

    @ConfigEntry.Gui.Tooltip
    public boolean volumeToggle = IBConfigDefaults.DEFAULT_VOLUME_TOGGLE;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 10, max = 120)
    public int backgroundFps = IBConfigDefaults.DEFAULT_BACKGROUND_FPS;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 2, max = 64)
    public int backgroundRenderDistance = IBConfigDefaults.DEFAULT_BACKGROUND_RENDER_DISTANCE;

    @Override
    public boolean isFpsToggleEnabled() {
        return fpsToggle;
    }

    @Override
    public boolean isRenderDistanceToggleEnabled() {
        return renderDistanceToggle;
    }

    @Override
    public boolean isVolumeToggleEnabled() {
        return volumeToggle;
    }

    @Override
    public int getBackgroundFps() {
        return backgroundFps;
    }

    @Override
    public int getBackgroundRenderDistance() {
        return backgroundRenderDistance;
    }
}