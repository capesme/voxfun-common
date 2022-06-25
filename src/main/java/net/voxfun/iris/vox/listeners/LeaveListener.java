package net.voxfun.iris.vox.listeners;

import net.voxfun.iris.vox.index;
import net.voxfun.iris.vox.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {
    @EventHandler
    public static void onLeave(PlayerQuitEvent event) {
        if (index.isMainLobby) {
            event.setQuitMessage("");
            Bukkit.broadcastMessage(String.format("%s%s has quit the lobby.", PlayerManager.getRank(event.getPlayer().getUniqueId().toString()), event.getPlayer().getName()));
        }
        PlayerManager.leave(event.getPlayer().getUniqueId().toString());
    }
}
