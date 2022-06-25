package net.voxfun.iris.vox.commands;

import net.voxfun.iris.vox.index;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LobbyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (index.isMainLobby) {
            sender.sendMessage(ChatColor.RED + "You are already connected to the lobby!");
            return true;
        }
        Player player = (Player) sender;
        Bukkit.getMessenger().registerOutgoingPluginChannel(index.plugin, "BungeeCord");
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF("lobby");
        } catch (IOException ignored) {}
        player.sendPluginMessage(index.plugin, "BungeeCord", b.toByteArray());
        return true;
    }
}
