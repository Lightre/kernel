package com.lightre.kernel;

import com.lightre.kernel.commands.base.CommandManager;
import com.lightre.kernel.commands.impl.*;

import com.lightre.kernel.listeners.PlayerVanishListener;
import com.lightre.kernel.listeners.PlayerDamageListener;

import com.lightre.kernel.managers.VanishManager;
import com.lightre.kernel.managers.GodManager;

import org.bukkit.plugin.java.JavaPlugin;

public final class Kernel extends JavaPlugin {

    // Set Managers
    private CommandManager commandManager;
    private VanishManager vanishManager;
    private GodManager godManager;

    @Override
    public void onEnable() {
        getLogger().info("Kernel has been enabled.");

        // Initialize and set up the command manager
        this.commandManager = new CommandManager(this);
        this.vanishManager = new VanishManager(this);
        this.godManager = new GodManager();
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        getLogger().info("Kernel has been disabled.");
    }

    // Register Listeners
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerVanishListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(this), this);
    }

    // Register Commands
    private void registerCommands() {
        commandManager.registerCommand(new KernelCommand(this));
        commandManager.registerCommand(new Heal());
        commandManager.registerCommand(new Feed());
        commandManager.registerCommand(new Fly());
        commandManager.registerCommand(new Vanish(this));
        commandManager.registerCommand(new God(this));
        commandManager.registerCommand(new Hat());
        commandManager.registerCommand(new Whois(this));
    }

    // Managers
    public VanishManager getVanishManager() {
        return vanishManager;
    }

    public GodManager getGodManager() {
        return godManager;
    }
}