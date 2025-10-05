package com.lightre.kernel.commands.impl;

import com.lightre.kernel.commands.base.AbstractCommand;
import com.lightre.kernel.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class Broadcast extends AbstractCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            ChatUtils.sendMessage(sender, "&cUsage: /broadcast <message>");
            return true;
        }

        String message = String.join(" ", args);
        String prefix = "&b&l[Server] &7» &r";

        String finalMessage = ChatUtils.colorize(prefix + message);

        Bukkit.broadcastMessage(finalMessage);

        return true;
    }

    @Override
    public String getName() {
        return "broadcast";
    }

    @Override
    public String getPermission() {
        return "kernel.admin.broadcast";
    }
}