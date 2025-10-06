package com.lightre.kernel.commands.base;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public abstract class AbstractCommand implements ICommand {

    @Override
    public abstract boolean execute(CommandSender sender, String[] args);

    @Override
    public abstract String getName();

    @Override
    public abstract String getPermission();

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}