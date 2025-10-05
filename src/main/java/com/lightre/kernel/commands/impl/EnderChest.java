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
        // Guard Clause: Sadece oyundaki oyuncular envanter açabilir.
        if (!(sender instanceof Player viewer)) {
            ChatUtils.sendMessage(sender, "&cThis command can only be used by in-game players.");
            return true;
        }

        // Senaryo 1: Oyuncu kendi Ender Sandığı'nı açıyor.
        if (args.length == 0) {
            viewer.openInventory(viewer.getEnderChest());
            return true;
        }

        // Senaryo 2: Oyuncu başkasının Ender Sandığı'nı açıyor.
        if (!sender.hasPermission(getPermission() + ".others")) {
            ChatUtils.sendMessage(sender, "&cYou do not have permission to open other players' ender chests.");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) { // Offline oyuncuların da Ender Sandığı açılabilir.
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
        return "kernel.utility.enderchest";
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1 && sender.hasPermission(getPermission() + ".others")) {
            // Hem online hem de daha önce oynamış oyuncuları önermek daha iyi olabilir,
            // ama şimdilik sadece online olanları önermek yeterli ve basittir.
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}