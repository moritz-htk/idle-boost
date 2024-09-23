package net.moritz_htk.idle_boost.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.moritz_htk.idle_boost.IdleBoost;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Manages the configuration for the Idle Boost mod.
 */
public class IBConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/" + IdleBoost.MOD_ID + ".json");
    private static IBConfig CONFIG;

    /**
     * Loads the configuration from the config file. If the file does not exist, a new configuration is created.
     */
    public static void loadConfig() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                CONFIG = GSON.fromJson(reader, IBConfig.class);
                IdleBoost.logDebugMode("Config loaded successfully");
            } catch (IOException e) {
                IdleBoost.LOGGER.error("Failed to load config: {}", e.getMessage());
                e.printStackTrace();
            }
        } else {
            CONFIG = new IBConfig();
            IdleBoost.LOGGER.warn("Config file not found. Creating new config...");
        }
    }

    /**
     * Saves the current configuration to the config file.
     */
    public static void saveConfig() {
        IdleBoost.logDebugMode("Saving config...");
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(CONFIG, writer);
            IdleBoost.logDebugMode("Config saved successfully");
        } catch (IOException e) {
            IdleBoost.LOGGER.error("Failed to save config: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Returns the current configuration.
     *
     * @return the current IBConfig instance
     */
    public static IBConfig getConfig() {
        return CONFIG;
    }
}