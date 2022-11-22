package pl.yspar.cage.tab;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.yspar.cage.basic.Cage;
import pl.yspar.cage.basic.Party;
import pl.yspar.cage.basic.User;
import pl.yspar.cage.config.Config;
import pl.yspar.cage.manager.CageManager;
import pl.yspar.cage.manager.PartyManager;
import pl.yspar.cage.manager.UserManager;
import pl.yspar.cage.manager.tops.TopDeathsManager;
import pl.yspar.cage.manager.tops.TopKillsManager;
import pl.yspar.cage.manager.tops.TopWinManager;
import pl.yspar.cage.utils.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TabList {
    private static final Map<UUID, Tab> tabMap;

    static {
        tabMap = new HashMap<UUID, Tab>();
    }

    public static void initialize(final UUID uuid) {
        final Player player = Bukkit.getPlayer(uuid);
        if (TabList.tabMap != null) {
            TabList.tabMap.remove(uuid);
        }
        final Tab tab = new Tab(player);
        TabList.tabMap.put(uuid, tab);
    }

    public static void update(final UUID uuid) {
        final Player player = Bukkit.getPlayer(uuid);
        final User user = UserManager.getUser(player);
        final Party p = PartyManager.getParty(player);
        final Tab t = TabList.tabMap.get(uuid);
        if (t != null) {
            t.set(0, 0, "");
            t.set(0, 1, "  ");
            t.set(0, 2, "   ");
            t.set(0, 3, "  &7(&fTopka Wygranych klatek&7)");
            t.set(0, 4, "");
            t.set(0, 5, getTopWin(1));
            t.set(0, 6, getTopWin(2));
            t.set(0, 7, getTopWin(3));
            t.set(0, 8, getTopWin(4));
            t.set(0, 9, getTopWin(5));
            t.set(0, 10, getTopWin(6));
            t.set(0, 11, getTopWin(7));
            t.set(0, 12, getTopWin(8));
            t.set(0, 13, getTopWin(9));
            t.set(0, 14, getTopWin(10));
            t.set(0, 15, getTopWin(11));
            t.set(0, 16, getTopWin(12));
            t.set(0, 17, getTopWin(13));
            t.set(0, 18, "                  ");
            t.set(1, 1, "");
            t.set(1, 1, " ");
            t.set(1, 2, "");
            t.set(1, 3, "  &7(&fTopka Zgonow&7)");
            t.set(1, 4, "");
            t.set(1, 5, getTopDeath(1));
            t.set(1, 6, getTopDeath(2));
            t.set(1, 7, getTopDeath(3));
            t.set(1, 8, getTopDeath(4));
            t.set(1, 9, getTopDeath(5));
            t.set(1, 10, getTopDeath(6));
            t.set(1, 11, getTopDeath(7));
            t.set(1, 12, getTopDeath(8));
            t.set(1, 13, getTopDeath(9));
            t.set(1, 14, getTopDeath(10));
            t.set(1, 15, getTopDeath(11));
            t.set(1, 16, getTopDeath(12));
            t.set(1, 17, getTopDeath(13));
            t.set(1, 18, "");
            t.set(2, 0, "");
            t.set(2, 1, "");
            t.set(2, 2, "");
            t.set(2, 3, "  &7(&fInformacje&7)");
            t.set(2, 4, "");
            t.set(2, 5, "&7>> &7Wygrane klatki&8: " + Config.getInstance().tablist.mainColor + user.getWins());
            t.set(2, 6, "&7>> &7Ostatnio wygral&8: " + Config.getInstance().tablist.mainColor + Cage.lastwin);
            t.set(2, 7, "&7>> &7Zabojstwa&8: " + Config.getInstance().tablist.mainColor + user.getKills());
            t.set(2, 8, "&7>> &7Zgony&8: " + Config.getInstance().tablist.mainColor + user.getDeaths());
            t.set(2, 9, "&7>> &7Status gry&8: " + Config.getInstance().tablist.mainColor + Cage.statusCage);
            t.set(2, 10, "&7>> &7Organizator&8: " + Config.getInstance().tablist.mainColor + Cage.organizer);
            t.set(2, 11, "&7>> &7ile vs ile&8: " + Config.getInstance().tablist.mainColor + Cage.xvsx + "vs" + Cage.xvsx);
            t.set(2, 12, "");
            t.set(2, 13, "  &7(&fTwoje party&7)");
            t.set(2, 14, "");
            t.set(2, 15, "&7>> " + ((p == null) ? "&7Nazwa&8: &cBrak" : ("&7Nazwa&8: " + Config.getInstance().tablist.mainColor + p.getTag())));
            t.set(2, 16, "&7>> " + ((p == null) ? "&7Lider&8: &cBrak" : ("&7Lider&8: " + Config.getInstance().tablist.mainColor + p.getOwner())));
            t.set(2, 17, "&7>> " + ((p == null) ? "&7Graczy&8: &cBrak" : ("&7Graczy&8: " + Config.getInstance().tablist.mainColor + p.getMembers().size())));
            t.set(2, 18, "&7>> " + ((p == null) ? "&7Lista&8: &cBrak" : ("&7Lista&8: " + Config.getInstance().tablist.mainColor + StringUtils.join((Object[]) CageManager.getMemberList(p.getMembers()), "&8, "))));
            t.set(3, 0, "");
            t.set(3, 1, "");
            t.set(3, 2, "");
            t.set(3, 3, "   &7(&fTopka Zabojstw&7)");
            t.set(3, 4, " ");
            t.set(3, 5, getTopKill(1));
            t.set(3, 6, getTopKill(2));
            t.set(3, 7, getTopKill(3));
            t.set(3, 8, getTopKill(4));
            t.set(3, 9, getTopKill(5));
            t.set(3, 10, getTopKill(6));
            t.set(3, 11, getTopKill(7));
            t.set(3, 12, getTopKill(8));
            t.set(3, 13, getTopKill(9));
            t.set(3, 14, getTopKill(10));
            t.set(3, 15, getTopKill(11));
            t.set(3, 16, getTopKill(12));
            t.set(3, 17, getTopKill(13));
            t.set(3, 18, "                   ");
            t.set(Config.getInstance().tablist.tablistHeader, Config.getInstance().tablist.tablistFooter.replace("{ONLINE}", Integer.toString(Bukkit.getOnlinePlayers().size())));
        }
    }

    public static String getTopWin(final Integer i) {
        if (TopWinManager.getMedals().size() >= i) {
            String s;
            if (i == 1) {
                s = "&6" + i + ". &7";
            } else if (i == 2) {
                s = "&6" + i + ". &7";
            } else if (i == 3) {
                s = "&6" + i + ". &7";
            } else {
                s = "&7" + i + ". &7";
            }
            return Util.fixColor(s + (TopWinManager.getMedals().get(i - 1).isOnline() ? "&a\u2666 &7" : "&c\u2666 &7") + TopWinManager.getMedals().get(i - 1).getName() + " &6" + TopWinManager.getMedals().get(i - 1).getWins());
        }
        return "&7" + i + ".";
    }

    public static String getTopDeath(final Integer i) {
        if (TopDeathsManager.getDeathss().size() >= i) {
            String s;
            if (i == 1) {
                s = "&6" + i + ". &7";
            } else if (i == 2) {
                s = "&6" + i + ". &7";
            } else if (i == 3) {
                s = "&6" + i + ". &7";
            } else {
                s = "&7" + i + ". &7";
            }
            return Util.fixColor(s + (TopDeathsManager.getDeathss().get(i - 1).isOnline() ? "&a\u2666 &7" : "&c\u2666 &7") + TopDeathsManager.getDeathss().get(i - 1).getName() + " &6" + TopDeathsManager.getDeathss().get(i - 1).getDeaths());
        }
        return "&7" + i + ".";
    }

    public static String getTopKill(final Integer i) {
        if (TopKillsManager.getKillss().size() >= i) {
            String s;
            if (i == 1) {
                s = "&6" + i + ". &7";
            } else if (i == 2) {
                s = "&6" + i + ". &7";
            } else if (i == 3) {
                s = "&6" + i + ". &7";
            } else {
                s = "&7" + i + ". &7";
            }
            return Util.fixColor(s + (TopKillsManager.getKillss().get(i - 1).isOnline() ? "&a\u2666 &7" : "&c\u2666 &7") + TopKillsManager.getKillss().get(i - 1).getName() + " &6" + TopKillsManager.getKillss().get(i - 1).getKills());
        }
        return "&7" + i + ".";
    }
}
