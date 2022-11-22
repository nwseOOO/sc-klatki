package pl.yspar.cage.manager;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import pl.yspar.cage.basic.Cage;
import pl.yspar.cage.basic.Party;
import pl.yspar.cage.basic.User;
import pl.yspar.cage.config.Config;
import pl.yspar.cage.config.LocationCage;
import pl.yspar.cage.utils.Util;

import java.util.Iterator;
import java.util.Set;

public class CageManager {
    public static void start() {
        for (final Party g : PartyManager.party.values()) {
            g.removeFightAs(1);
        }
        for (final Player pb : Bukkit.getOnlinePlayers()) {
            if (Cage.partySave.size() == 1 && Cage.partyPass.size() == 0) {
                final Party partyOne = PartyManager.getParty(Cage.partySave.get(0));
                final Iterator<Player> iterator3 = partyOne.getCageMembers().iterator();
                if (iterator3.hasNext()) {
                    final Player px = iterator3.next();
                    final User userWin = UserManager.getUser(px);
                    checkWin(userWin, pb, Config.getInstance().titlemessages.winDontHaveOpponentTitle, Config.getInstance().titlemessages.winDontHaveOpponentSubTitle.replace("{PARTY}", partyOne.getTag()).replace("{MEMBERS}", StringUtils.join((Object[]) getMemberList(partyOne.getMembers()), "&8, ")));
                    stop();
                    return;
                }
            }
            if (Cage.partySave.size() >= 2) {
                final Party partyOne = PartyManager.getParty(Cage.partySave.get(0));
                final Party partyTwo = PartyManager.getParty(Cage.partySave.get(1));
                if (partyOne.getCageMembers().size() == Cage.xvsx) {
                    if (partyTwo.getCageMembers().size() == Cage.xvsx) {
                        for (final Player pOne : partyOne.getCageMembers()) {
                            final Iterator<Player> iterator5 = partyTwo.getCageMembers().iterator();
                            if (iterator5.hasNext()) {
                                final Player pTwo = iterator5.next();
                                pOne.teleport(LocationCage.TEAM1);
                                pTwo.teleport(LocationCage.TEAM2);
                                //final Party partyOne2;
                                Bukkit.getOnlinePlayers().forEach(playerOnline -> Util.sendTitleMessage(playerOnline, "", Util.fixColor(Config.getInstance().titlemessages.nowFighting).replace("{TEAM2}", partyTwo.getTag()).replace("{TEAM1}", partyOne.getTag()), 0, 30, 0));
                                fight(pOne);
                                fight(pTwo);
                                pOne.showPlayer(pTwo);
                                pTwo.showPlayer(pOne);
                                Bukkit.getOnlinePlayers().forEach(playerOnline -> playerOnline.showPlayer(pOne));
                                Bukkit.getOnlinePlayers().forEach(playerOnline -> playerOnline.showPlayer(pTwo));
                                partyOne.setFightingNow(true);
                                partyTwo.setFightingNow(true);
                                return;
                            }
                        }
                    } else {
                        Cage.partySave.remove(partyOne.getTag());
                    }
                    Cage.partyPass.remove(partyOne.getTag());
                    start();
                    return;
                }
                Cage.partySave.remove(partyTwo.getTag());
                Cage.partyPass.remove(partyTwo.getTag());
                start();
            }
        }
    }

    public static String[] getMemberList(final Set<String> members) {
        final String[] s = new String[members.size()];
        int i = 0;
        for (final String x : members) {
            final OfflinePlayer op = Bukkit.getOfflinePlayer(x);
            final User u = UserManager.getUser(x);
            s[i] = "&2" + (op.isOnline() ? "&2" : "&c") + op.getName();
            ++i;
        }
        return s;
    }

    public static void fight(final Player p) {
        p.setGameMode(GameMode.SURVIVAL);
        p.setFireTicks(0);
        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(20);
        p.getInventory().clear();
        p.getPlayer().removePotionEffect(PotionEffectType.ABSORPTION);
        p.getPlayer().removePotionEffect(PotionEffectType.REGENERATION);
        EquipManager.give(p);
    }

    public static void checkWin(final User u, final Player player, final String one, final String two) {
        u.addwin(1);
        Util.sendTitleMessage(player, Util.fixColor(one), Util.fixColor(two), 0, 70, 0);
    }

    public static void stop() {
        Cage.loadCage();
        PartyManager.deleteAll();
        Bukkit.getScheduler().cancelTask(Cage.task);
        Cage.i = -1;
    }

    public static void HideAllPlayer() {
        final Party g = PartyManager.getParty(Cage.partySave.get(0));
        final Party g2 = PartyManager.getParty(Cage.partySave.get(1));
        for (final Player pl : g.getCageMembers()) {
            for (final Player pl2 : g2.getCageMembers()) {
                for (final Player pb : Bukkit.getOnlinePlayers()) {
                    pb.setGameMode(GameMode.SPECTATOR);
                    pb.hidePlayer(pl2);
                    pb.hidePlayer(pl);
                    pb.hidePlayer(pb);
                    pl2.hidePlayer(pl);
                    pl.hidePlayer(pl2);
                }
            }
        }
    }

    public static void hidePlayer(final Player p) {
        for (final Player pb : Bukkit.getOnlinePlayers()) {
            p.hidePlayer(pb);
            pb.hidePlayer(p);
        }
    }
}
