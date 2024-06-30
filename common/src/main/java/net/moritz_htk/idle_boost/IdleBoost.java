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

public class IdleBoost {
    public static final String MOD_ID = "idle_boost";
    public static IBConfig CONFIG = new IBConfigDefaults();
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static Options GAME_SETTINGS;
    public static int FPS = IBConfigDefaults.BACKGROUND_FPS;
    public static int RENDER_DISTANCE = IBConfigDefaults.BACKGROUND_RENDER_DISTANCE;
    private static boolean RETRIEVED = false;
    public static boolean CLOSING = false;

    public static void init() {
        LOGGER.info("Initializing Idle Boost...");
        CONFIG = AutoConfig.register(IBConfigData.class, GsonConfigSerializer::new).getConfig();
        IBGameSettingsModifier.init();

        ClientLifecycleEvent.CLIENT_STARTED.register(client -> {
            LOGGER.info("Client started event triggered");
            GAME_SETTINGS = Minecraft.getInstance().options;
            if (!RETRIEVED) {
                FPS = GAME_SETTINGS.framerateLimit().get();
                RENDER_DISTANCE = GAME_SETTINGS.renderDistance().get();
                RETRIEVED = true;
                LOGGER.info("Initial FPS: {}, Initial Render Distance: {}", FPS, RENDER_DISTANCE);
            }
        });

        ClientLifecycleEvent.CLIENT_STOPPING.register(server -> {
            LOGGER.info("Client stopping event triggered");
            if (CONFIG.fpsToggle()) {
                LOGGER.info("Restoring FPS limit to {}", FPS);
                IBGameSettingsModifier.setFpsLimit(FPS);
                GAME_SETTINGS.save();
            }
            if (CONFIG.renderDistanceToggle()) {
                LOGGER.info("Restoring render distance to {}", RENDER_DISTANCE);
                GAME_SETTINGS.renderDistance().set(RENDER_DISTANCE);
                GAME_SETTINGS.save();
            }
            if (CONFIG.volumeToggle()) {
                if (GAME_SETTINGS.getSoundSourceVolume(SoundSource.MASTER) <= 0) {
                    LOGGER.info("Resuming sound");
                    Minecraft.getInstance().getSoundManager().resume();
                }
            }
            CLOSING = true;
        });
        LOGGER.info("Initialized Idle Boost");
    }
}