package net.moritz_htk.idle_boost.config;

public class IBConfigDefaults implements IBConfig {
    public static final boolean FPS_TOGGLE = true;
    public static final boolean RENDER_DISTANCE_TOGGLE = true;
    public static final boolean VOLUME_TOGGLE = true;
    public static final int BACKGROUND_FPS = 10;
    public static final int BACKGROUND_RENDER_DISTANCE = 2;

    @Override
    public boolean fpsToggle() {
        return FPS_TOGGLE;
    }

    @Override
    public boolean renderDistanceToggle() {
        return RENDER_DISTANCE_TOGGLE;
    }

    @Override
    public boolean volumeToggle() {
        return VOLUME_TOGGLE;
    }

    @Override
    public int backgroundFps() {
        return BACKGROUND_FPS;
    }

    @Override
    public int backgroundRenderDistance() {
        return BACKGROUND_RENDER_DISTANCE;
    }
}