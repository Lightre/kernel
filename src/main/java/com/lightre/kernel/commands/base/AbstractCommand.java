package com.lightre.kernel.commands.base;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * An abstract base class for commands, providing default behavior for optional methods.
 * Commands should extend this class to avoid boilerplate code.
 */
public abstract class AbstractCommand implements ICommand {

    // These methods are essential and must be implemented by every command.
    @Override
    public abstract boolean execute(CommandSender sender, String[] args);

    @Override
    public abstract String getName();

    @Override
    public abstract String getPermission();

    /**
     * Default implementation for tab completion.
     * Returns an empty list by default, meaning no suggestions.
     * Commands that need tab completion should @Override this method.
     */
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}