package com.lightre.kernel.commands.impl;

import com.lightre.kernel.commands.base.AbstractCommand;
import com.lightre.kernel.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Hat extends AbstractCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player target;

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                ChatUtils.sendMessage(sender, "&cPlease specify a player. Usage: /hat <player>");
                return true;
            }
            target = (Player) sender;
        } else {
            if (!sender.hasPermission(getPermission() + ".others")) {
                ChatUtils.sendMessage(sender, "&cYou do not have permission to use this command on others.");
                return true;
            }
            target = Bukkit.getPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                ChatUtils.sendMessage(sender, "&cPlayer '" + args[0] + "' not found.");
                return true;
            }
        }

        PlayerInventory inventory = target.getInventory();
        ItemStack itemInHand = inventory.getItemInMainHand();

        if (itemInHand.getType() == Material.AIR) {
            ChatUtils.sendMessage(sender, "&cYou must be holding an item to put it on your head.");
            return true;
        }

        ItemStack itemOnHead = inventory.getHelmet();
        inventory.setHelmet(itemInHand);
        inventory.setItemInMainHand(itemOnHead);

        if (target.equals(sender)) {
            ChatUtils.sendMessage(sender, "&aItem placed on your head!");
        } else {
            ChatUtils.sendMessage(sender, "&aYou placed an item on " + target.getName() + "'s head.");
            ChatUtils.sendMessage(target, "&a" + sender.getName() + " placed an item on your head.");
        }

        return true;
    }

    @Override
    public String getName() {
        return "hat";
    }

    @Override
    public String getPermission() {
        return "kernel.admin.hat";
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