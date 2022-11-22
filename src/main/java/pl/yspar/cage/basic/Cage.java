package pl.yspar.cage.basic;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pl.yspar.cage.CagePlugin;
import pl.yspar.cage.config.Config;
import pl.yspar.cage.manager.CageManager;
import pl.yspar.cage.types.CageType;
import pl.yspar.cage.utils.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cage {
    public static CageType statusCage;
    public static long saveCage;
    public static int xvsx;
    public static int cooldown;
    public static String organizer;
    public static String lastwin;
    public static boolean waiting;
    public static int round;
    public static int walkovers;
    public static boolean custom;
    public static boolean ZELAZNE;
    public static boolean DIAMENTOWE;
    public static boolean PVPHYPE;
    public static boolean SKORZANE;
    public static List<String> partySave;
    public static List<String> partyPass;
    public static int i;
    public static int task;
    private static File file;
    private static FileConfiguration c;

    static {
        Cage.i = 100;
    }

    public static void loadCage() {
        Cage.file = new File(CagePlugin.getPlugin().getDataFolder(), "system.yml");
        Cage.statusCage = CageType.ENDING;
        Cage.saveCage = 0L;
        Cage.xvsx = 1;
        Cage.cooldown = 1;
        Cage.partySave = new ArrayList<>();
        Cage.partyPass = new ArrayList<>();
        Cage.organizer = "Brak";
        Cage.lastwin = Util.fixColor("Brak");
        Cage.waiting = false;
        Cage.round = 1;
        Cage.walkovers = 0;
        Cage.custom = false;
        Cage.DIAMENTOWE = true;
        Cage.ZELAZNE = false;
        Cage.PVPHYPE = false;
        Cage.SKORZANE = false;
        Cage.c = null;
    }

    public static void save(final Player p) throws IOException {
        final YamlConfiguration c = new YamlConfiguration();
        c.set("inventory.armor", p.getInventory().getArmorContents());
        c.set("inventory.content", p.getInventory().getContents());
        c.save(new File("customkit.yml"));
    }

    public static void restore(final Player p) throws IOException {
        final YamlConfiguration c = YamlConfiguration.loadConfiguration(new File("customkit.yml"));
        ItemStack[] armor = (ItemStack[]) c.get("inventory.armor");
        p.getInventory().setArmorContents(armor);
        ItemStack[] content = (ItemStack[]) c.get("inventory.content");
        p.getInventory().setContents(content);
    }

    public static void removeInt() {
        --Cage.i;
    }

    public static void waiting() {
        Cage.statusCage = CageType.WAITING;
        Cage.task = CagePlugin.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask((Plugin) CagePlugin.getPlugin(), (Runnable) new Runnable() {
            @Override
            public void run() {
                if (Cage.statusCage != CageType.WAITING || Cage.i == -1) {
                    return;
                }
                if (Cage.i != 0) {
                    Bukkit.getOnlinePlayers().forEach(playerOnline -> Util.sendTitleMessage(playerOnline, "", Util.fixColor(Config.getInstance().titlemessages.waiting).replace("{TYPE}", Integer.toString(Cage.xvsx)).replace("{TIME}", Integer.toString(Cage.i)), 10, 10, 10));
                    Cage.removeInt();
                    return;
                }
                if (Cage.partySave.size() >= 2) {
                    Bukkit.getOnlinePlayers().forEach(playerOnline -> Util.sendTitleMessage(playerOnline, "", "&aStart!", 0, 30, 0));
                    CageManager.start();
                    Cage.statusCage = CageType.INGAME;
                    Cage.i = -1;
                    return;
                }
                Bukkit.getOnlinePlayers().forEach(playerOnline -> Util.sendTitleMessage(playerOnline, "", Util.fixColor(Config.getInstance().titlemessages.startRequirements), 0, 30, 0));
                CageManager.stop();
            }
        }, 0L, 20L);
    }
}
