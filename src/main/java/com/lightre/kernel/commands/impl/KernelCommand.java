package com.lightre.kernel.commands.impl;

import com.lightre.kernel.Kernel;
import com.lightre.kernel.commands.base.AbstractCommand;
import com.lightre.kernel.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KernelCommand extends AbstractCommand {

    private final Kernel plugin;

    public KernelCommand(Kernel plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            displayPluginInfo(sender);
            return true;
        }

        String subCommand = args[0].toLowerCase();
        return switch (subCommand) {
            case "help" -> handleHelpCommand(sender);
            case "reload" -> handleReloadCommand(sender);
            default -> {
                ChatUtils.sendMessage(sender, "&cUnknown subcommand. Use &e/kernel help&c for a list of commands.");
                yield true;
            }
        };
    }

    private void displayPluginInfo(CommandSender sender) {
        PluginDescriptionFile desc = plugin.getDescription();
        sender.sendMessage(ChatUtils.colorize("&8&m-----------------------------------------------------"));
        sender.sendMessage(ChatUtils.colorize(" &b&l" + desc.getName() + " &e- Version " + desc.getVersion()));
        sender.sendMessage(ChatUtils.colorize(" &7" + desc.getDescription()));
        sender.sendMessage("");
        sender.sendMessage(ChatUtils.colorize(" &fAuthor: &b" + String.join(", ", desc.getAuthors())));
        sender.sendMessage(ChatUtils.colorize("&8&m-----------------------------------------------------"));
    }

    private boolean handleHelpCommand(CommandSender sender) {
        if (!sender.hasPermission(getPermission() + ".help")) {
            ChatUtils.sendMessage(sender, "&cYou do not have permission to use this command.");
            return true;
        }

        ChatUtils.sendMessage(sender, "&6&lKernel Commands:");
        plugin.getDescription().getCommands().forEach((cmd, data) -> {
            String description = (String) data.getOrDefault("description", "No description available.");
            sender.sendMessage(ChatUtils.colorize(" &e/" + cmd + " &7- " + description));
        });
        return true;
    }

    private boolean handleReloadCommand(CommandSender sender) {
        if (!sender.hasPermission(getPermission() + ".reload")) {
            ChatUtils.sendMessage(sender, "&cYou do not have permission to use this command.");
            return true;
        }

        ChatUtils.sendMessage(sender, "&aKernel configuration reloaded (Placeholder).");
        return true;
    }

    @Override
    public String getName() {
        return "kernel";
    }

    @Override
    public String getPermission() {
        return "kernel.command.main";
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Stream.of("help", "reload")
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}