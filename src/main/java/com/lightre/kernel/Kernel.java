package com.lightre.kernel;

import com.lightre.kernel.commands.base.CommandManager;
import com.lightre.kernel.commands.impl.Feed;
import com.lightre.kernel.commands.impl.Heal;
import org.bukkit.plugin.java.JavaPlugin;

public final class Kernel extends JavaPlugin {

    private CommandManager commandManager;

    @Override
    public void onEnable() {
        getLogger().info("Kernel has been enabled.");

        // Initialize and set up the command manager
        this.commandManager = new CommandManager(this);
        registerCommands();
    }

    @Override
    public void onDisable() {
        getLogger().info("Kernel has been disabled.");
    }

    private void registerCommands() {
        commandManager.registerCommand(new Heal());
        commandManager.registerCommand(new Feed());
        // commandManager.registerCommand(new Fly());
    }
}