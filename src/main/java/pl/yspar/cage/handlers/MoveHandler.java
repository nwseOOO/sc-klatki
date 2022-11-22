package pl.yspar.cage.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.yspar.cage.config.LocationCage;

public class MoveHandler implements Listener {
    @EventHandler
    public static void onMove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (event.getTo().getBlockY() < -40 || event.getTo().getBlockY() > 160) {
            player.teleport(LocationCage.LOBBY);
        }
    }
}
