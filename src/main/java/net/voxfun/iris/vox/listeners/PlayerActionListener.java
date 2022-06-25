package net.voxfun.iris.vox.listeners;

import net.voxfun.iris.vox.index;
import net.voxfun.iris.vox.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerActionListener implements Listener{

    @EventHandler
    public static void onInventoryDrop(PlayerDropItemEvent event) {
        GameMode gameMode = event.getPlayer().getGameMode();
        if (index.isMainLobby && gameMode.equals(GameMode.ADVENTURE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public static void onInteractEvent(PlayerInteractEvent event) {
        GameMode gameMode = event.getPlayer().getGameMode();
        if (index.isMainLobby && gameMode.equals(GameMode.ADVENTURE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) {
        GameMode gameMode = event.getWhoClicked().getGameMode();
        if (index.isMainLobby && gameMode.equals(GameMode.ADVENTURE)) {
            event.setCancelled(true);
        }
    }
}


