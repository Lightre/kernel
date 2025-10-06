package com.lightre.kernel.managers;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GodManager {

    private final Set<UUID> godPlayers = new HashSet<>();

    public boolean isGod(Player player) {
        return godPlayers.contains(player.getUniqueId());
    }

    /**
     * Toggles the god mode for a player.
     * @param player The player whose god mode to toggle.
     * @return The new god mode state (true if enabled, false if disabled).
     */
    public boolean toggleGod(Player player) {
        if (isGod(player)) {
            godPlayers.remove(player.getUniqueId());
            return false; // God mode is now disabled.
        } else {
            godPlayers.add(player.getUniqueId());
            return true; // God mode is now enabled.
        }
    }
}