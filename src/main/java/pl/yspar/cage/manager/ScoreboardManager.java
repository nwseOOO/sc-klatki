package pl.yspar.cage.manager;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.entity.Player;
import pl.yspar.cage.basic.Cage;
import pl.yspar.cage.basic.Party;
import pl.yspar.cage.basic.User;
import pl.yspar.cage.config.Config;
import pl.yspar.cage.utils.Util;

public class ScoreboardManager {
    public static void createSidebar(final Player player) {
        final User u = UserManager.getUser(player);
        final Party p = PartyManager.getParty(player);
        final BPlayerBoard board = Netherboard.instance().createBoard(player, "sidebarr");
        board.setName(Util.fixColor(Config.getInstance().sidebar.header));
        board.set(Util.fixColor("&3"), 8);
        board.set(Util.fixColor(Config.getInstance().sidebar.line1.replace("{SAVE}", (p != null) ? p.isSaveEvent(p) : "Nie")), 7);
        board.set(Util.fixColor(Config.getInstance().sidebar.line2.replace("{PING}", Integer.toString(u.getPing()))), 6);
        board.set(Util.fixColor(Config.getInstance().sidebar.line3.replace("{FIGHTAS}", (p != null) ? Integer.toString(p.getFightAs()) : "Brak")), 5);
        board.set(Util.fixColor(Config.getInstance().sidebar.line4.replace("{PARTY}", (p != null) ? p.getTag() : "Brak")), 4);
        board.set(Util.fixColor(" &d"), 3);
        board.set(Util.fixColor(Config.getInstance().sidebar.line5.replace("{SIZE}", Integer.toString(Cage.partySave.size()))), 2);
        board.set(Util.fixColor(Config.getInstance().sidebar.line6.replace("{SIZE}", Integer.toString(Cage.partyPass.size()))), 1);
        board.set(Util.fixColor(" &e"), 0);
    }

    public static void updateSidebar(final Player player) {
        final User u = UserManager.getUser(player);
        final Party p = PartyManager.getParty(player);
        final BPlayerBoard board = Netherboard.instance().getBoard(player);
        board.set(Util.fixColor("&3"), 8);
        board.set(Util.fixColor(Config.getInstance().sidebar.line1.replace("{SAVE}", (p != null) ? p.isSaveEvent(p) : "Nie")), 7);
        board.set(Util.fixColor(Config.getInstance().sidebar.line2.replace("{PING}", Integer.toString(u.getPing()))), 6);
        board.set(Util.fixColor(Config.getInstance().sidebar.line3.replace("{FIGHTAS}", (p != null) ? Integer.toString(p.getFightAs()) : "Brak")), 5);
        board.set(Util.fixColor(Config.getInstance().sidebar.line4.replace("{PARTY}", (p != null) ? p.getTag() : "Brak")), 4);
        board.set(Util.fixColor(" &d"), 3);
        board.set(Util.fixColor(Config.getInstance().sidebar.line5.replace("{SIZE}", Integer.toString(Cage.partySave.size()))), 2);
        board.set(Util.fixColor(Config.getInstance().sidebar.line6.replace("{SIZE}", Integer.toString(Cage.partyPass.size()))), 1);
        board.set(Util.fixColor(" &e"), 0);
    }
}
