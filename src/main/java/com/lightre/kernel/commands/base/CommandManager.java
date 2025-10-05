package com.lightre.kernel.commands.base;

import com.lightre.kernel.Kernel;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the registration and delegation of all plugin commands.
 * This class acts as a single entry point for Bukkit's command system.
 */
public class CommandManager implements CommandExecutor, TabCompleter {

    private final Kernel plugin;
    private final Map<String, ICommand> commands = new HashMap<>();

    public CommandManager(Kernel plugin) {
        this.plugin = plugin;
    }

    /**
     * Registers a command and sets this manager as its executor and tab completer.
     * @param command The command instance to register.
     */
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
            // This should not happen if registered correctly.
            return false;
        }

        // Centralized permission check for the base command permission.
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
            // Delegate the tab completion logic to the specific command class.
            return cmd.onTabComplete(sender, command, alias, args);
        }
        return Collections.emptyList();
    }
}