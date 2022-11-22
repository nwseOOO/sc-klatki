package pl.yspar.cage.basic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.yspar.cage.database.Database;
import pl.yspar.cage.manager.PartyManager;
import pl.yspar.cage.manager.tops.TopDeathsManager;
import pl.yspar.cage.manager.tops.TopKillsManager;
import pl.yspar.cage.manager.tops.TopWinManager;
import pl.yspar.cage.utils.ReflectionUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String name;
    private int kills;
    private int win;
    private int deaths;
    private long cooldown;
    private SideBar sideBar;

    public User(final Player p) {
        this.name = p.getName();
        this.kills = 0;
        this.win = 0;
        this.cooldown = 0L;
        this.sideBar = new SideBar(true);
        TopDeathsManager.addDeaths(this);
        TopKillsManager.addKills(this);
        TopWinManager.addMedal(this);
        this.insert();
    }

    public User(final String p) {
        this.name = p;
        this.kills = 0;
        this.win = 0;
        this.cooldown = 0L;
        TopDeathsManager.addDeaths(this);
        TopKillsManager.addKills(this);
        TopWinManager.addMedal(this);
        this.sideBar = new SideBar(true);
        this.insert();
    }

    public User(final ResultSet rs) {
        try {
            this.name = rs.getString("NAME");
            this.kills = rs.getInt("KILLS");
            this.win = rs.getInt("WIN");
            this.deaths = rs.getInt("DEATHS");
            this.cooldown = rs.getLong("COOLDOWN");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert() {
        Database.update(false, "INSERT INTO `{P}users`(`id`,`name`, `kills`, `win`, `deaths`, `cooldown`) VALUES (NULL,'" + this.getName() + "','" + this.getKills() + "','" + this.getWins() + "','" + this.getDeaths() + "','" + this.getLastHost() + "')");
    }

    public void updateData() {
        Database.update("UPDATE `{P}users` SET `kills`='" + this.kills + "', `win`='" + this.win + "', `deaths`='" + this.deaths + "', `cooldown`='" + this.cooldown + "' WHERE `name` ='" + this.getName() + "';");
    }

    public void saveData(final User u) {
        u.updateData();
    }

    public boolean isLastHost() {
        return System.currentTimeMillis() > this.cooldown;
    }

    public SideBar getSideBar() {
        return this.sideBar;
    }

    public void setSideBar(final SideBar sideBar) {
        this.sideBar = sideBar;
    }

    public long getLastHost() {
        return this.cooldown;
    }

    public void setLastHost(final long cooldown) {
        this.cooldown = cooldown;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public void setDeaths(final int deaths) {
        this.deaths = deaths;
    }

    public int getWins() {
        return this.win;
    }

    public int getKills() {
        return this.kills;
    }

    public void setKills(final int kills) {
        this.kills = kills;
    }

    public void setwin(final int win) {
        this.win = win;
    }

    public void addwin(final int win) {
        this.win += win;
    }

    public void addKills(final int kills) {
        this.kills += kills;
    }

    public void addDeaths(final int deaths) {
        this.deaths += deaths;
    }

    public void removeDeaths(final int deaths) {
        this.deaths -= deaths;
    }

    public void removeKills(final int kills) {
        this.kills -= kills;
    }

    public void removeWin(final int win) {
        this.win -= win;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.getName());
    }

    public boolean isOnline() {
        return this.getPlayer() != null;
    }

    public Party getParty() {
        return PartyManager.getParty(this.getPlayer());
    }

    public int getPing() {
        int ping = 0;
        final Player p;
        if ((p = this.getPlayer()) == null) {
            return ping;
        }
        try {
            final Class<?> clazz;
            final Class<?> c = clazz = ReflectionUtil.getBukkitEntityClass("CraftPlayer");
            final Object cp = clazz.cast(p);
            final Object handle;
            ping = (int) (handle = clazz.getMethod("getHandle", new Class[0]).invoke(cp)).getClass().getField("ping").get(handle);
            return ping;
        } catch (Exception c2) {
            return ping;
        }
    }
}
