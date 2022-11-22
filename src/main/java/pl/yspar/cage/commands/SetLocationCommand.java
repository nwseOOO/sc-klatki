package pl.yspar.cage.commands;

import org.bukkit.entity.Player;
import pl.yspar.cage.commands.manager.PlayerCommand;
import pl.yspar.cage.config.LocationCage;
import pl.yspar.cage.utils.Util;

public class SetLocationCommand extends PlayerCommand {
    public SetLocationCommand() {
        super("setloc", "ustawianie lokacji", "/setloc", "cage.cmd.admin");
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        if (args.length != 1) {
            Util.sendMessage(p, "&8>> &7/setloc lobby &8- &7ustawia lobby");
            Util.sendMessage(p, "&8>> &7/setloc spawn1 &8- &7ustawia resp dla teamu 1");
            Util.sendMessage(p, "&8>> &7/setloc spawn2 &8- &7ustawia resp dla teamu 2");
            return false;
        }
        if (args[0].equals("lobby")) {
            LocationCage.LOBBY = null;
            LocationCage.setLobby(p.getLocation());
            LocationCage.saveLocation();
            Util.sendTitleMessage(p, "&6Klatki resp", "&custawiono lobby", 30, 70, 40);
            return false;
        }
        if (args[0].equals("spawn1")) {
            LocationCage.TEAM1 = null;
            LocationCage.setLocationTeam1(p.getLocation());
            LocationCage.saveLocation();
            Util.sendTitleMessage(p, "&6Klatki resp", "&custawiono spawn team1", 30, 70, 40);
            return false;
        }
        if (args[0].equals("spawn2")) {
            LocationCage.TEAM2 = null;
            LocationCage.setLocationTeam2(p.getLocation());
            LocationCage.saveLocation();
            Util.sendTitleMessage(p, "&6Klatki resp", "&custawiono spawn team2", 30, 70, 40);
            return false;
        }
        Util.sendTitleMessage(p, "", "&cBledny argument....", 30, 70, 40);
        return false;
    }
}
