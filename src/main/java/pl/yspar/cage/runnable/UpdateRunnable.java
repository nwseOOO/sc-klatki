package pl.yspar.cage.runnable;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.yspar.cage.manager.ScoreboardManager;
import pl.yspar.cage.manager.UserManager;
import pl.yspar.cage.utils.TagUtil;

public class UpdateRunnable extends BukkitRunnable {

    @Override
    public void run() {
        final World world = Bukkit.getWorld("world");
        world.setStorm(false);
        world.setWeatherDuration(0);
        for (final Player player : Bukkit.getOnlinePlayers()) {
            ScoreboardManager.updateSidebar(player);
            UserManager.getUser(player).updateData();
            TagUtil.refreshAll();
        }
    }

}
