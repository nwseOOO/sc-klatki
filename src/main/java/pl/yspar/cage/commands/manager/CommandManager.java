package pl.yspar.cage.commands.manager;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import pl.yspar.cage.utils.Reflection;

import java.util.HashMap;

public class CommandManager {
    public static final HashMap<String, Command> commands;
    private static final Reflection.FieldAccessor<SimpleCommandMap> f;
    private static CommandMap cmdMap;

    static {
        commands = new HashMap<>();
        f = Reflection.getField(SimplePluginManager.class, "commandMap", SimpleCommandMap.class);
        CommandManager.cmdMap = CommandManager.f.get(Bukkit.getServer().getPluginManager());
    }

    public static void register(final Command cmd) {
        if (CommandManager.cmdMap == null) {
            CommandManager.cmdMap = CommandManager.f.get(Bukkit.getServer().getPluginManager());
        }
        CommandManager.cmdMap.register(cmd.getName(), cmd);
        CommandManager.commands.put(cmd.getName(), cmd);
    }
}
