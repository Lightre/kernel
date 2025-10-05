package com.lightre.kernel.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * A utility class for chat-related functions, such as colorizing messages and sending prefixed messages.
 */
public class ChatUtils {

    public static final String PREFIX = "&b&lKernel &7»";

    /**
     * Translates alternate color codes ('&') into Bukkit's internal color codes.
     * @param message The message to colorize.
     * @return The colorized message.
     */
    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Sends a prefixed and colorized message to a CommandSender.
     * This is the primary method to be used for all plugin messages.
     * @param sender The recipient of the message (player or console).
     * @param message The message content (without the prefix).
     */
    public static void sendMessage(CommandSender sender, String message) {
        // Prefix, bir boşluk ve mesajı birleştirip renklendiriyoruz.
        String finalMessage = colorize(PREFIX + " &r" + message);
        sender.sendMessage(finalMessage);
    }
}