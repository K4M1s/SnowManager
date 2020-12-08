package me.k4m1s.snowmanager.Helpers;

import me.k4m1s.snowmanager.Chat.Messages;
import me.k4m1s.snowmanager.SnowManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Random;

public class Snow {

    private static double getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private static Location getRandomPositionCloseToPlayer(Player player, int radius) {
        Location playerLocation = player.getLocation();
        return playerLocation.add(getRandomNumberInRange(-radius, radius), 0, getRandomNumberInRange(-radius, radius));
    }

    public static void spawnSnow(Player player, int radius) {
        // Allow snow to be placed only in normal world.
        if (player.getWorld().getEnvironment() != World.Environment.NORMAL) {
            return;
        }

        if (!SnowManager.getWhitelistedWorlds().contains(player.getWorld().getName())) {
            return;
        }

        // Get random spawn location.
        Location spawnLocation = getRandomPositionCloseToPlayer(player, radius);
        int y = Functions.getHighestY(player.getWorld(), spawnLocation.getBlockX(), spawnLocation.getBlockZ());

        // Check if block underneath Snow is proper.
        spawnLocation.setY(y);
        if (SnowManager.getGrassThings().contains(spawnLocation.getBlock().getType())) {
            return;
        }
        if (spawnLocation.getBlock().isPassable()) {
            return;
        }
        for (String name : SnowManager.getIgnoredBlocksByName()) {
            if (spawnLocation.getBlock().getType().toString().contains(name)) {
                return;
            }
        }

        if (SnowManager.getIgnoredBiomes().size() > 0 && SnowManager.getIgnoredBiomes().contains(player.getWorld().getBiome(spawnLocation.getBlockX(), spawnLocation.getBlockY(), spawnLocation.getBlockZ()))) {
            return;
        }

        // Place snow on top of the proper block.
        spawnLocation.setY(y + 1);
        spawnLocation.getBlock().setType(Material.SNOW);
    }
}
