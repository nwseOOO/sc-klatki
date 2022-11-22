package pl.yspar.cage.commands;

import org.bukkit.entity.Player;
import pl.yspar.cage.basic.Cage;
import pl.yspar.cage.commands.manager.PlayerCommand;
import pl.yspar.cage.config.Config;
import pl.yspar.cage.gui.HostGui;
import pl.yspar.cage.types.CageType;
import pl.yspar.cage.utils.Util;

public class HostCommand extends PlayerCommand {
    public HostCommand() {
        super("host", "startowanie eventu", "/host", "cage.cmd.user");
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        if (!p.hasPermission("cage.cmd.host")) {
            Util.sendMessage(p, Config.getInstance().messages.dontHavePermision);
            return false;
        }
        if (Cage.statusCage == CageType.WAITING) {
            Util.sendMessage(p, "&cAktualnie jest juz odliczanie, sprobuj ponownie pozniej");
            return false;
        }
        HostGui.manage(p);
        return false;
    }
}
