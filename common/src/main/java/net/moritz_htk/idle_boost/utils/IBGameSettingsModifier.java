package net.moritz_htk.idle_boost.utils;

import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.sounds.SoundSource;
import net.moritz_htk.idle_boost.IdleBoost;

/**
 * Utility class for modifying game settings based on the window's active state.
 */
public class IBGameSettingsModifier {
    /**
     * Initializes the game settings modifier by registering client tick event handlers.
     */
    public static void init() {
        ClientTickEvent.CLIENT_PRE.register(client -> handleClientPreTick());
        ClientTickEvent.CLIENT_POST.register(IBGameSettingsModifier::handleClientPostTick);
    }

    /**
     * Handles the client pre-tick event.
     * Applies background settings if the window is inactive, otherwise restores foreground settings.
     */
    private static void handleClientPreTick() {
        if (IdleBoost.CLOSING || Minecraft.getInstance().getWindow().shouldClose() || Minecraft.getInstance().level == null) {
            return;
        }

        Options gameSettings = Minecraft.getInstance().options;

        if (!Minecraft.getInstance().isWindowActive()) {
            applyBackgroundSettings(gameSettings);
        } else {
            if (Minecraft.getInstance().screen instanceof VideoSettingsScreen) {
                return;
            }
            restoreForegroundSettings(gameSettings);
        }
    }

    /**
     * Applies background settings when the game window is inactive.
     *
     * @param gameSettings The current game settings.
     */
    private static void applyBackgroundSettings(Options gameSettings) {
        if (IdleBoost.CONFIG.isFpsToggleEnabled()) {
            setFpsLimit(IdleBoost.CONFIG.getBackgroundFps());
        }
        if (IdleBoost.CONFIG.isRenderDistanceToggleEnabled()) {
            gameSettings.renderDistance().set(IdleBoost.CONFIG.getBackgroundRenderDistance());
        }
        if (IdleBoost.CONFIG.isVolumeToggleEnabled()) {
            Minecraft.getInstance().getSoundManager().stop();
        }
    }

    /**
     * Restores foreground settings when the game window is active.
     *
     * @param gameSettings The current game settings.
     */
    private static void restoreForegroundSettings(Options gameSettings) {
        if (IdleBoost.CONFIG.isFpsToggleEnabled()) {
            setFpsLimit(IdleBoost.FPS);
        }
        if (IdleBoost.CONFIG.isRenderDistanceToggleEnabled()) {
            gameSettings.renderDistance().set(IdleBoost.RENDER_DISTANCE);
        }
        if (IdleBoost.CONFIG.isVolumeToggleEnabled() && gameSettings.getSoundSourceVolume(SoundSource.MASTER) <= 0) {
            Minecraft.getInstance().getSoundManager().resume();
        }
    }

    /**
     * Handles the client post-tick event.
     * Updates the stored FPS and render distance values if they have changed.
     *
     * @param client The Minecraft client instance.
     */
    private static void handleClientPostTick(Minecraft client) {
        if (client.level == null || !Minecraft.getInstance().isWindowActive()) {
            return;
        }

        Options gameSettings = IdleBoost.GAME_SETTINGS;
        int currentDist = gameSettings.renderDistance().get();
        int currentFps = gameSettings.framerateLimit().get();

        if (currentDist != IdleBoost.RENDER_DISTANCE && Math.abs(currentDist - IdleBoost.CONFIG.getBackgroundRenderDistance()) > 1) {
            IdleBoost.RENDER_DISTANCE = currentDist;
        }

        if (currentFps != IdleBoost.FPS && Math.abs(currentFps - IdleBoost.CONFIG.getBackgroundFps()) > 1) {
            IdleBoost.FPS = currentFps;
        }

        gameSettings.save();
    }

    /**
     * Sets the FPS limit of the game window.
     *
     * @param fps The FPS limit to set.
     */
    public static void setFpsLimit(int fps) {
        Minecraft.getInstance().getWindow().setFramerateLimit(fps);
    }
}