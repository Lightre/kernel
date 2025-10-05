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

public class ClearChat extends AbstractCommand {

    private static final int LINES_TO_CLEAR = 150;

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        boolean isSilent = false;

        if (args.length > 0) {
            String argument = args[0].toLowerCase();
            if (argument.equals("silent") || argument.equals("-s")) {
                if (!sender.hasPermission(getPermission() + ".silent")) {
                    ChatUtils.sendMessage(sender, "&cYou do not have permission to clear chat silently.");
                    return true;
                }
                isSilent = true;
            } else {
                ChatUtils.sendMessage(sender, "&cUnknown argument. Usage: /clearchat [silent]");
                return true;
            }
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            clearPlayerChat(onlinePlayer);
        }

        clearConsoleChat();

        String message;
        if (isSilent) {
            message = "&7Chat has been cleared.";
        } else {
            message = "&7Chat has been cleared by &e" + sender.getName() + "&7.";
        }

        ChatUtils.broadcastMessage(message);

        return true;
    }

    private void clearPlayerChat(Player player) {
        for (int i = 0; i < LINES_TO_CLEAR; i++) {
            player.sendMessage("");
        }
    }

    private void clearConsoleChat() {
        for (int i = 0; i < LINES_TO_CLEAR; i++) {
            Bukkit.getConsoleSender().sendMessage("");
        }
    }

    @Override
    public String getName() {
        return "clearchat";
    }

    @Override
    public String getPermission() {
        return "kernel.admin.clearchat";
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            if (sender.hasPermission(getPermission() + ".silent")) {
                if ("silent".startsWith(args[0].toLowerCase())) {
                    return Collections.singletonList("silent");
                }
            }
        }
        return Collections.emptyList();
    }
}