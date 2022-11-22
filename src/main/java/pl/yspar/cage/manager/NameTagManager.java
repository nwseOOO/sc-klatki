package pl.yspar.cage.manager;

import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pl.yspar.cage.basic.Party;
import pl.yspar.cage.basic.User;
import pl.yspar.cage.utils.Util;

public class NameTagManager {
    private static Scoreboard scoreboard;

    public static void init() {
        NameTagManager.scoreboard = new Scoreboard();
    }

    public static void createBoard(final Player p) {
        try {
            ScoreboardTeam team = null;
            if (NameTagManager.scoreboard.getPlayerTeam(p.getName()) == null) {
                team = NameTagManager.scoreboard.createTeam(p.getName());
            }
            NameTagManager.scoreboard.addPlayerToTeam(p.getName(), team.getName());
            team.setPrefix("");
            team.setDisplayName("");
            team.setSuffix("");
            final PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 0);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            Bukkit.getOnlinePlayers().stream().filter(pp -> pp != p).forEach(pp -> ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(packet));
            //final ScoreboardTeam t;
            //final PacketPlayOutScoreboardTeam packetShow;
            Bukkit.getOnlinePlayers().stream().filter(pp -> pp != p).forEach(pp -> {
                final ScoreboardTeam t = NameTagManager.scoreboard.getTeam(pp.getName());
                final PacketPlayOutScoreboardTeam packetShow = new PacketPlayOutScoreboardTeam(t, 0);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetShow);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeBoard(final Player p) {
        try {
            if (NameTagManager.scoreboard.getPlayerTeam(Bukkit.getPlayer(p.getName()).getName()) == null) {
                return;
            }
            final ScoreboardTeam team = NameTagManager.scoreboard.getPlayerTeam(p.getName());
            NameTagManager.scoreboard.removePlayerFromTeam(p.getName(), team);
            final PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 1);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            Bukkit.getOnlinePlayers().stream().filter(pp -> pp != p).forEach(pp -> ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(packet));
            //final ScoreboardTeam t;
            //final PacketPlayOutScoreboardTeam packetHide;
            Bukkit.getOnlinePlayers().stream().filter(pp -> pp != p).forEach(pp -> {
                final ScoreboardTeam t = NameTagManager.scoreboard.getTeam(pp.getName());
                final PacketPlayOutScoreboardTeam packetHide = new PacketPlayOutScoreboardTeam(t, 1);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetHide);
                return;
            });
            NameTagManager.scoreboard.removeTeam(team);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateBoard(final Player p) {
        if (p != null) {
            final ScoreboardTeam team = NameTagManager.scoreboard.getPlayerTeam(p.getName());
            if (team == null) {
                return;
            }
            team.setDisplayName("");
            for (final Player online : Bukkit.getOnlinePlayers()) {
                team.setSuffix(getGuildValidSuffix(p, online));
                final PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 2);
                ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    private static String getGuildValidSuffix(final Player get, final Player send) {
        final User user = UserManager.getUser(get);
        final Party g = PartyManager.getParty(get);
        final Party o = PartyManager.getParty(send);
        String tag = " ";
        String color = " &c";
        if (g != null && o != null && g.equals(o)) {
            color = " &a";
        }
        if (g != null) {
            tag = color + user.getParty().getTag();
        }
        return Util.fixColor(tag);
    }

    public static String getGuildPrefixIncognito(final Player player, final Player player2) {
        String color = "&c";
        final Party guild = PartyManager.getParty(player);
        final Party guild2 = PartyManager.getParty(player2);
        if (guild != null && guild2 != null && guild.getTag().equals(guild2.getTag())) {
            color = "&a";
        }
        String s2 = " &k";
        if (guild != null) {
            s2 = color + guild.getTag().replace(guild.getTag(), "???") + " " + color + "&k";
        }
        return Util.fixColor(s2);
    }
}
