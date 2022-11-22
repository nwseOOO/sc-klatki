package pl.yspar.cage.manager;

import org.bukkit.entity.Player;
import pl.yspar.cage.basic.Cage;
import pl.yspar.cage.basic.Party;
import pl.yspar.cage.basic.User;
import pl.yspar.cage.database.Database;
import pl.yspar.cage.utils.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PartyManager {
    public static final ConcurrentHashMap<String, Party> party;
    private static List<User> online;

    static {
        party = new ConcurrentHashMap<String, Party>();
    }

    public static Party getParty(final Player p) {
        for (final Party g : PartyManager.party.values()) {
            if (g.isMember(p)) {
                return g;
            }
        }
        return null;
    }

    public static List<User> getOnline() {
        return new ArrayList<User>(PartyManager.online);
    }

    public static Party getParty(final User user) {
        for (final Party g : PartyManager.party.values()) {
            if (g.isMember(user)) {
                return g;
            }
        }
        return null;
    }

    public static Party getGuildByNamePlayer(final String name) {
        for (final Party g : PartyManager.party.values()) {
            if (g.isMember(name)) {
                return g;
            }
        }
        return null;
    }

    public static Party getParty(final String name) {
        for (final Party g : PartyManager.party.values()) {
            if (g.getName().equalsIgnoreCase(name) || g.getTag().equalsIgnoreCase(name)) {
                return g;
            }
        }
        return null;
    }

    public static Party createParty(final String tag, final String name, final Player owner) {
        final Party g = new Party(tag, name, owner);
        PartyManager.party.put(tag, g);
        return g;
    }

    public static void deleteAll() {
        for (final Party g : PartyManager.party.values()) {
            deleteParty(g);
        }
    }

    public static void deleteParty(final Party g) {
        PartyManager.party.remove(g.getTag());
        Database.update(false, "DELETE FROM `{P}party` WHERE `tag` = '" + g.getTag() + "'");
        Database.update(false, "DELETE FROM `{P}members` WHERE `tag` = '" + g.getTag() + "'");
        Cage.partySave.remove(g.getTag());
        Cage.partyPass.remove(g.getTag());
    }

    public static void loadParty() {
        try {
            final ResultSet rs = Database.query("SELECT * FROM `{P}party`");
            while (rs.next()) {
                final Party g = new Party(rs);
                PartyManager.party.put(g.getTag(), g);
            }
            rs.close();
            Logger.info("Loaded " + PartyManager.party.size() + " party");
        } catch (SQLException e) {
            Logger.info("Can not load guild ERROR: " + e.getMessage());
        }
    }

    public static ConcurrentHashMap<String, Party> getparty() {
        return PartyManager.party;
    }
}
