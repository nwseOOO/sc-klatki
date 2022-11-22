package pl.yspar.cage.runnable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.yspar.cage.basic.Cage;
import pl.yspar.cage.config.Config;
import pl.yspar.cage.types.CageType;
import pl.yspar.cage.utils.Util;

public class ActionRunnable extends BukkitRunnable {

    @Override
    public void run() {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            if (Cage.statusCage == CageType.ENDING) {
                Util.sendActionBar(p, Util.fixColor(Config.getInstance().actionmessages.cageEnding));
            }
            if (Cage.statusCage == CageType.WAITING) {
                Util.sendActionBar(p, Util.fixColor(Config.getInstance().actionmessages.cageWaiting));
            }
            if (Cage.statusCage == CageType.INGAME) {
                Util.sendActionBar(p, Util.fixColor(Config.getInstance().actionmessages.cageInGame).replace("{TYPE}", Integer.toString(Cage.xvsx)));
            }
        }
    }

}
