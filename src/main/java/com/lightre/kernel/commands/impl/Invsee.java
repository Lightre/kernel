package com.lightre.kernel.commands.impl;

import com.lightre.kernel.commands.base.AbstractCommand;
import com.lightre.kernel.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Invsee extends AbstractCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player viewer)) {
            ChatUtils.sendMessage(sender, "&cThis command can only be used by in-game players.");
            return true;
        }
        if (args.length == 0) {
            ChatUtils.sendMessage(sender, "&cUsage: /invsee <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            ChatUtils.sendMessage(sender, "&cPlayer '" + args[0] + "' not found.");
            return true;
        }

        if (target.equals(viewer)) {
            ChatUtils.sendMessage(sender, "&cYou cannot view your own inventory.");
            return true;
        }

        // KOPYA OLUŞTURMAK YERİNE, DOĞRUDAN HEDEFİN ENVANTERİNİ ALIYORUZ.
        Inventory targetInventory = target.getInventory();

        // VE HEDEFİN GERÇEK ENVANTERİNİ GÖRÜNTÜLEYİCİYE AÇIYORUZ.
        viewer.openInventory(targetInventory);

        return true;
    }

    @Override
    public String getName() {
        return "invsee";
    }

    @Override
    public String getPermission() {
        return "kernel.admin.invsee";
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> !name.equals(sender.getName()))
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}