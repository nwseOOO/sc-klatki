package pl.yspar.cage.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.yspar.cage.CagePlugin;
import pl.yspar.cage.basic.Cage;
import pl.yspar.cage.basic.Party;
import pl.yspar.cage.basic.User;
import pl.yspar.cage.config.LocationCage;
import pl.yspar.cage.manager.PartyManager;
import pl.yspar.cage.manager.ScoreboardManager;
import pl.yspar.cage.manager.UserManager;
import pl.yspar.cage.manager.WalkoverManager;
import pl.yspar.cage.types.CageType;
import pl.yspar.cage.utils.TagUtil;

public class JoinQuitHandler implements Listener {
    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player p = event.getPlayer();
        event.setJoinMessage(null);
        final User user = UserManager.getUser(p.getPlayer());
        if (user == null) {
            UserManager.createrUser(p);
        }
        p.teleport(LocationCage.LOBBY);
        UserManager.playerJoin(p);
        ScoreboardManager.createSidebar(p);
        final Player player;
        Bukkit.getScheduler().runTaskAsynchronously(CagePlugin.getPlugin(), () -> {
            try {
                TagUtil.createBoard(p);
            } catch (Exception ex) {
                TagUtil.refreshAll();
                ScoreboardManager.updateSidebar(p);
            }
        });
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final Player p = event.getPlayer();
        event.setQuitMessage((String) null);
        TagUtil.removeBoard(p);
        final Party party = PartyManager.getParty(p);
        if (party != null) {
            if (party.isFightingNow() && Cage.statusCage == CageType.INGAME) {
                WalkoverManager.quitWalkover(p);
                return;
            }
            if (Cage.partySave.contains(party.getTag())) {
                if (party.isOwner(p) && !party.isFightingNow()) {
                    Cage.partySave.remove(party.getTag());
                }
                party.removeMember(p);
                return;
            }
            if (Cage.partyPass.contains(party.getTag())) {
                if (party.isOwner(p) && !party.isFightingNow()) {
                    Cage.partyPass.remove(party.getTag());
                }
                party.removeMember(p);
            }
        }
    }
}
