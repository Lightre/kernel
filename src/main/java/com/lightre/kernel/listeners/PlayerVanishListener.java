package com.lightre.kernel.listeners;

import com.lightre.kernel.Kernel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerVanishListener implements Listener {

    private final Kernel plugin;

    public PlayerVanishListener(Kernel plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player joinedPlayer = event.getPlayer();
        plugin.getVanishManager().updateVanishedForPlayer(joinedPlayer);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player quittingPlayer = event.getPlayer();
        if (plugin.getVanishManager().isVanished(quittingPlayer)) {
            plugin.getVanishManager().setVanished(quittingPlayer, false);
        }
    }
}