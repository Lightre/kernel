package com.lightre.kernel;

import com.lightre.kernel.commands.base.CommandManager;
import com.lightre.kernel.commands.impl.*;
import com.lightre.kernel.listeners.*;
import com.lightre.kernel.managers.*;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

public final class Kernel extends JavaPlugin {

    private CommandManager commandManager;
    private VanishManager vanishManager;
    private GodManager godManager;
    private FreezeManager freezeManager;

    @Override
    public void onEnable() {
        loadManagers();
        loadCommands();
        loadListeners();

        getLogger().info("Kernel has been enabled successfully.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Kernel has been disabled.");
    }

    private void loadManagers() {
        this.commandManager = new CommandManager(this);
        this.vanishManager = new VanishManager(this);
        this.godManager = new GodManager();
        this.freezeManager = new FreezeManager();
    }

    private void loadCommands() {
        Stream.of(
                new KernelCommand(this),
                new Heal(),
                new Feed(),
                new Fly(),
                new Vanish(this),
                new God(this),
                new Hat(),
                new Whois(this),
                new Broadcast(),
                new AdminChat(),
                new Freeze(this),
                new ClearChat(),
                new Speed(),
                new Repair(),
                new EnderChest(),
                new Invsee(),
                new Equsee()
        ).forEach(commandManager::registerCommand);
    }

    private void loadListeners() {
        Stream.of(
                new PlayerVanishListener(this),
                new PlayerFreezeListener(this),
                new GodModeListener(this)
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    public VanishManager getVanishManager() {
        return vanishManager;
    }

    public FreezeManager getFreezeManager() {
        return freezeManager;
    }

    public GodManager getGodManager() {
        return godManager;
    }
}