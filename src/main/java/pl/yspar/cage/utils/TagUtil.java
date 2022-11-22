package pl.yspar.cage.utils;

import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pl.yspar.cage.basic.Party;
import pl.yspar.cage.basic.User;
import pl.yspar.cage.config.Config;
import pl.yspar.cage.manager.PartyManager;
import pl.yspar.cage.manager.UserManager;

public class TagUtil {
    private static Scoreboard scoreboard;

    static {
        TagUtil.scoreboard = new Scoreboard();
    }

    public static void init() {
        TagUtil.scoreboard = new Scoreboard();
    }

    public static void refreshAll() {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            updateBoard(p);
        }
    }

    public static void createBoard(final Player p) {
        ScoreboardTeam team = null;
        if (TagUtil.scoreboard.getPlayerTeam(Bukkit.getPlayer(p.getName()).getName()) == null) {
            team = TagUtil.scoreboard.createTeam(p.getName());
        }
        TagUtil.scoreboard.addPlayerToTeam(p.getName(), team.getName());
        team.setPrefix("");
        team.setDisplayName("");
        team.setSuffix("");
        final PacketPlayOutScoreboardTeam packetPlayOutScoreboardTeam = new PacketPlayOutScoreboardTeam(team, 0);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetPlayOutScoreboardTeam);
        for (final Player player2 : Bukkit.getOnlinePlayers()) {
            if (player2 != p) {
                ((CraftPlayer) player2).getHandle().playerConnection.sendPacket(packetPlayOutScoreboardTeam);
            }
        }
        for (final Player player3 : Bukkit.getOnlinePlayers()) {
            if (player3 != p) {
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(TagUtil.scoreboard.getTeam(player3.getName()), 0));
            }
        }
    }

    public static void updateBoard(final Player p) {
        if (TagUtil.scoreboard.getPlayerTeam(Bukkit.getPlayer(p.getName()).getName()) == null) {
            return;
        }
        final ScoreboardTeam team = TagUtil.scoreboard.getPlayerTeam(p.getName());
        for (final Player online : Bukkit.getOnlinePlayers()) {
            team.setPrefix(getValidPrefix(p, online));
            team.setDisplayName("");
            final PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 2);
            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public static String getValidPrefix(final Player get, final Player send) {
        final User u = UserManager.getUser(get);
        String color = Util.fixColor(Config.getInstance().nametag.enemyColor);
        final Party g = PartyManager.getParty(get);
        final Party o = PartyManager.getParty(send);
        if (g != null && o != null && g.equals(o)) {
            color = Config.getInstance().nametag.friendlyColor;
        }
        if (g != null) {
            String tag = "";
            if (g != null) {
                if (send.hasPermission("core.helper")) {
                    return tag = Util.fixColor(color + "[" + g.getTag() + "] ");
                }
                tag = Util.fixColor(color + "[" + g.getTag() + "] ");
            }
            return tag;
        }
        return color;
    }

    public static void removeBoard(final Player p) {
        try {
            ScoreboardTeam team = null;
            if (TagUtil.scoreboard.getPlayerTeam(Bukkit.getPlayer(p.getName()).getName()) == null) {
                return;
            }
            team = TagUtil.scoreboard.getPlayerTeam(p.getName());
            TagUtil.scoreboard.removePlayerFromTeam(p.getName(), team);
            final PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 1);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            for (final Player pp : Bukkit.getServer().getOnlinePlayers()) {
                if (pp != p) {
                    ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(packet);
                }
            }
            for (final Player pp : Bukkit.getServer().getOnlinePlayers()) {
                if (pp != p) {
                    final ScoreboardTeam t = TagUtil.scoreboard.getTeam(pp.getName());
                    final PacketPlayOutScoreboardTeam packetHide = new PacketPlayOutScoreboardTeam(t, 1);
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetHide);
                }
            }
            TagUtil.scoreboard.removeTeam(team);
        } catch (Exception ex) {
        }
    }
}
