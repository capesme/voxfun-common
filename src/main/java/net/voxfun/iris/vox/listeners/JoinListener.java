package net.voxfun.iris.vox.listeners;

import net.voxfun.iris.vox.index;
import net.voxfun.iris.vox.managers.LobbySelector;
import net.voxfun.iris.vox.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public static void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (index.isMainLobby) {
            event.setJoinMessage("");
            Bukkit.broadcastMessage(String.format("%s%s has joined the lobby.", PlayerManager.getRank(player.getUniqueId().toString()), player.getName()));
            new LobbySelector(player);
            player.setGameMode(GameMode.ADVENTURE);
            player.setInvulnerable(false);
        }
        PlayerManager.join(event.getPlayer().getUniqueId().toString());
    }
}
