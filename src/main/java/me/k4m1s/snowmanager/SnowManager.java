package me.k4m1s.snowmanager;

import me.k4m1s.snowmanager.Helpers.ConfigManager;
import me.k4m1s.snowmanager.Helpers.Snow;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import me.k4m1s.snowmanager.Chat.Messages;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public final class SnowManager extends JavaPlugin {

    private static SnowManager instance;
    FileConfiguration config;

    private static List<Material> ignoredBlocks;
    private static List<String> ignoredBlocksByName;
    private static List<Biome> ignoredBiomes;
    private static List<String> whitelistedWorlds;

    private BukkitTask taskTimer;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        config = getConfig();
        instance = this;

        if (!ConfigManager.validateConfig(config)) {
            Messages.sendMessage("&cPlugin configuration is invalid!");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        ignoredBlocks = new ArrayList<>();
        for (String materialName : config.getStringList("ignoredBlocks")) {
            Material material = Material.getMaterial(materialName);
            if (material != null)
                ignoredBlocks.add(material);
            else
                Messages.sendMessage(String.format("&cMaterial %s is not valid!", materialName));
        }

        ignoredBlocksByName = config.getStringList("ignoredBlocksByName");

        ignoredBiomes = new ArrayList<>();
        for (String biomeName : config.getStringList("blacklistedBiomes")) {
            try {
                Biome biome = Biome.valueOf(biomeName);
                ignoredBiomes.add(biome);
            } catch(IllegalArgumentException e) {
                Messages.sendMessage(String.format("&Biome %s is not valid!", biomeName));
            }

        }

        whitelistedWorlds = config.getStringList("whitelistedWorlds");

        taskTimer = Bukkit.getScheduler().runTaskTimer(this, () -> {
            for(Player player : Bukkit.getOnlinePlayers()){
                Snow.spawnSnow(player, config.getInt("spawnRadius"));
            }
        }, config.getLong("snowSpawnDelay"), 0);
    }

    @Override
    public void onDisable() {
        taskTimer.cancel();
    }

    public static SnowManager getInstance() { return instance; }

    public static List<Material> getGrassThings() {
        return ignoredBlocks;
    }

    public static List<String> getIgnoredBlocksByName() { return ignoredBlocksByName; }
    public static List<Biome> getIgnoredBiomes() { return ignoredBiomes; }
    public static List<String> getWhitelistedWorlds() { return whitelistedWorlds; }
}
