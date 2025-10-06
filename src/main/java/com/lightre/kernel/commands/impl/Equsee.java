package com.lightre.kernel.commands.impl;

import com.lightre.kernel.commands.base.AbstractCommand;
import com.lightre.kernel.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Equsee extends AbstractCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player viewer)) {
            ChatUtils.sendMessage(sender, "&cThis command can only be used by in-game players.");
            return true;
        }
        if (args.length == 0) {
            ChatUtils.sendMessage(sender, "&cUsage: /equsee <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            ChatUtils.sendMessage(sender, "&cPlayer '" + args[0] + "' not found.");
            return true;
        }
        if (target.equals(viewer)) {
            ChatUtils.sendMessage(sender, "&cYou cannot view your own equipment this way.");
            return true;
        }

        PlayerInventory targetInventory = target.getInventory();
        Inventory equseeInventory = Bukkit.createInventory(null, 9, target.getName() + "'s Equipment");

        equseeInventory.setContents(targetInventory.getArmorContents());
        equseeInventory.setItem(4, targetInventory.getItemInOffHand());

        viewer.openInventory(equseeInventory);
        return true;
    }

    @Override
    public String getName() {
        return "equsee";
    }

    @Override
    public String getPermission() {
        return "kernel.admin.equsee";
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