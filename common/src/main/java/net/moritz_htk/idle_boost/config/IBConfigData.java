package net.moritz_htk.idle_boost.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.moritz_htk.idle_boost.IdleBoost;

@Config(name = IdleBoost.MOD_ID)
public class IBConfigData implements ConfigData, IBConfig {
    @ConfigEntry.Gui.Tooltip
    public boolean fpsToggle = IBConfigDefaults.FPS_TOGGLE;

    @ConfigEntry.Gui.Tooltip
    public boolean renderDistanceToggle = IBConfigDefaults.RENDER_DISTANCE_TOGGLE;

    @ConfigEntry.Gui.Tooltip
    public boolean volumeToggle = IBConfigDefaults.VOLUME_TOGGLE;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 10, max = 120)
    public int backgroundFps = IBConfigDefaults.BACKGROUND_FPS;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 2, max = 64)
    public int backgroundRenderDistance = IBConfigDefaults.BACKGROUND_RENDER_DISTANCE;

    @Override
    public boolean fpsToggle() {
        return fpsToggle;
    }

    @Override
    public boolean renderDistanceToggle() {
        return renderDistanceToggle;
    }

    @Override
    public boolean volumeToggle() {
        return volumeToggle;
    }

    @Override
    public int backgroundFps() {
        return backgroundFps;
    }

    @Override
    public int backgroundRenderDistance() {
        return backgroundRenderDistance;
    }
}