package com.lightre.kernel.listeners;

import com.lightre.kernel.Kernel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {

    private final Kernel plugin;

    public PlayerDamageListener(Kernel plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        // Eğer hasar alan varlık bir oyuncu değilse, bizi ilgilendirmez.
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        // Eğer oyuncu god modunda ise, gelen hasar olayını iptal et.
        if (plugin.getGodManager().isGod(player)) {
            event.setCancelled(true);
        }
    }
}