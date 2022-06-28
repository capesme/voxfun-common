package net.voxfun.iris.vox.listeners;

import net.voxfun.iris.vox.index;
import net.voxfun.iris.vox.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public static void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        event.setFormat(PlayerManager.getRank(player.getUniqueId().toString()) + "" + ChatColor.WHITE + "" + player.getName() + ": " + message);
        event.setMessage(message);
    }
}
