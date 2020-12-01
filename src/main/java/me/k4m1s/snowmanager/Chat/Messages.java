package me.k4m1s.snowmanager.Chat;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Messages {
    private static final String prefix = "&6&l[SnowManager]&r ";

    /**
     * Sends message to server console.
     *
     * @param msg Message to send.
     */
    public static void sendMessage(String msg) {
        System.out.println(ChatColor.translateAlternateColorCodes('&', prefix + msg));
    }

    /**
     * Send message to command sender.
     *
     * @param sender Subject that message should be sent to.
     * @param msg Message to send.
     */
    public static void sendMessage(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));
    }

    /**
     * Sends message to command sender.
     *
     * @param sender Subject that message should be sent to.
     * @param msg Message to send.
     * @param bPrefix Include prefix?
     */
    public static void sendMessage(CommandSender sender, String msg, boolean bPrefix) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', bPrefix ? prefix : "" + msg));
    }
}