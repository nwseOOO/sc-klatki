package pl.yspar.cage;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.yspar.cage.basic.Cage;
import pl.yspar.cage.commands.EventCommand;
import pl.yspar.cage.commands.HostCommand;
import pl.yspar.cage.commands.PartyCommand;
import pl.yspar.cage.commands.SetLocationCommand;
import pl.yspar.cage.commands.manager.Command;
import pl.yspar.cage.commands.manager.CommandManager;
import pl.yspar.cage.config.Config;
import pl.yspar.cage.config.LocationCage;
import pl.yspar.cage.database.Database;
import pl.yspar.cage.handlers.InventoryHandler;
import pl.yspar.cage.handlers.JoinQuitHandler;
import pl.yspar.cage.handlers.MoveHandler;
import pl.yspar.cage.handlers.PlayerDeathHandler;
import pl.yspar.cage.manager.PartyManager;
import pl.yspar.cage.manager.UserManager;
import pl.yspar.cage.runnable.ActionRunnable;
import pl.yspar.cage.runnable.CageSystemRunnable;
import pl.yspar.cage.runnable.RefreshTablistRunnable;
import pl.yspar.cage.runnable.UpdateRunnable;
import pl.yspar.cage.utils.Util;

import java.io.File;

public class CagePlugin extends JavaPlugin {
    private static CagePlugin plugin;

    public static CagePlugin getPlugin() {
        return CagePlugin.plugin;
    }

    public void onLoad() {
        CagePlugin.plugin = this;
    }

    public void onEnable() {
        this.registerConfig();
        this.registerDatabase();
        this.registerManager();
        this.registerCommand();
        this.registerHandlers();
        this.registerRunnable();
    }

    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(playerOnline -> playerOnline.kickPlayer(Util.fixColor(Config.getInstance().messages.pluginRestart)));
    }

    public void registerDatabase() {
        new Database(Config.getInstance().database.hostname, Config.getInstance().database.port, Config.getInstance().database.user, Config.getInstance().database.password, Config.getInstance().database.name, Config.getInstance().database.prefix);
        if (Database.isConnected()) {
            Database.update(true, "CREATE TABLE IF NOT EXISTS `{P}users` (`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, `name` varchar(32) NOT NULL, `kills` int(11) NOT NULL, `win` int(11) NOT NULL, `deaths` int(11) NOT NULL, `cooldown` varchar(256) NOT NULL);");
            Database.update(true, "CREATE TABLE IF NOT EXISTS `{P}party` (`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, `tag` varchar(5) NOT NULL, `name` varchar(32) NOT NULL, `owner` varchar(64) NOT NULL);");
            Database.update(true, "CREATE TABLE IF NOT EXISTS `{P}members` (`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, `name` varchar(32) NOT NULL, `tag` varchar(5) NOT NULL);");
        }
    }

    public void registerCommand(final Command command) {
        CommandManager.register(command);
    }

    public void registerRunnable() {
        new ActionRunnable().runTaskTimerAsynchronously(this, 40L, 20L);
        new CageSystemRunnable().runTaskTimerAsynchronously(this, 40L, 20L);
        new RefreshTablistRunnable().runTaskTimerAsynchronously(this, 30L, 90L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, (Runnable) new UpdateRunnable(), 40L, 80L);
    }

    public void registerHandlers() {
        this.getServer().getPluginManager().registerEvents(new JoinQuitHandler(), this);
        this.getServer().getPluginManager().registerEvents(new MoveHandler(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryHandler(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDeathHandler(), this);
    }

    public void registerCommand() {
        this.registerCommand(new HostCommand());
        this.registerCommand(new EventCommand());
        this.registerCommand(new PartyCommand());
        this.registerCommand(new SetLocationCommand());
    }

    public void registerConfig() {
        Config.init(new File("./plugins/" + getPlugin().getName()));
        Config.load("./plugins/" + getPlugin().getName() + "/config.json");
        Config.getInstance().toFile("./plugins/" + getPlugin().getName() + "/config.json");
        LocationCage.reloadLocation();
        Cage.loadCage();
    }

    public void registerManager() {
        UserManager.loadUsers();
        PartyManager.deleteAll();
    }

}
