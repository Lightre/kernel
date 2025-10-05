package com.lightre.kernel.commands.impl;

import com.lightre.kernel.commands.base.AbstractCommand;
import com.lightre.kernel.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminChat extends AbstractCommand {

    private final String chatPermission = "kernel.admin.adminchat.receive";

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            ChatUtils.sendMessage(sender, "&cUsage: /adminchat <message>");
            return true;
        }

        String message = String.join(" ", args);
        String senderName = sender.getName();

        String format = "&4&l[AC] &c" + senderName + " &7» &r" + message;
        String formattedMessage = ChatUtils.colorize(format);

        // Sunucudaki tüm online oyuncuları döngüye al
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            // Eğer oyuncunun mesajı görme yetkisi varsa, mesajı ona gönder
            if (onlinePlayer.hasPermission(chatPermission)) {
                onlinePlayer.sendMessage(formattedMessage);
            }
        }

        // Mesajı konsola da gönder, çünkü konsol her zaman yetkilidir.
        Bukkit.getConsoleSender().sendMessage(formattedMessage);

        return true;
    }

    @Override
    public String getName() {
        return "adminchat";
    }

    @Override
    public String getPermission() {
        // Bu, komutu kullanma (mesaj gönderme) yetkisidir.
        return "kernel.admin.adminchat.send";
    }

    // Bu komut serbest metin aldığı için onTabComplete'e gerek yok.
}