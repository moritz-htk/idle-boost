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

package net.kyronis.idle_boost.activity;

import net.kyronis.idle_boost.IdleBoost;
import net.kyronis.idle_boost.config.IBConfig;
import net.kyronis.idle_boost.config.IBConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;

import java.util.concurrent.TimeUnit;

/**
 * Temporarily reduces client settings while the window is inactive.
 */
public class IBWindowActivityHandler {
    private static Integer originalFramerateLimit;
    private static Integer originalRenderDistance;

    private static boolean waitingForBackground;
    private static long backgroundStartedAtNanos;

    private IBWindowActivityHandler() {
    }

    /**
     * Called by the client tick event.
     */
    public static void handleClientTick() {
        Minecraft client = Minecraft.getInstance();

        if (!client.isSameThread()) {
            client.execute(IBWindowActivityHandler::handleClientTick);
            return;
        }

        IBConfig config = IBConfigManager.getConfig();

        boolean backgroundRequested = config.modToggle && client.level != null && !client.isWindowActive() && (config.framerateLimitToggle || config.renderDistanceToggle);
        boolean backgroundActive = isBackgroundReady(backgroundRequested, config.backgroundDelaySeconds);

        updateFramerateLimit(client.options.framerateLimit(), backgroundActive && config.framerateLimitToggle, config.framerateLimit);
        updateRenderDistance(client.options.renderDistance(), backgroundActive && config.renderDistanceToggle, config.renderDistance);
    }

    private static boolean isBackgroundReady(boolean requested, int delaySeconds) {
        if (!requested) {
            waitingForBackground = false;
            return false;
        }

        long now = System.nanoTime();

        if (!waitingForBackground) {
            waitingForBackground = true;
            backgroundStartedAtNanos = now;
        }

        long delayNanos = TimeUnit.SECONDS.toNanos(Math.clamp(delaySeconds, 0, 60));

        return now - backgroundStartedAtNanos >= delayNanos;
    }

    private static void updateFramerateLimit(OptionInstance<Integer> option, boolean enabled, int configuredLimit) {
        if (enabled) {
            if (originalFramerateLimit == null) {
                originalFramerateLimit = option.get();
            }

            int target = Math.min(originalFramerateLimit, configuredLimit);
            setIfChanged(option, target, "Framerate limit");
        } else if (originalFramerateLimit != null) {
            setIfChanged(option, originalFramerateLimit, "Framerate limit restored");
            originalFramerateLimit = null;
        }
    }

    private static void updateRenderDistance(OptionInstance<Integer> option, boolean enabled, int configuredDistance) {
        if (enabled) {
            if (originalRenderDistance == null) {
                originalRenderDistance = option.get();
            }

            int target = Math.min(originalRenderDistance, configuredDistance);
            setIfChanged(option, target, "Render distance");
        } else if (originalRenderDistance != null) {
            setIfChanged(option, originalRenderDistance, "Render distance restored");
            originalRenderDistance = null;
        }
    }

    private static void setIfChanged(OptionInstance<Integer> option, int value, String description) {
        if (option.get() != value) {
            option.set(value);
            IdleBoost.logDebugMode("{}: {}", description, value);
        }
    }
}