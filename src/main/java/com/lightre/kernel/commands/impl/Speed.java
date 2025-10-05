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
import java.util.stream.Stream;

public class Speed extends AbstractCommand {

    private static final float DEFAULT_WALK_SPEED = 0.2f;
    private static final float DEFAULT_FLY_SPEED = 0.1f;

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            ChatUtils.sendMessage(sender, "&cUsage: /speed <walk|fly> <0-10> [player]");
            return true;
        }

        String speedType = args[0].toLowerCase();
        if (!speedType.equals("walk") && !speedType.equals("fly")) {
            ChatUtils.sendMessage(sender, "&cInvalid speed type. Use 'walk' or 'fly'.");
            return true;
        }

        float speedValue;
        try {
            speedValue = Float.parseFloat(args[1]);
        } catch (NumberFormatException e) {
            ChatUtils.sendMessage(sender, "&c'" + args[1] + "' is not a valid number.");
            return true;
        }

        if (speedValue < 0 || speedValue > 10) {
            ChatUtils.sendMessage(sender, "&cSpeed must be a number between 0-10.");
            return true;
        }

        Player target;
        boolean isSelf = args.length == 2;
        if (isSelf) {
            if (!(sender instanceof Player)) {
                ChatUtils.sendMessage(sender, "&cPlease specify a player. Usage: /speed <type> <speed> <player>");
                return true;
            }
            target = (Player) sender;
        } else {
            if (!sender.hasPermission(getPermission() + ".others")) {
                ChatUtils.sendMessage(sender, "&cYou do not have permission to change other players' speed.");
                return true;
            }
            target = Bukkit.getPlayer(args[2]);
            if (target == null || !target.isOnline()) {
                ChatUtils.sendMessage(sender, "&cPlayer '" + args[2] + "' not found.");
                return true;
            }
        }

        if (speedType.equals("walk")) {
            target.setWalkSpeed(speedValue / 5.0f);
            ChatUtils.sendMessage(sender, "&7Set walk speed for &e" + target.getName() + "&7 to &b" + speedValue + "&7.");
        } else {
            if (!target.getAllowFlight()) {
                ChatUtils.sendMessage(sender, "&c" + target.getName() + " is not allowed to fly. Enable flight first.");
                return true;
            }
            target.setFlySpeed(speedValue / 10.0f);
            ChatUtils.sendMessage(sender, "&7Set fly speed for &e" + target.getName() + "&7 to &b" + speedValue + "&7.");
        }

        return true;
    }

    @Override
    public String getName() {
        return "speed";
    }

    @Override
    public String getPermission() {
        return "kernel.utility.speed";
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Stream.of("walk", "fly")
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (args.length == 2) {
            String speedType = args[0].toLowerCase();
            if (speedType.equals("walk")) {
                return Collections.singletonList(String.valueOf(DEFAULT_WALK_SPEED * 5)); // 1.0
            } else if (speedType.equals("fly")) {
                return Collections.singletonList(String.valueOf(DEFAULT_FLY_SPEED * 10)); // 1.0
            }
        }

        if (args.length == 3) {
            if (sender.hasPermission(getPermission() + ".others")) {
                return Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .filter(name -> name.toLowerCase().startsWith(args[2].toLowerCase()))
                        .collect(Collectors.toList());
            }
        }

        return Collections.emptyList();
    }
}