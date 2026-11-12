/*
 * Copyright (C) 2026 Kyronis
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as published
 * by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.kyronis.idle_boost;

import net.kyronis.idle_boost.config.IBConfigManager;
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