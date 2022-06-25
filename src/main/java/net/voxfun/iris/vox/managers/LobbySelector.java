
package net.voxfun.iris.vox.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.Material.COMPASS;

public class LobbySelector implements Listener {
    public LobbySelector(Player player) {
        if (player == null) return;
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            if (player.getInventory().getItem(i) != null) {
                if (player.getInventory().getItem(i).getItemMeta().getDisplayName().equalsIgnoreCase("server navigator"))
                    return;
            }
        }
        ItemStack item = new ItemStack(COMPASS);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Server Navigator");
        item.setItemMeta(meta);
        player.getInventory().addItem(item);
    }

    @EventHandler
    public static void onClick(PlayerInteractEvent event) {
        Action a = event.getAction();
        ItemStack item = event.getItem();
        if (a == Action.PHYSICAL || item == null || item.getType() == Material.AIR) return;
        if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
            if (item.getType() == COMPASS && item.getItemMeta().getDisplayName().equalsIgnoreCase("server navigator")) {
                openGUI(event.getPlayer());
            }
        }
    }

    @EventHandler
    public static void selectGame(InventoryClickEvent event) {
        if (event.getClickedInventory().getSize() > 9) return;
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta())
            return;
        if (event.getCurrentItem().getType() == Material.BOW && event.isLeftClick()) {
            //Sends to the Recon game room

            Location recon = new Location(Bukkit.getWorld("world"), -0.5, 161, 127.5, 0, 0);
            player.teleport(recon);

            player.closeInventory();
        }

        if (event.getCurrentItem().getType() == Material.BOW && event.isRightClick()) {
            //Sends to the Recon game room

            Location recon = new Location(Bukkit.getWorld("world"), -0.5, 161, 127.5, 0, 0);
            player.teleport(recon);

            player.closeInventory();
        }

        if (event.getCurrentItem().getType() == Material.BOW && event.isShiftClick()) {
            //Sends them to available Recon lobby
            if (event.getCurrentItem().getType() == Material.BOW) {
                player.performCommand("play recon");
                player.closeInventory();
            }
        }
    }

        private static void openGUI(Player player) {
            Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 9, ChatColor.UNDERLINE + "Server Navigator");
            ItemStack RECON = new ItemStack(Material.BOW);
            ItemMeta RECON_META = RECON.getItemMeta();
            RECON_META.setDisplayName(ChatColor.BOLD + "Recon");
            RECON.setItemMeta(RECON_META);
            inventory.setItem(0, RECON);
            player.openInventory(inventory);
        }
    }

