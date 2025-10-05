package com.lightre.kernel.listeners;

import com.lightre.kernel.Kernel;
import com.lightre.kernel.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerFreezeListener implements Listener {

    private final Kernel plugin;
    // YENİ: Mesaj cooldown'ında olan oyuncuları takip etmek için bir Set.
    private final Set<UUID> messageCooldown = new HashSet<>();

    public PlayerFreezeListener(Kernel plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (plugin.getFreezeManager().isFrozen(player)) {
            event.setCancelled(true);

            // YENİ OPTİMİZASYON MANTIĞI:
            // Eğer oyuncu zaten cooldown listesinde ise, hiçbir şey yapma.
            if (messageCooldown.contains(player.getUniqueId())) {
                return;
            }

            // Oyuncu cooldown'da değilse, mesajı gönder ve cooldown'a ekle.
            ChatUtils.sendMessage(player, "&cYou are frozen and cannot move.");
            messageCooldown.add(player.getUniqueId());

            // 2 saniye (40 tick) sonra oyuncuyu cooldown'dan çıkaracak bir görev planla.
            new BukkitRunnable() {
                @Override
                public void run() {
                    messageCooldown.remove(player.getUniqueId());
                }
            }.runTaskLater(plugin, 40L); // 20 tick = 1 saniye
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.getFreezeManager().isFrozen(player)) {
            event.setCancelled(true);

            // Aynı optimizasyonu sohbet için de uyguluyoruz.
            if (messageCooldown.contains(player.getUniqueId())) {
                return;
            }

            ChatUtils.sendMessage(player, "&cYou are frozen and cannot chat.");
            messageCooldown.add(player.getUniqueId());

            new BukkitRunnable() {
                @Override
                public void run() {
                    messageCooldown.remove(player.getUniqueId());
                }
            }.runTaskLater(plugin, 40L);
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (plugin.getFreezeManager().isFrozen(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        // Oyuncu çıktığında, onu hem freeze listesinden hem de cooldown listesinden temizle.
        if (plugin.getFreezeManager().isFrozen(player)) {
            plugin.getFreezeManager().toggleFreeze(player);
        }
        messageCooldown.remove(player.getUniqueId());
    }
}