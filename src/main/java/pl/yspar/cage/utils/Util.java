package pl.yspar.cage.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pl.yspar.cage.CagePlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class Util {
    public static String nmsver;

    static {
        Util.nmsver = Bukkit.getServer().getClass().getPackage().getName();
        Util.nmsver = Util.nmsver.substring(Util.nmsver.lastIndexOf(".") + 1);
    }

    public static String fixColor(final String s) {
        if (s == null) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static Collection<String> fixColor(final Collection<String> collection) {
        final Collection<String> local = new ArrayList<>();
        for (final String s : collection) {
            local.add(fixColor(s));
        }
        return local;
    }

    public static void copy(final InputStream in, final File file) {
        try {
            final OutputStream out = new FileOutputStream(file);
            final byte[] buf = new byte[63];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] fixColor(final String[] array) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = fixColor(array[i]);
        }
        return array;
    }

    public static boolean sendMessage(final CommandSender sender, final String message, final String permission) {
        if (sender instanceof ConsoleCommandSender) {
            sendMessage(sender, message);
        }
        return permission != null && permission != "" && sender.hasPermission(permission) && sendMessage(sender, message);
    }

    public static boolean sendMessage(final CommandSender sender, final String message) {
        if (sender instanceof Player) {
            sender.sendMessage(fixColor(message));
        } else {
            sender.sendMessage(ChatColor.stripColor(fixColor(message)));
        }
        return true;
    }


    public static void sendActionBar(Player player, String message) {
        final ActionBarUtil actionBarMessageEvent = new ActionBarUtil(player, message);
        Bukkit.getPluginManager().callEvent(actionBarMessageEvent);
        if (actionBarMessageEvent.isCancelled()) {
            return;
        }
        if (player == null || message == null) return;
        String nmsVersion = Bukkit.getServer().getClass().getPackage().getName();
        nmsVersion = nmsVersion.substring(nmsVersion.lastIndexOf(".") + 1);

        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);

            Class<?> ppoc = Class.forName("net.minecraft.server." + nmsVersion + ".PacketPlayOutChat");
            Class<?> packet = Class.forName("net.minecraft.server." + nmsVersion + ".Packet");
            Object packetPlayOutChat;
            Class<?> chat = Class.forName("net.minecraft.server." + nmsVersion + (nmsVersion.equalsIgnoreCase("v1_8_R1") ? ".ChatSerializer" : ".ChatComponentText"));
            Class<?> chatBaseComponent = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");

            Method method = null;
            if (nmsVersion.equalsIgnoreCase("v1_8_R1")) method = chat.getDeclaredMethod("a", String.class);

            Object object = nmsVersion.equalsIgnoreCase("v1_8_R1") ? chatBaseComponent.cast(method.invoke(chat, "{'text': '" + message + "'}")) : chat.getConstructor(new Class[]{String.class}).newInstance(message);
            packetPlayOutChat = ppoc.getConstructor(new Class[]{chatBaseComponent, Byte.TYPE}).newInstance(object, (byte) 2);

            Method handle = craftPlayerClass.getDeclaredMethod("getHandle");
            Object iCraftPlayer = handle.invoke(craftPlayer);
            Field playerConnectionField = iCraftPlayer.getClass().getDeclaredField("playerConnection");
            Object playerConnection = playerConnectionField.get(iCraftPlayer);
            Method sendPacket = playerConnection.getClass().getDeclaredMethod("sendPacket", packet);
            sendPacket.invoke(playerConnection, packetPlayOutChat);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void giveItems(final Player p, final ItemStack... items) {
        final Inventory i = p.getInventory();
        final HashMap<Integer, ItemStack> notStored = i.addItem(items);
        for (final Map.Entry<Integer, ItemStack> e : notStored.entrySet()) {
            p.getWorld().dropItemNaturally(p.getLocation(), e.getValue());
        }
    }

    public static void sendActionBar(final Player player, final String message, int duration) {
        sendActionBar(player, message);
        if (duration >= 0) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Util.sendActionBar(player, "");
                }
            }.runTaskLater(CagePlugin.getPlugin(), duration + 1);
        }
        while (duration > 60) {
            duration -= 60;
            final int sched = duration % 60;
            new BukkitRunnable() {
                @Override
                public void run() {
                    Util.sendActionBar(player, message);
                }
            }.runTaskLater(CagePlugin.getPlugin(), sched);
        }
    }

    public static void sendTitleMessage(final Player player, String title, String subtitle, final int fadeIn, final int stay, final int fadeOut) {
        if (title == null) {
            title = "";
        }
        if (subtitle == null) {
            subtitle = "";
        }
        title = title.replace("&", "�");
        subtitle = subtitle.replace("&", "�");
        final CraftPlayer craftPlayer = (CraftPlayer) player;
        final IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
        final PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetTitle);
        final IChatBaseComponent chatSubtitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
        final PacketPlayOutTitle packetSubtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubtitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetSubtitle);
    }

}
