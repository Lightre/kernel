package com.lightre.kernel.commands.base;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents the contract for any command in this plugin.
 * Defines the essential methods that every command must have.
 */
public interface ICommand {

    /**
     * Executes the command logic.
     */
    boolean execute(CommandSender sender, String[] args);

    /**
     * Returns the primary name of the command (as defined in plugin.yml).
     */
    String getName();

    /**
     * Returns the base permission required to use this command.
     */
    String getPermission();

    /**
     * Provides a list of suggestions for tab completion.
     */
    List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args);
}