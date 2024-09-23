package net.moritz_htk.idle_boost.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.moritz_htk.idle_boost.IdleBoost;
import net.moritz_htk.idle_boost.config.IBConfigManager;

/**
 * Handles window activity for the IdleBoost mod, adjusting game settings
 * based on whether the Minecraft window is active or not.
 */
public class IBWindowActivityHandler {
    private static int originalRenderDistance = -1;
    private static int originalFramerateLimit = -1;
    private static boolean wasWindowActive = true;

    /**
     * Handles the client tick event, adjusting settings based on window activity.
     */
    public static void handleClientTick() {
        if (Minecraft.getInstance().level == null) {
            return;
        }

        Minecraft.getInstance().execute(() -> {
            Options gameSettings = Minecraft.getInstance().options;
            if (IBConfigManager.getConfig().modToggle) {
                boolean isWindowActive = Minecraft.getInstance().isWindowActive();
                if (!isWindowActive && wasWindowActive) {
                    IdleBoost.logDebugMode("Window is inactive, applying background settings");
                    applyBackgroundSettings(gameSettings);
                } else if (isWindowActive && !wasWindowActive) {
                    IdleBoost.logDebugMode("Window is active, restoring foreground settings");
                    restoreForegroundSettings(gameSettings);
                }
                wasWindowActive = isWindowActive;
            }
        });
    }

    /**
     * Applies background settings when the Minecraft window is inactive.
     *
     * @param gameSettings The current game settings.
     */
    private static void applyBackgroundSettings(Options gameSettings) {
        if (IBConfigManager.getConfig().framerateLimitToggle) {
            int framerateLimit = IBConfigManager.getConfig().framerateLimit;
            if (originalFramerateLimit == -1) {
                originalFramerateLimit = gameSettings.framerateLimit().get();
                IdleBoost.logDebugMode("Original framerate limit saved: {}", originalFramerateLimit);
            }

            if (gameSettings.framerateLimit().get() != framerateLimit) {
                gameSettings.framerateLimit().set(framerateLimit);
                IdleBoost.logDebugMode("Framerate limit set to: {}", framerateLimit);
            }
        }

        if (IBConfigManager.getConfig().renderDistanceToggle) {
            int renderDistance = IBConfigManager.getConfig().renderDistance;
            if (originalRenderDistance == -1) {
                originalRenderDistance = gameSettings.renderDistance().get();
                IdleBoost.logDebugMode("Original render distance saved: {}", originalRenderDistance);
            }

            if (gameSettings.renderDistance().get() != renderDistance) {
                gameSettings.renderDistance().set(renderDistance);
                IdleBoost.logDebugMode("Render distance set to: {}", renderDistance);
            }
        }
    }

    /**
     * Restores foreground settings when the Minecraft window is active.
     *
     * @param gameSettings The current game settings.
     */
    private static void restoreForegroundSettings(Options gameSettings) {
        if (IBConfigManager.getConfig().framerateLimitToggle && originalFramerateLimit != -1) {
            if (gameSettings.framerateLimit().get() != originalFramerateLimit) {
                gameSettings.framerateLimit().set(originalFramerateLimit);
                IdleBoost.logDebugMode("Framerate limit restored to original value: {}", originalFramerateLimit);
            }
            originalFramerateLimit = -1;
        }

        if (IBConfigManager.getConfig().renderDistanceToggle && originalRenderDistance != -1) {
            if (gameSettings.renderDistance().get() != originalRenderDistance) {
                gameSettings.renderDistance().set(originalRenderDistance);
                IdleBoost.logDebugMode("Render distance restored to original value: {}", originalRenderDistance);
            }
            originalRenderDistance = -1;
        }
    }
}