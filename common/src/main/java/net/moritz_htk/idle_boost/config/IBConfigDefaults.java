package net.moritz_htk.idle_boost.config;

/**
 * Default configuration values for Idle Boost.
 */
public class IBConfigDefaults implements IBConfig {
    public static final boolean DEFAULT_FPS_TOGGLE = true;
    public static final boolean DEFAULT_RENDER_DISTANCE_TOGGLE = true;
    public static final boolean DEFAULT_VOLUME_TOGGLE = true;
    public static final int DEFAULT_BACKGROUND_FPS = 10;
    public static final int DEFAULT_BACKGROUND_RENDER_DISTANCE = 2;

    @Override
    public boolean isFpsToggleEnabled() {
        return DEFAULT_FPS_TOGGLE;
    }

    @Override
    public boolean isRenderDistanceToggleEnabled() {
        return DEFAULT_RENDER_DISTANCE_TOGGLE;
    }

    @Override
    public boolean isVolumeToggleEnabled() {
        return DEFAULT_VOLUME_TOGGLE;
    }

    @Override
    public int getBackgroundFps() {
        return DEFAULT_BACKGROUND_FPS;
    }

    @Override
    public int getBackgroundRenderDistance() {
        return DEFAULT_BACKGROUND_RENDER_DISTANCE;
    }
}