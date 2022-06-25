package net.voxfun.iris.vox.listeners;

import net.voxfun.iris.vox.index;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener{
    @EventHandler
    public void onPlayerWalk(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (index.isMainLobby) {

            Location blockUnder = player.getLocation();
            blockUnder.setY(blockUnder.getY() - 1);

            //Changing blocks

            final BlockState state = blockUnder.getBlock().getState();

            int delay = 20;

            //Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            //    @Override
            //    public void run() {
            //        state.update(true);
            //    }
            //}, delay);


            //Launch Pads

            if (player.getLocation().getBlock().getType().equals((Material.LIGHT_WEIGHTED_PRESSURE_PLATE)) && blockUnder.getBlock().getType().equals(Material.JACK_O_LANTERN)) {
                player.setVelocity(player.getLocation().getDirection().multiply(3).setY(0.7));
                player.playSound(player.getLocation(), Sound.ENTITY_WOLF_SHAKE, 10, 1);
            }
            if (player.getLocation().getBlock().getType().equals((Material.LIGHT_WEIGHTED_PRESSURE_PLATE)) && blockUnder.getBlock().getType().equals(Material.STRUCTURE_BLOCK)) {
                player.setVelocity(player.getLocation().getDirection().multiply(7).setY(2));
                player.setVelocity(player.getLocation().getDirection().multiply(3).setY(1));
                player.playSound(player.getLocation(), Sound.ENTITY_WOLF_SHAKE, 10, 1);
            }

            if (blockUnder.getBlock().getType().equals(Material.CYAN_TERRACOTTA)) {
                blockUnder.getBlock().setType(Material.BLUE_TERRACOTTA);
                }
            if (blockUnder.getBlock().getType().equals(Material.BLUE_TERRACOTTA)) {
                blockUnder.getBlock().setType(Material.PURPLE_TERRACOTTA);
            }
            if (blockUnder.getBlock().getType().equals(Material.PURPLE_TERRACOTTA)) {
                blockUnder.getBlock().setType(Material.MAGENTA_TERRACOTTA);
            }
            if (blockUnder.getBlock().getType().equals(Material.MAGENTA_TERRACOTTA)) {
                blockUnder.getBlock().setType(Material.PINK_TERRACOTTA);
            }
            if (blockUnder.getBlock().getType().equals(Material.PINK_TERRACOTTA)) {
                blockUnder.getBlock().setType(Material.RED_TERRACOTTA);
            }
            if (blockUnder.getBlock().getType().equals(Material.RED_TERRACOTTA)) {
                blockUnder.getBlock().setType(Material.ORANGE_TERRACOTTA);
            }
            if (blockUnder.getBlock().getType().equals(Material.ORANGE_TERRACOTTA)) {
                blockUnder.getBlock().setType(Material.YELLOW_TERRACOTTA);
            }
            if (blockUnder.getBlock().getType().equals(Material.YELLOW_TERRACOTTA)) {
            }

        }
    }
}