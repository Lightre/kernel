package com.lightre.kernel.commands.base;

import com.lightre.kernel.Kernel;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager implements CommandExecutor, TabCompleter {

    private final Kernel plugin;
    private final Map<String, ICommand> commands = new HashMap<>();

    public CommandManager(Kernel plugin) {
        this.plugin = plugin;
    }

    public void registerCommand(ICommand command) {
        PluginCommand pluginCommand = plugin.getCommand(command.getName());
        if (pluginCommand == null) {
            plugin.getLogger().severe("Command '" + command.getName() + "' could not be found in plugin.yml!");
            return;
        }

        pluginCommand.setExecutor(this);
        pluginCommand.setTabCompleter(this);

        commands.put(command.getName().toLowerCase(), command);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ICommand cmd = commands.get(command.getName().toLowerCase());
        if (cmd == null) {
            return false;
        }

        if (cmd.getPermission() != null && !sender.hasPermission(cmd.getPermission())) {
            sender.sendMessage("§cYou do not have permission to execute this command.");
            return true;
        }

        return cmd.execute(sender, args);
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ICommand cmd = commands.get(command.getName().toLowerCase());
        if (cmd != null) {
            return cmd.onTabComplete(sender, command, alias, args);
        }
        return Collections.emptyList();
    }
}