package pl.yspar.cage.handlers;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import pl.yspar.cage.basic.Cage;
import pl.yspar.cage.basic.Party;
import pl.yspar.cage.basic.User;
import pl.yspar.cage.config.Config;
import pl.yspar.cage.config.LocationCage;
import pl.yspar.cage.manager.PartyManager;
import pl.yspar.cage.manager.UserManager;
import pl.yspar.cage.utils.Util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PlayerDeathHandler implements Listener {
    public boolean removePoints(final Player p) {
        final Party g1 = PartyManager.getParty(p);
        g1.removePoints(1);
        return false;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDeath(final PlayerDeathEvent e) {
        e.getDrops().clear();
        e.setDeathMessage((String) null);
        final Player p = e.getEntity();
        final Player k = p.getKiller();
        BigDecimal bigDecimal = new BigDecimal(k.getHealth() / 2.0);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        final double health = bigDecimal.doubleValue();
        final User uKiller = UserManager.getUser(k);
        final User uPlayer = UserManager.getUser(p);
        final Party partyKiller = PartyManager.getParty(k);
        final Party partyEntity = PartyManager.getParty(p);
        this.removePoints(p);
        p.teleport(LocationCage.LOBBY);
        p.setGameMode(GameMode.SPECTATOR);
        p.getInventory().clear();
        p.getInventory().setHelmet((ItemStack) null);
        p.getInventory().setChestplate((ItemStack) null);
        p.getInventory().setLeggings((ItemStack) null);
        p.getInventory().setBoots((ItemStack) null);
        uKiller.addKills(1);
        uPlayer.addDeaths(1);
        partyEntity.setFightAs(0);
        partyKiller.setFightAs(Cage.partyPass.size());
        if (partyKiller.getFightAs() < 1) {
            partyKiller.setFightAs(0);
        }
        Util.sendTitleMessage(k, "", Config.getInstance().titlemessages.killTitle.replace("{PLAYER}", p.getName()), 0, 20, 0);
        Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.killMessages).replace("{KILLER}", k.getDisplayName()).replace("{PLAYER}", p.getDisplayName()).replace("{HP}", Double.toString(health)));
        for (final Player pbs : Bukkit.getOnlinePlayers()) {
            pbs.hidePlayer(p);
        }
        if (partyEntity.getPoints() == 0) {
            for (final Player x : partyKiller.getCageMembers()) {
                x.setGameMode(GameMode.SPECTATOR);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onRespawn(final PlayerRespawnEvent e) {
        final Player p = e.getPlayer();
        e.setRespawnLocation(LocationCage.LOBBY);
        p.teleport(LocationCage.LOBBY);
    }
}
