        package net.voxfun.iris.vox.listeners;

        import net.voxfun.iris.vox.index;
        import org.bukkit.Location;
        import org.bukkit.Material;
        import org.bukkit.Sound;
        import org.bukkit.entity.Player;
        import org.bukkit.event.EventHandler;
        import org.bukkit.event.Listener;
        import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener{
    @EventHandler
    public void onPlyerWalk(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if (index.isMainLobby) {
            Location blockUnder = p.getLocation();
            blockUnder.setY(blockUnder.getY() - 1);
            if (p.getLocation().getBlock().getType().equals((Material.LIGHT_WEIGHTED_PRESSURE_PLATE)) && blockUnder.getBlock().getType().equals(Material.JACK_O_LANTERN)) {
                p.setVelocity(p.getLocation().getDirection().multiply(3).setY(0.7));
                p.playSound(p.getLocation(), Sound.ENTITY_WOLF_SHAKE, 10, 1);
            }
            if (p.getLocation().getBlock().getType().equals((Material.LIGHT_WEIGHTED_PRESSURE_PLATE)) && blockUnder.getBlock().getType().equals(Material.STRUCTURE_BLOCK)) {
                p.setVelocity(p.getLocation().getDirection().multiply(7).setY(2));
                p.setVelocity(p.getLocation().getDirection().multiply(3).setY(1));
                p.playSound(p.getLocation(), Sound.ENTITY_WOLF_SHAKE, 10, 1);
            }
        }
    }
}