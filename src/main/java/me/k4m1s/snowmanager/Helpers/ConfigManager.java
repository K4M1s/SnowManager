package me.k4m1s.snowmanager.Helpers;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    public static boolean validateConfig(FileConfiguration config) {
        if(config.getString("snowSpawnDelay") == null) return false;
        return config.getString("spawnRadius") != null;
    }
}
