package com.lightre.kernel.commands.impl;

import com.lightre.kernel.commands.base.AbstractCommand;
import com.lightre.kernel.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EnderChest extends AbstractCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player viewer)) {
            ChatUtils.sendMessage(sender, "&cThis command can only be used by in-game players.");
            return true;
        }

        if (args.length == 0) {
            viewer.openInventory(viewer.getEnderChest());
            return true;
        }

        if (!sender.hasPermission(getPermission() + ".others")) {
            ChatUtils.sendMessage(sender, "&cYou do not have permission to open other players' ender chests.");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            ChatUtils.sendMessage(sender, "&cPlayer '" + args[0] + "' not found.");
            return true;
        }

        viewer.openInventory(target.getEnderChest());
        ChatUtils.sendMessage(sender, "&aOpening " + target.getName() + "'s ender chest...");
        return true;
    }

    @Override
    public String getName() {
        return "enderchest";
    }

    @Override
    public String getPermission() {
        return "kernel.admin.enderchest";
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1 && sender.hasPermission(getPermission() + ".others")) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}