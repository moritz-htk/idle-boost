package net.moritz_htk.idle_boost;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.sounds.SoundSource;
import net.moritz_htk.idle_boost.config.IBConfig;
import net.moritz_htk.idle_boost.config.IBConfigData;
import net.moritz_htk.idle_boost.config.IBConfigDefaults;
import net.moritz_htk.idle_boost.utils.IBGameSettingsModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for the Idle Boost mod.
 */
public class IdleBoost {
    public static final String MOD_ID = "idle_boost";
    public static IBConfig CONFIG = new IBConfigDefaults();
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static Options GAME_SETTINGS;
    public static int FPS = IBConfigDefaults.DEFAULT_BACKGROUND_FPS;
    public static int RENDER_DISTANCE = IBConfigDefaults.DEFAULT_BACKGROUND_RENDER_DISTANCE;
    private static boolean RETRIEVED = false;
    public static boolean CLOSING = false;

    /**
     * Initializes the Idle Boost mod by registering configuration and event handlers.
     */
    public static void init() {
        LOGGER.info("Initializing Idle Boost...");
        CONFIG = AutoConfig.register(IBConfigData.class, GsonConfigSerializer::new).getConfig();
        IBGameSettingsModifier.init();

        ClientLifecycleEvent.CLIENT_STARTED.register(client -> {
            LOGGER.info("Client started event triggered");
            retrieveInitialSettings();
        });

        ClientLifecycleEvent.CLIENT_STOPPING.register(server -> {
            LOGGER.info("Client stopping event triggered");
            restoreSettingsOnExit();
        });

        LOGGER.info("Idle Boost initialized");
    }

    /**
     * Retrieves the initial game settings when the client starts.
     */
    private static void retrieveInitialSettings() {
        GAME_SETTINGS = Minecraft.getInstance().options;
        if (!RETRIEVED) {
            FPS = GAME_SETTINGS.framerateLimit().get();
            RENDER_DISTANCE = GAME_SETTINGS.renderDistance().get();
            RETRIEVED = true;
            LOGGER.info("Retrieved initial settings: FPS = {}, Render Distance = {}", FPS, RENDER_DISTANCE);
        }
    }

    /**
     * Restores the game settings when the client stops.
     */
    private static void restoreSettingsOnExit() {
        if (CONFIG.isFpsToggleEnabled()) {
            LOGGER.info("Restoring FPS limit to {}", FPS);
            IBGameSettingsModifier.setFpsLimit(FPS);
            GAME_SETTINGS.save();
        }

        if (CONFIG.isRenderDistanceToggleEnabled()) {
            LOGGER.info("Restoring render distance to {}", RENDER_DISTANCE);
            GAME_SETTINGS.renderDistance().set(RENDER_DISTANCE);
            GAME_SETTINGS.save();
        }

        if (CONFIG.isVolumeToggleEnabled() && GAME_SETTINGS.getSoundSourceVolume(SoundSource.MASTER) <= 0) {
            LOGGER.info("Resuming sound");
            Minecraft.getInstance().getSoundManager().resume();
        }

        CLOSING = true;
    }
}