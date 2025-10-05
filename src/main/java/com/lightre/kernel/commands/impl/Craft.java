package com.lightre.kernel.commands.impl;

import com.lightre.kernel.commands.base.AbstractCommand;
import com.lightre.kernel.utils.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Craft extends AbstractCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            ChatUtils.sendMessage(sender, "&cThis command can only be used by players.");
            return true;
        }

        player.openWorkbench(player.getLocation(), true);

        return true;
    }

    @Override
    public String getName() {
        return "craft";
    }

    @Override
    public String getPermission() {
        return "kernel.utility.craft";
    }
}