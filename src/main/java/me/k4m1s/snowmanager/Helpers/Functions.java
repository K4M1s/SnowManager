package me.k4m1s.snowmanager.Helpers;

import org.bukkit.Material;
import org.bukkit.World;

public class Functions {

    public static int getHighestY(World world, int x, int z) {
        int y = 255;
        while(world.getBlockAt(x, y, z).getType() == Material.AIR) { y--; }

        return y;
    }

}
