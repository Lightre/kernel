package com.lightre.kernel.managers;

import com.lightre.kernel.Kernel;
import com.lightre.kernel.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class VanishManager {

    private final Kernel plugin;
    private final Set<UUID> vanishedPlayers = new HashSet<>();
    private final String bypassPermission = "kernel.admin.vanish.bypass";

    public VanishManager(Kernel plugin) {
        this.plugin = plugin;
    }

    public boolean isVanished(Player player) {
        return vanishedPlayers.contains(player.getUniqueId());
    }

    public void setVanished(Player player, boolean vanish) {
        if (vanish) {
            vanishedPlayers.add(player.getUniqueId());
            hidePlayer(player);
        } else {
            vanishedPlayers.remove(player.getUniqueId());
            showPlayer(player);
        }
        updatePlayerListName(player);
    }

    public void hidePlayer(Player playerToHide) {
        for (Player observer : Bukkit.getOnlinePlayers()) {
            if (!observer.hasPermission(bypassPermission) && !observer.equals(playerToHide)) {
                observer.hidePlayer(plugin, playerToHide);
            }
        }
    }

    public void showPlayer(Player playerToShow) {
        for (Player observer : Bukkit.getOnlinePlayers()) {
            observer.showPlayer(plugin, playerToShow);
        }
    }

    public void updateVanishedForPlayer(Player observer) {
        for (UUID vanishedUUID : vanishedPlayers) {
            Player vanishedPlayer = Bukkit.getPlayer(vanishedUUID);
            if (vanishedPlayer != null) {
                if (observer.hasPermission(bypassPermission) || observer.equals(vanishedPlayer)) {
                    observer.showPlayer(plugin, vanishedPlayer);
                } else {
                    observer.hidePlayer(plugin, vanishedPlayer);
                }
            }
        }
    }

    /**
     * Updates the player's display name in the tab list based on their vanish status.
     * Adds a [Vanish] prefix if vanished, otherwise resets to default.
     * @param player The player whose list name to update.
     */
    private void updatePlayerListName(Player player) {
        if (isVanished(player)) {
            String prefix = ChatUtils.colorize("&7[Vanish] &r");
            player.setPlayerListName(prefix + player.getName());
        } else {
            player.setPlayerListName(null);
        }
    }

    public Set<UUID> getVanishedPlayers() {
        return vanishedPlayers;
    }
}