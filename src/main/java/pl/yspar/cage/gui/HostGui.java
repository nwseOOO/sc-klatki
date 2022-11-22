package pl.yspar.cage.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import pl.yspar.cage.builder.ItemBuilder;
import pl.yspar.cage.config.Config;
import pl.yspar.cage.utils.Util;

import java.util.stream.IntStream;

public class HostGui {
    public static void manage(final Player p) {
        final Inventory inv = Bukkit.createInventory((InventoryHolder) null, InventoryType.HOPPER, Util.fixColor(Config.getInstance().inventory.hostTitle));
        final ItemBuilder start = new ItemBuilder(Material.STAINED_CLAY, 1, (short) 13).setTitle(Util.fixColor("&6Wystartuj &7(&fKlatki&7)")).addLore(Util.fixColor("&6Kliknij aby wykona\u0107."));
        IntStream.range(0, inv.getSize()).filter(i -> inv.getItem(i) == null).forEach(i -> inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1)));
        inv.setItem(2, start.build());
        p.openInventory(inv);
    }

    public static void type(final Player p) {
        final ItemBuilder x1 = new ItemBuilder(Material.STAINED_CLAY, 1, (short) 13).setTitle(Util.fixColor("&cWybierz 1vs1"));
        final ItemBuilder x2 = new ItemBuilder(Material.STAINED_CLAY, 2, (short) 13).setTitle(Util.fixColor("&cWybierz 2vs2"));
        final ItemBuilder x3 = new ItemBuilder(Material.STAINED_CLAY, 3, (short) 13).setTitle(Util.fixColor("&cWybierz 3vs3"));
        final ItemBuilder x4 = new ItemBuilder(Material.STAINED_CLAY, 4, (short) 3).setTitle(Util.fixColor("&cWybierz 4vs4"));
        final ItemBuilder x5 = new ItemBuilder(Material.STAINED_CLAY, 5, (short) 3).setTitle(Util.fixColor("&cWybierz 5vs5"));
        final ItemBuilder x6 = new ItemBuilder(Material.STAINED_CLAY, 6, (short) 14).setTitle(Util.fixColor("&cWybierz 6vs6"));
        final ItemBuilder x7 = new ItemBuilder(Material.STAINED_CLAY, 7, (short) 14).setTitle(Util.fixColor("&cWybierz 7vs7"));
        final Inventory inv = Bukkit.createInventory((InventoryHolder) p, 27, Util.fixColor(Config.getInstance().inventory.xvsxTitle));
        IntStream.range(0, inv.getSize()).filter(i -> inv.getItem(i) == null).forEach(i -> inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1)));
        inv.setItem(10, x1.build());
        inv.setItem(11, x2.build());
        inv.setItem(12, x3.build());
        inv.setItem(13, x4.build());
        inv.setItem(14, x5.build());
        inv.setItem(15, x6.build());
        inv.setItem(16, x7.build());
        p.openInventory(inv);
    }

    public static void equip(final Player p) {
        final ItemBuilder x1 = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1).setTitle(Util.fixColor("&bDiamentowe"));
        final ItemBuilder x2 = new ItemBuilder(Material.IRON_CHESTPLATE, 1).setTitle(Util.fixColor("&cZelazne"));
        final ItemBuilder x3 = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1).setTitle(Util.fixColor("&9Skorzane"));
        final ItemBuilder custom = new ItemBuilder(Material.BEACON, 1).setTitle(Util.fixColor("&cUstaw sw\u00f3j w\u0142asny zestaw.")).addLore(Util.fixColor("")).addLore(Util.fixColor("&eKliknij tutaj aby pobra\u0107 zestaw z twojego &6ekwipunku&e")).addLore(Util.fixColor(""));
        final Inventory inv = Bukkit.createInventory((InventoryHolder) p, 27, Util.fixColor(Config.getInstance().inventory.equipTitle));
        IntStream.range(0, inv.getSize()).filter(i -> inv.getItem(i) == null).forEach(i -> inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1)));
        inv.setItem(10, x1.build());
        inv.setItem(11, x2.build());
        inv.setItem(12, x3.build());
        inv.setItem(14, custom.build());
        p.openInventory(inv);
    }

    public static void time(final Player p) {
        final ItemBuilder c2 = new ItemBuilder(Material.WATCH, 1).setTitle(Util.fixColor("&610 sekund"));
        final ItemBuilder c3 = new ItemBuilder(Material.WATCH, 3).setTitle(Util.fixColor("&630 sekund"));
        final ItemBuilder c4 = new ItemBuilder(Material.WATCH, 5).setTitle(Util.fixColor("&650 sekund"));
        final ItemBuilder c5 = new ItemBuilder(Material.WATCH, 6).setTitle(Util.fixColor("&660 sekund"));
        final ItemBuilder c6 = new ItemBuilder(Material.WATCH, 7).setTitle(Util.fixColor("&670 sekund"));
        final ItemBuilder c7 = new ItemBuilder(Material.WATCH, 8).setTitle(Util.fixColor("&680 sekund"));
        final ItemBuilder c8 = new ItemBuilder(Material.WATCH, 10).setTitle(Util.fixColor("&6100 sekund"));
        final Inventory inv = Bukkit.createInventory((InventoryHolder) p, 27, Util.fixColor(Config.getInstance().inventory.timeTitle));
        IntStream.range(0, inv.getSize()).filter(i -> inv.getItem(i) == null).forEach(i -> inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1)));
        inv.setItem(10, c2.build());
        inv.setItem(11, c3.build());
        inv.setItem(12, c4.build());
        inv.setItem(13, c5.build());
        inv.setItem(14, c6.build());
        inv.setItem(15, c7.build());
        inv.setItem(16, c8.build());
        p.openInventory(inv);
    }
}
