package pl.yspar.cage.manager;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import pl.yspar.cage.basic.User;
import pl.yspar.cage.database.Database;
import pl.yspar.cage.manager.tops.TopDeathsManager;
import pl.yspar.cage.manager.tops.TopKillsManager;
import pl.yspar.cage.manager.tops.TopWinManager;
import pl.yspar.cage.tab.TabList;
import pl.yspar.cage.utils.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    private static final ConcurrentHashMap<String, User> users;

    static {
        users = new ConcurrentHashMap<String, User>();
    }

    public static User getUser(final String name) {
        for (final User u : UserManager.users.values()) {
            if (u.getName().equalsIgnoreCase(name)) {
                return u;
            }
        }
        return null;
    }

    public static void createrUser(final Player p) {
        final User u = new User(p);
        UserManager.users.put(p.getName(), u);
    }

    public static User getUser(final Player p) {
        for (final User u : UserManager.users.values()) {
            if (u.getName().equalsIgnoreCase(p.getName())) {
                return u;
            }
        }
        return null;
    }

    public static boolean isUser(final Player p) {
        return UserManager.users.containsKey(p.getName());
    }

    public static void deleteUser(final User u) {
        UserManager.users.remove(u.getName());
        Database.update(false, "DELETE FROM `{P}users` WHERE `name` = '" + u.getName() + "'");
    }

    public static void playerJoin(final Player player) {
        TabList.initialize(player.getUniqueId());
        TabList.update(player.getUniqueId());
        player.setGameMode(GameMode.SPECTATOR);
        CageManager.hidePlayer(player);
    }

    public static void loadUsers() {
        try {
            final ResultSet rs = Database.query("SELECT * FROM `{P}users`");
            while (rs.next()) {
                final User u = new User(rs);
                UserManager.users.put(u.getName(), u);
                TopDeathsManager.addDeaths(u);
                TopKillsManager.addKills(u);
                TopWinManager.addMedal(u);
            }
            rs.close();
            Logger.info("Loaded " + UserManager.users.size() + " players");
        } catch (SQLException e) {
            Logger.info("Can not load players Error " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static ConcurrentHashMap<String, User> getUsers() {
        return UserManager.users;
    }
}
