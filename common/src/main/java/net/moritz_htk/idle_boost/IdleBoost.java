package net.moritz_htk.idle_boost;

import net.moritz_htk.idle_boost.config.IBConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for the Idle Boost mod.
 */
public class IdleBoost {
    public static final String MOD_ID = "idle_boost";
    public static final Logger LOGGER = LoggerFactory.getLogger("Idle Boost");

    /**
     * Initializes the Idle Boost mod.
     */
    public static void init() {
        LOGGER.info("Initializing Idle Boost...");
        IBConfigManager.loadConfig();
        LOGGER.info("Idle Boost initialized");
    }

    /**
     * Logs debug messages if debug mode is enabled.
     *
     * @param message  The message to log.
     * @param argument The optional argument to include in the message.
     */
    public static void logDebugMode(String message, Object... argument) {
        if (IBConfigManager.getConfig().debugModeToggle) {
            if (argument.length > 0) {
                LOGGER.info(message, argument);
            } else {
                LOGGER.info(message);
            }
        }
    }
}