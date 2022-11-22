package pl.yspar.cage.basic;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import pl.yspar.cage.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Party {
    private String tag;
    private String name;
    private String owner;
    private int points;
    private boolean fightNow;
    private int fightAs;
    private Set<String> members;
    private Set<OfflinePlayer> invites;

    public Party(final String tag, final String name, final Player owner) {
        this.members = new HashSet<>();
        this.invites = new HashSet<>();
        this.tag = tag;
        this.name = name;
        this.owner = owner.getName();
        this.addMember(owner);
        this.points = 0;
        this.fightNow = false;
        this.fightAs = 0;
        this.insert();
    }

    public Party(final ResultSet rs) throws SQLException {
        this.tag = rs.getString("tag");
        this.name = rs.getString("name");
        this.owner = rs.getString("owner");
        final ResultSet r = Database.query("SELECT * FROM `{P}members` WHERE `tag` = '" + this.tag + "';");
        while (r.next()) {
            this.members.add(r.getString("name"));
        }
    }

    private void insert() {
        Database.update(false, "INSERT INTO `{P}party`(`id`, `tag`,`name`, `owner`) VALUES (NULL, '" + this.getTag() + "','" + this.getName() + "','" + this.getOwner() + "');");
    }

    public Set<Player> getCageMembers() {
        final Set<Player> userCage = new HashSet<Player>();
        for (final String u : this.members) {
            final OfflinePlayer op = Bukkit.getOfflinePlayer(u);
            if (op.isOnline()) {
                userCage.add(op.getPlayer());
            }
        }
        return userCage;
    }

    public boolean isFightingNow() {
        return this.fightNow;
    }

    public void setFightingNow(final boolean fightNow) {
        this.fightNow = fightNow;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(final int points) {
        this.points = points;
    }

    public int addPoints(int points) {
        return this.points += points;
    }

    public void removePoints(final int index) {
        this.points -= index;
    }

    public int getFightAs() {
        return this.fightAs;
    }

    public void setFightAs(final int fightAs) {
        this.fightAs = fightAs;
    }

    public void removeFightAs(final int index) {
        this.fightAs -= index;
    }

    public void addMember(final Player p) {
        this.members.add(p.getName());
        Database.update(false, "INSERT INTO `{P}members` (`id`,`name`,`tag`) VALUES(NULL, '" + p.getName() + "', '" + this.getTag() + "');");
    }

    public void addMember(final User owner2) {
        this.members.add(owner2.getName());
    }

    public void removeMember(final Player p) {
        this.members.remove(p.getName());
        Database.update(false, "DELETE FROM `{P}members` WHERE `name` = '" + p.getName() + "' AND `tag` = '" + this.getTag() + "';");
    }

    public void removeMember(final String p) {
        this.members.remove(p);
        Database.update(false, "DELETE FROM `{P}members` WHERE `name` = '" + p + "' AND `tag` = '" + this.getTag() + "';");
    }

    public Set<OfflinePlayer> getInvites() {
        return this.invites;
    }

    public Set<String> getMembers() {
        return this.members;
    }

    public String isSaveEvent(final Party p) {
        String save = "";
        if (Cage.partySave.contains(p.getTag()) || Cage.partyPass.contains(p.getTag())) {
            return save = "Tak";
        }
        return save = "Nie";
    }

    public boolean isMember(final String name) {
        return this.getMembers().contains(name);
    }

    public boolean isMember(final OfflinePlayer offlinePlayer) {
        return this.getMembers().contains(offlinePlayer);
    }

    public boolean isMember(final User user) {
        return this.getMembers().contains(user);
    }

    public boolean isMember(final Player p) {
        return this.getMembers().contains(p.getName());
    }

    public boolean isOwner(final Player p) {
        return this.getOwner().equalsIgnoreCase(p.getName());
    }

    public boolean isOwner(final String p) {
        return this.getOwner().equalsIgnoreCase(p);
    }

    public boolean isLeader(final String string) {
        return this.getOwner().equals(string);
    }

    public String getTag() {
        return this.tag;
    }

    public String getName() {
        return this.name;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
        Database.update(false, "UPDATE `{P}party` SET `owner` = '" + this.getOwner() + "' WHERE `tag` ='" + this.getTag() + "';");
    }
}
