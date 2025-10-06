package com.lightre.kernel.commands.impl;

import com.lightre.kernel.commands.base.AbstractCommand;
import com.lightre.kernel.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminChat extends AbstractCommand {

    private final String chatPermission = "kernel.admin.adminchat.receive";

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            ChatUtils.sendMessage(sender, "&cUsage: /adminchat <message>");
            return true;
        }

        String message = String.join(" ", args);
        String senderName = sender.getName();

        String format = "&4&l[AC] &c" + senderName + " &7» &r" + message;
        String formattedMessage = ChatUtils.colorize(format);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.hasPermission(chatPermission)) {
                onlinePlayer.sendMessage(formattedMessage);
            }
        }

        Bukkit.getConsoleSender().sendMessage(formattedMessage);

        return true;
    }

    @Override
    public String getName() {
        return "adminchat";
    }

    @Override
    public String getPermission() {
        return "kernel.admin.adminchat.send";
    }
}