package com.lightre.kernel.commands.impl;

import com.lightre.kernel.Kernel;
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

public class Freeze extends AbstractCommand {

    private final Kernel plugin;

    public Freeze(Kernel plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            ChatUtils.sendMessage(sender, "&cUsage: /freeze <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            ChatUtils.sendMessage(sender, "&cPlayer '" + args[0] + "' not found.");
            return true;
        }

        boolean isNowFrozen = plugin.getFreezeManager().toggleFreeze(target);
        String status = isNowFrozen ? "&bfrozen" : "&aunfrozen";

        // Komutu kullanan kişiye geri bildirim gönder.
        if (target.equals(sender)) {
            ChatUtils.sendMessage(sender, "&eYou have been " + status + ".");
        } else {
            ChatUtils.sendMessage(sender, "&cYou have " + status + target.getName() + ".");
        }

        return true;
    }

    @Override
    public String getName() {
        return "freeze";
    }

    @Override
    public String getPermission() {
        return "kernel.admin.freeze";
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}