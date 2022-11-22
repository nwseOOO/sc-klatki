package pl.yspar.cage.runnable;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.yspar.cage.manager.UserManager;
import pl.yspar.cage.manager.tops.TopDeathsManager;
import pl.yspar.cage.manager.tops.TopKillsManager;
import pl.yspar.cage.manager.tops.TopWinManager;
import pl.yspar.cage.tab.TabList;

public class RefreshTablistRunnable extends BukkitRunnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(players -> TabList.update(players.getUniqueId()));
        TopDeathsManager.sortUserDeathss();
        TopKillsManager.sortUserKillss();
        TopWinManager.sortUserMedals();
        Bukkit.getOnlinePlayers().forEach(players -> UserManager.getUser(players).updateData());
    }

}
