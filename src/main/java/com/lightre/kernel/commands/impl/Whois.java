package com.lightre.kernel.commands.impl;

import com.lightre.kernel.Kernel;
import com.lightre.kernel.commands.base.AbstractCommand;
import com.lightre.kernel.utils.ChatUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Whois extends AbstractCommand {

    private final Kernel plugin;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Whois(Kernel plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            ChatUtils.sendMessage(sender, "&cUsage: /whois <player> [action]");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            ChatUtils.sendMessage(sender, "&cPlayer '" + args[0] + "' not found or is not online.");
            return true;
        }

        if (args.length > 1) {
            String action = args[1].toLowerCase();
            if (action.equals("teleport") || action.equals("-tp")) {
                return handleTeleportAction(sender, target);
            } else {
                ChatUtils.sendMessage(sender, "&cUnknown action '" + args[1] + "'.");
                return true;
            }
        }

        displayWhoisInfo(sender, target);
        return true;
    }

    private void displayWhoisInfo(CommandSender sender, Player target) {
        String name = target.getName();
        String uuid = target.getUniqueId().toString();
        String ipAddress = target.getAddress() != null ? target.getAddress().getAddress().getHostAddress() : "N/A";
        Location loc = target.getLocation();
        long firstPlayedTimestamp = target.getFirstPlayed();
        long playTicks = target.getStatistic(Statistic.PLAY_ONE_MINUTE);

        String healthDisplay = String.format("&a%.1f&7/&a%.1f", target.getHealth(), Objects.requireNonNull(target.getAttribute(Attribute.MAX_HEALTH)).getValue());
        String foodDisplay = String.format("&f%d&7/&f20", target.getFoodLevel());
        String gamemodeDisplay = formatGamemode(target.getGameMode());
        String locationDisplay = String.format("&b%s&7, X:&f%.1f&7, Y:&f%.1f&7, Z:&f%.1f", Objects.requireNonNull(loc.getWorld()).getName(), loc.getX(), loc.getY(), loc.getZ());
        String firstPlayed = (firstPlayedTimestamp > 0) ? dateFormat.format(new Date(firstPlayedTimestamp)) : "N/A";
        String playtime = formatPlaytime(playTicks);
        String opStatus = target.isOp() ? "&aYes" : "&cNo";
        String godStatus = plugin.getGodManager().isGod(target) ? "&aYes" : "&cNo";
        String vanishStatus = plugin.getVanishManager().isVanished(target) ? "&aYes" : "&cNo";

        sender.sendMessage(ChatUtils.colorize("&8&m----------&r&8[ &6Whois: &e" + name + " &8]&m----------"));
        sendClickableInfo(sender, "UUID", uuid, "Click to copy UUID", ClickEvent.Action.COPY_TO_CLIPBOARD, uuid);
        sendInfoLine(sender, "IP Address", ipAddress);
        sendInfoLine(sender, "Ping", target.getPing() + "ms");
        sender.sendMessage("");
        sendInfoLine(sender, "Health", healthDisplay);
        sendInfoLine(sender, "Food", foodDisplay);
        sendInfoLine(sender, "Gamemode", gamemodeDisplay);

        if (sender instanceof Player) {
            String teleportCommand = "/whois " + name + " teleport";
            sendClickableInfo(sender, "Location", locationDisplay, "Click to teleport to player", ClickEvent.Action.RUN_COMMAND, teleportCommand);
        } else {
            sendInfoLine(sender, "Location", locationDisplay);
        }

        sender.sendMessage("");
        sendInfoLine(sender, "Operator", opStatus);
        sendInfoLine(sender, "God Mode", godStatus);
        sendInfoLine(sender, "Vanished", vanishStatus);
        sender.sendMessage("");
        sendInfoLine(sender, "Playtime", playtime);
        sendInfoLine(sender, "First Joined", firstPlayed);
        sender.sendMessage(ChatUtils.colorize("&8&m----------------------------------------"));
    }

    private boolean handleTeleportAction(CommandSender sender, Player target) {
        if (!sender.hasPermission(getPermission() + ".teleport")) {
            ChatUtils.sendMessage(sender, "&cYou do not have permission to teleport to players.");
            return true;
        }
        if (!(sender instanceof Player)) {
            ChatUtils.sendMessage(sender, "&cOnly players can teleport.");
            return true;
        }

        ((Player) sender).teleport(target);
        ChatUtils.sendMessage(sender, "&aYou have been teleported to " + target.getName() + ".");
        return true;
    }

    private void sendClickableInfo(CommandSender sender, String label, String value, String hoverText, ClickEvent.Action clickAction, String clickValue) {
        TextComponent message = new TextComponent(ChatUtils.colorize(" &6» &e" + label + ": &f"));
        TextComponent clickablePart = new TextComponent(ChatUtils.colorize(value));
        clickablePart.setClickEvent(new ClickEvent(clickAction, clickValue));
        clickablePart.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatUtils.colorize("&e" + hoverText))));
        message.addExtra(clickablePart);
        sender.spigot().sendMessage(message);
    }

    private void sendInfoLine(CommandSender sender, String label, String value) {
        sender.sendMessage(ChatUtils.colorize(" &6» &e" + label + ": &f" + value));
    }

    private String formatPlaytime(long ticks) {
        long seconds = ticks / 20;
        long days = TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) % 24;
        long minutes = TimeUnit.SECONDS.toMinutes(seconds) % 60;
        return String.format("%dd %dh %dm", days, hours, minutes);
    }

    private String formatGamemode(GameMode gm) {
        String name = gm.toString();
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    @Override
    public String getName() {
        return "whois";
    }

    @Override
    public String getPermission() {
        return "kernel.admin.whois";
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}