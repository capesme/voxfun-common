package net.voxfun.iris.vox.listeners;

import net.voxfun.iris.vox.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public static void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Bukkit.broadcastMessage(String.format("%s%s: %s", PlayerManager.getRank(e.getPlayer().getUniqueId().toString()), e.getPlayer().getName(), e.getMessage()));
    }
}
