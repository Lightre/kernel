package com.lightre.kernel.managers;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class GodManager {

    private final Set<UUID> godPlayers = new HashSet<>();

    public boolean isGod(Player player) {
        return godPlayers.contains(player.getUniqueId());
    }

    public boolean toggleGod(Player player) {
        if (isGod(player)) {
            godPlayers.remove(player.getUniqueId());
            return false;
        } else {
            godPlayers.add(player.getUniqueId());
            clearExistingTargets(player);

            return true;
        }
    }

    private void clearExistingTargets(Player player) {
        for (Entity entity : player.getNearbyEntities(100, 100, 100)) {
            if (entity instanceof Mob mob) {
                if (player.equals(mob.getTarget())) {
                    mob.setTarget(null);
                }
            }
        }
    }
}