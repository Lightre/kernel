package com.lightre.kernel.commands.impl;

import com.lightre.kernel.commands.base.AbstractCommand;
import com.lightre.kernel.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class Repair extends AbstractCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            ChatUtils.sendMessage(sender, "&cThis command can only be used by players.");
            return true;
        }

        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemInHand.getItemMeta();

        if (itemInHand.getType() == Material.AIR || !(itemMeta instanceof Damageable damageableMeta)) {
            ChatUtils.sendMessage(player, "&cThe item in your hand cannot be repaired.");
            return true;
        }

        if (damageableMeta.getDamage() == 0) {
            ChatUtils.sendMessage(player, "&eThe item in your hand is already at full durability.");
            return true;
        }

        damageableMeta.setDamage(0);
        itemInHand.setItemMeta(damageableMeta);

        ChatUtils.sendMessage(player, "&aThe item in your hand has been successfully repaired!");
        return true;
    }

    @Override
    public String getName() {
        return "repair";
    }

    @Override
    public String getPermission() {
        return "kernel.admin.repair";
    }
}