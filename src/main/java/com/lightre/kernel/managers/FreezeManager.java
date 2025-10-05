package com.lightre.kernel.managers;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FreezeManager {

    private final Set<UUID> frozenPlayers = new HashSet<>();

    public boolean isFrozen(Player player) {
        return frozenPlayers.contains(player.getUniqueId());
    }

    public boolean toggleFreeze(Player player) {
        if (isFrozen(player)) {
            frozenPlayers.remove(player.getUniqueId());
            return false;
        } else {
            frozenPlayers.add(player.getUniqueId());
            return true;
        }
    }
}