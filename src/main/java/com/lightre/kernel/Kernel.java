package com.lightre.kernel;

import com.lightre.kernel.commands.base.AbstractCommand;
import com.lightre.kernel.commands.base.CommandManager;
import com.lightre.kernel.commands.impl.*;
import com.lightre.kernel.listeners.*;
import com.lightre.kernel.managers.*;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Stream;

public final class Kernel extends JavaPlugin {

    private CommandManager commandManager;
    private VanishManager vanishManager;
    private GodManager godManager;
    private FreezeManager freezeManager;

    @Override
    public void onEnable() {
        // Plugin'in başlatılma adımlarını sırayla ve temiz bir şekilde çağır.
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
        // Tüm komutları bir stream'de topla ve tek seferde kaydet.
        // Yeni bir komut eklemek için sadece bu streame eklemen yeterli.
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
                new Speed()
        ).forEach(commandManager::registerCommand);
    }

    private void loadListeners() {
        // Tüm listener'ları bir stream'de topla ve tek seferde kaydet.
        Stream.of(
                new PlayerVanishListener(this),
                new PlayerDamageListener(this),
                new PlayerFreezeListener(this)
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    // Manager Getter'ları
    public VanishManager getVanishManager() {
        return vanishManager;
    }

    public GodManager getGodManager() {
        return godManager;
    }

    public FreezeManager getFreezeManager() {
        return freezeManager;
    }
}