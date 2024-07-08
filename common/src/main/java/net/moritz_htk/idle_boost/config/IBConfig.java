package net.moritz_htk.idle_boost.config;

/**
 * Interface for Idle Boost configuration.
 */
public interface IBConfig {
    boolean isFpsToggleEnabled();
    boolean isRenderDistanceToggleEnabled();
    boolean isVolumeToggleEnabled();
    int getBackgroundFps();
    int getBackgroundRenderDistance();
}