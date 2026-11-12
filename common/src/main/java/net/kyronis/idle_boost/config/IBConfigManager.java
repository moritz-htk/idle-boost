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

package net.kyronis.idle_boost.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import net.kyronis.idle_boost.IdleBoost;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/**
 * Loads and saves the Idle Boost configuration.
 */
public class IBConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_FILE = Path.of("config", IdleBoost.MOD_ID + ".json").toAbsolutePath();

    private static final int MIN_FRAMERATE = 10;
    private static final int MAX_FRAMERATE = 120;
    private static final int MIN_RENDER_DISTANCE = 2;
    private static final int MAX_RENDER_DISTANCE = 64;

    private static IBConfig config = new IBConfig();
    private static boolean savingAllowed = true;

    private IBConfigManager() {
    }

    /**
     * Loads a valid configuration or falls back to defaults.
     */
    public static synchronized void loadConfig() {
        config = new IBConfig();
        savingAllowed = false;

        try {
            IBConfig loaded;

            try (Reader reader = Files.newBufferedReader(CONFIG_FILE, StandardCharsets.UTF_8)) {
                loaded = GSON.fromJson(reader, IBConfig.class);
            }

            if (loaded == null) {
                throw new JsonParseException("Configuration is empty or contains JSON null");
            }

            validate(loaded);
            config = loaded;
            savingAllowed = true;

            IdleBoost.logDebugMode("Config loaded successfully");
        } catch (NoSuchFileException e) {
            savingAllowed = true;
            IdleBoost.LOGGER.info("Config file not found. Creating defaults.");
            saveConfig();
        } catch (JsonParseException e) {
            IdleBoost.LOGGER.error("Invalid configuration. Using defaults.", e);

            if (backupInvalidConfig()) {
                savingAllowed = true;
                saveConfig();
            }
        } catch (IOException e) {
            IdleBoost.LOGGER.error("Failed to read configuration. Using defaults; saving is disabled to protect the existing file.", e);
        }
    }

    /**
     * Saves via a temporary file in the same directory.
     */
    public static synchronized void saveConfig() {
        if (!savingAllowed) {
            IdleBoost.LOGGER.warn("Configuration was not saved because loading or backing up the existing file failed. Reload the configuration before saving again.");
            return;
        }

        Path temporaryFile = null;

        try {
            validate(config);
            Files.createDirectories(CONFIG_FILE.getParent());

            temporaryFile = Files.createTempFile(CONFIG_FILE.getParent(), CONFIG_FILE.getFileName().toString() + ".", ".tmp");

            try (Writer writer = Files.newBufferedWriter(temporaryFile, StandardCharsets.UTF_8)) {
                GSON.toJson(config, writer);
            }

            try {
                Files.move(temporaryFile, CONFIG_FILE, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                IdleBoost.LOGGER.warn("Atomic replacement is not supported. Falling back to a regular file replacement.");
                Files.move(temporaryFile, CONFIG_FILE, StandardCopyOption.REPLACE_EXISTING);
            }

            IdleBoost.logDebugMode("Config saved successfully");
        } catch (IOException | JsonParseException e) {
            IdleBoost.LOGGER.error("Failed to save configuration.", e);
        } finally {
            if (temporaryFile != null) {
                try {
                    Files.deleteIfExists(temporaryFile);
                } catch (IOException e) {
                    IdleBoost.LOGGER.warn("Failed to remove temporary config file: {}", temporaryFile, e);
                }
            }
        }
    }

    public static synchronized IBConfig getConfig() {
        return config;
    }

    /**
     * Preserves invalid input before replacing it with defaults.
     */
    private static boolean backupInvalidConfig() {
        Path backup = CONFIG_FILE.resolveSibling(CONFIG_FILE.getFileName().toString() + ".broken");

        try {
            Files.move(CONFIG_FILE, backup, StandardCopyOption.REPLACE_EXISTING);
            IdleBoost.LOGGER.warn("Invalid configuration preserved at: {}", backup);
            return true;
        } catch (IOException e) {
            IdleBoost.LOGGER.error("Failed to preserve invalid configuration. Saving is disabled to avoid overwriting it.", e);
            return false;
        }
    }

    private static void validate(IBConfig value) {
        value.backgroundDelaySeconds = Math.clamp(value.backgroundDelaySeconds, 0, 60);
        value.framerateLimit = Math.clamp(value.framerateLimit, MIN_FRAMERATE, MAX_FRAMERATE);
        value.renderDistance = Math.clamp(value.renderDistance, MIN_RENDER_DISTANCE, MAX_RENDER_DISTANCE);
    }
}