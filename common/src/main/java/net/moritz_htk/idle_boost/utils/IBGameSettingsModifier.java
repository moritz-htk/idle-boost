package net.moritz_htk.idle_boost.utils;

import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.sounds.SoundSource;
import net.moritz_htk.idle_boost.IdleBoost;

public class IBGameSettingsModifier {
    public static void init() {
        ClientTickEvent.CLIENT_PRE.register(client -> {
            if (IdleBoost.CLOSING) return;
            if (Minecraft.getInstance().getWindow().shouldClose() || Minecraft.getInstance().level == null) return;
            Options gameSettings = Minecraft.getInstance().options;
            if (!Minecraft.getInstance().isWindowActive()) {
                if (IdleBoost.CONFIG.fpsToggle()) {
                    setFpsLimit(IdleBoost.CONFIG.backgroundFps());
                }
                if (IdleBoost.CONFIG.renderDistanceToggle()) {
                    gameSettings.renderDistance().set(IdleBoost.CONFIG.backgroundRenderDistance());
                }
                if (IdleBoost.CONFIG.volumeToggle()) {
                    Minecraft.getInstance().getSoundManager().stop();
                }
            } else {
                if (Minecraft.getInstance().screen != null && Minecraft.getInstance().screen instanceof VideoSettingsScreen) {
                    return;
                } else {
                    if (IdleBoost.CONFIG.fpsToggle()) {
                        setFpsLimit(IdleBoost.FPS);
                    }
                    if (IdleBoost.CONFIG.renderDistanceToggle()) {
                        gameSettings.renderDistance().set(IdleBoost.RENDER_DISTANCE);
                    }
                }
                if (IdleBoost.CONFIG.volumeToggle()) {
                    if (gameSettings.getSoundSourceVolume(SoundSource.MASTER) <= 0) {
                        Minecraft.getInstance().getSoundManager().resume();
                    }
                }
            }
        });
        ClientTickEvent.CLIENT_POST.register(client -> {
            if (client.level != null) {
                if (!Minecraft.getInstance().isWindowActive()) return;

                int currentDist = IdleBoost.GAME_SETTINGS.renderDistance().get();
                int currentFps = IdleBoost.GAME_SETTINGS.framerateLimit().get();

                if (currentDist != IdleBoost.RENDER_DISTANCE && (currentDist >= IdleBoost.CONFIG.backgroundRenderDistance() + 1) || (currentDist <= IdleBoost.CONFIG.backgroundRenderDistance() - 1)) {
                    IdleBoost.RENDER_DISTANCE = currentDist;
                }
                if (currentFps != IdleBoost.FPS && (currentFps >= IdleBoost.CONFIG.backgroundFps() + 1) || (currentFps <= IdleBoost.CONFIG.backgroundFps() - 1)) {
                    IdleBoost.FPS = currentFps;
                }
                IdleBoost.GAME_SETTINGS.save();
            }
        });
    }

    public static void setFpsLimit(int fps) {
        Minecraft.getInstance().getWindow().setFramerateLimit(fps);
    }
}