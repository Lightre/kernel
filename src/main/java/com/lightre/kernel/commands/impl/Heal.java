package com.lightre.kernel.commands.impl;

import com.lightre.kernel.commands.base.AbstractCommand;
import com.lightre.kernel.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Heal extends AbstractCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player target;

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                ChatUtils.sendMessage(sender, "&cPlease specify a player to heal. Usage: /heal <player>");
                return true;
            }
            target = (Player) sender;
        } else {
            if (!sender.hasPermission(getPermission() + ".others")) {
                ChatUtils.sendMessage(sender, "&cYou do not have permission to heal other players.");
                return true;
            }
            target = Bukkit.getPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                ChatUtils.sendMessage(sender, "&cPlayer '" + args[0] + "' not found.");
                return true;
            }
        }

        healPlayer(target);

        if (target.equals(sender)) {
            ChatUtils.sendMessage(sender, "&aYou have been healed.");
        } else {
            ChatUtils.sendMessage(sender, "&aYou have healed " + target.getName() + ".");
        }

        return true;
    }

    /**
     * A private helper method to handle the actual healing logic.
     * This prevents code duplication.
     *
     * @param player The player to heal.
     */
    private void healPlayer(Player player) {
        double maxHealth = Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).getValue();
        player.setHealth(maxHealth);
        player.setFoodLevel(20);
    }

    @Override
    public String getName() {
        return "heal";
    }

    @Override
    public String getPermission() {
        return "kernel.admin.heal";
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