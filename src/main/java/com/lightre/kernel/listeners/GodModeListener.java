package com.lightre.kernel.listeners;

import com.lightre.kernel.Kernel;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PotionSplashEvent;

public class GodModeListener implements Listener {

    private final Kernel plugin;

    public GodModeListener(Kernel plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (plugin.getGodManager().isGod(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (plugin.getGodManager().isGod(player)) {
                event.setCancelled(true);
                player.setFoodLevel(20);
                player.setSaturation(20.0F);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPotionSplash(PotionSplashEvent event) {
        for (org.bukkit.entity.LivingEntity entity : event.getAffectedEntities()) {
            if (entity instanceof Player player) {
                if (plugin.getGodManager().isGod(player)) {
                    event.setIntensity(player, 0);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityCombust(EntityCombustEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (plugin.getGodManager().isGod(player)) {
                event.setCancelled(true);
                player.setFireTicks(0);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityTarget(EntityTargetLivingEntityEvent event) {
        Entity target = event.getTarget();
        if (target instanceof Player player) {
            if (plugin.getGodManager().isGod(player)) {
                event.setCancelled(true);
            }
        }
    }
}