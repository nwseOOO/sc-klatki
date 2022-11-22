package pl.yspar.cage.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.yspar.cage.CagePlugin;

import java.io.File;
import java.lang.reflect.Field;

public class LocationCage {
    public static Location TEAM1;
    public static Location TEAM2;
    public static Location LOBBY;
    private static File file;
    private static FileConfiguration c;

    static {
        LocationCage.file = new File(CagePlugin.getPlugin().getDataFolder(), "Location.yml");
        LocationCage.c = null;
        final World world = Bukkit.getWorld("world");
        LocationCage.LOBBY = new Location(world, 0.0, 100.0, 0.0);
        LocationCage.TEAM1 = new Location(world, 0.0, 100.0, 0.0);
        LocationCage.TEAM2 = new Location(world, 0.0, 100.0, 0.0);
    }

    public static void loadLocation() {
        try {
            if (!LocationCage.file.exists()) {
                LocationCage.file.getParentFile().mkdirs();
                CagePlugin.getPlugin().getResource(LocationCage.file.getName());
            }
            LocationCage.c = (FileConfiguration) YamlConfiguration.loadConfiguration(LocationCage.file);
            Field[] fields;
            for (int length = (fields = LocationCage.class.getFields()).length, i = 0; i < length; ++i) {
                final Field f = fields[i];
                if (LocationCage.c.isSet("Location." + f.getName().toLowerCase().replaceFirst("_", ",").replace(",", "."))) {
                    f.set(null, LocationCage.c.get("Location." + f.getName().toLowerCase().replaceFirst("_", ",").replace(",", ".")));
                }
            }
        } catch (Exception ex) {
        }
    }

    public static void saveLocation() {
        try {
            Field[] fields;
            for (int length = (fields = LocationCage.class.getFields()).length, i = 0; i < length; ++i) {
                final Field f = fields[i];
                LocationCage.c.set("Location." + f.getName().toLowerCase().replaceFirst("_", ",").replace(",", "."), f.get(null));
            }
            LocationCage.c.save(LocationCage.file);
        } catch (Exception ex) {
        }
    }

    public static void reloadLocation() {
        loadLocation();
        saveLocation();
    }

    public static void setLocationTeam1(final Location loc) {
        LocationCage.TEAM1 = null;
        saveLocation();
        LocationCage.TEAM1 = loc;
        CagePlugin.getPlugin().reloadConfig();
        loadLocation();
        saveLocation();
    }

    public static void setLocationTeam2(final Location loc) {
        LocationCage.TEAM2 = null;
        saveLocation();
        LocationCage.TEAM2 = loc;
        CagePlugin.getPlugin().reloadConfig();
        loadLocation();
        saveLocation();
    }

    public static void setLobby(final Location loc) {
        LocationCage.LOBBY = null;
        saveLocation();
        LocationCage.LOBBY = loc;
        CagePlugin.getPlugin().reloadConfig();
        loadLocation();
        saveLocation();
    }
}
