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

public class Vanish extends AbstractCommand {

    private final Kernel plugin;

    public Vanish(Kernel plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player target;

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                ChatUtils.sendMessage(sender, "&cPlease specify a player. Usage: /vanish <player>");
                return true;
            }
            target = (Player) sender;
        } else {
            if (!sender.hasPermission(getPermission() + ".others")) {
                ChatUtils.sendMessage(sender, "&cYou do not have permission to vanish other players.");
                return true;
            }
            target = Bukkit.getPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                ChatUtils.sendMessage(sender, "&cPlayer '" + args[0] + "' not found.");
                return true;
            }
        }

        // Ana mantık: VanishManager'ı kullanarak durumu tersine çevir.
        boolean isNowVanished = !plugin.getVanishManager().isVanished(target);
        plugin.getVanishManager().setVanished(target, isNowVanished);

        String status = isNowVanished ? "&aenabled" : "&cdisabled";

        if (target.equals(sender)) {
            ChatUtils.sendMessage(sender, "&7Vanish mode " + status + "&7 for you.");
        } else {
            // Başkasını vanish'e alan kişiye her zaman mesaj gönderilir.
            ChatUtils.sendMessage(sender, "&7Vanish mode " + status + "&7 for " + target.getName() + ".");
            // Hedef oyuncu vanish'e ALINDIYSA ona mesaj GÖNDERİLMEZ, çünkü amacı gizlenmektir.
            // Sadece vanish'ten ÇIKARILDIYSA haberi olur.
            if (!isNowVanished) {
                ChatUtils.sendMessage(target, "&eVanish mode " + status + "&e for you by " + sender.getName() + ".");
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return "vanish";
    }

    @Override
    public String getPermission() {
        return "kernel.admin.vanish";
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