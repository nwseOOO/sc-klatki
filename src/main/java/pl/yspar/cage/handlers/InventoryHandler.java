package pl.yspar.cage.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.yspar.cage.basic.Cage;
import pl.yspar.cage.config.Config;
import pl.yspar.cage.gui.HostGui;
import pl.yspar.cage.utils.Util;

public class InventoryHandler implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClickInventory(final InventoryClickEvent e) {
        final Player p = (Player) e.getWhoClicked();
        if (Util.fixColor(Config.getInstance().inventory.hostTitle).equalsIgnoreCase(e.getInventory().getName())) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&6Wystartuj &7(&fKlatki&7)"))) {
                HostGui.type(p);
                return;
            }
        }
        if (Util.fixColor(Config.getInstance().inventory.xvsxTitle).equalsIgnoreCase(e.getInventory().getName())) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&cWybierz 1vs1"))) {
                Cage.xvsx = 1;
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeTypeMessages).replace("{TYPE}", Integer.toString(Cage.xvsx)));
                HostGui.equip(p);
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&cWybierz 2vs2"))) {
                Cage.xvsx = 2;
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeTypeMessages).replace("{TYPE}", Integer.toString(Cage.xvsx)));
                HostGui.equip(p);
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&cWybierz 3vs3"))) {
                Cage.xvsx = 3;
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeTypeMessages).replace("{TYPE}", Integer.toString(Cage.xvsx)));
                HostGui.equip(p);
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&cWybierz 4vs4"))) {
                Cage.xvsx = 4;
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeTypeMessages).replace("{TYPE}", Integer.toString(Cage.xvsx)));
                HostGui.equip(p);
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&cWybierz 5vs5"))) {
                Cage.xvsx = 5;
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeTypeMessages).replace("{TYPE}", Integer.toString(Cage.xvsx)));
                HostGui.equip(p);
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&cWybierz 6vs6"))) {
                Cage.xvsx = 6;
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeTypeMessages).replace("{TYPE}", Integer.toString(Cage.xvsx)));
                HostGui.equip(p);
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&cWybierz 7vs7"))) {
                Cage.xvsx = 7;
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeTypeMessages).replace("{TYPE}", Integer.toString(Cage.xvsx)));
                HostGui.equip(p);
                return;
            }
        }
        if (Util.fixColor(Config.getInstance().inventory.equipTitle).equalsIgnoreCase(e.getInventory().getName())) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&bDiamentowe"))) {
                Cage.DIAMENTOWE = true;
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeEquipMessages).replace("{TYPE}", e.getCurrentItem().getItemMeta().getDisplayName()));
                HostGui.time(p);
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&cZelazne"))) {
                Cage.ZELAZNE = true;
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeEquipMessages).replace("{TYPE}", e.getCurrentItem().getItemMeta().getDisplayName()));
                HostGui.time(p);
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&9Skorzane"))) {
                Cage.SKORZANE = true;
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeEquipMessages).replace("{TYPE}", e.getCurrentItem().getItemMeta().getDisplayName()));
                HostGui.time(p);
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&cUstaw sw\u00f3j w\u0142asny zestaw."))) {
                Cage.DIAMENTOWE = true;
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeEquipMessages).replace("{TYPE}", e.getCurrentItem().getItemMeta().getDisplayName()));
                HostGui.time(p);
                return;
            }
        }
        if (Util.fixColor(Config.getInstance().inventory.timeTitle).equalsIgnoreCase(e.getInventory().getName())) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&610 sekund"))) {
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeTimeMessages).replace("{TIME}", e.getCurrentItem().getItemMeta().getDisplayName()));
                p.closeInventory();
                Cage.i = 10;
                Cage.waiting();
                Cage.organizer = p.getName();
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&620 sekund"))) {
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeTimeMessages).replace("{TIME}", e.getCurrentItem().getItemMeta().getDisplayName()));
                p.closeInventory();
                Cage.i = 20;
                Cage.waiting();
                Cage.organizer = p.getName();
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&630 sekund"))) {
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeTimeMessages).replace("{TIME}", e.getCurrentItem().getItemMeta().getDisplayName()));
                p.closeInventory();
                Cage.i = 30;
                Cage.waiting();
                Cage.organizer = p.getName();
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&640 sekund"))) {
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeTimeMessages).replace("{TIME}", e.getCurrentItem().getItemMeta().getDisplayName()));
                p.closeInventory();
                Cage.i = 40;
                Cage.waiting();
                Cage.organizer = p.getName();
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&650 sekund"))) {
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeTimeMessages).replace("{TIME}", e.getCurrentItem().getItemMeta().getDisplayName()));
                p.closeInventory();
                Cage.i = 50;
                Cage.waiting();
                Cage.organizer = p.getName();
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&660 sekund"))) {
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeTimeMessages).replace("{TIME}", e.getCurrentItem().getItemMeta().getDisplayName()));
                p.closeInventory();
                Cage.i = 60;
                Cage.waiting();
                Cage.organizer = p.getName();
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&670 sekund"))) {
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeTimeMessages).replace("{TIME}", e.getCurrentItem().getItemMeta().getDisplayName()));
                p.closeInventory();
                Cage.i = 70;
                Cage.waiting();
                Cage.organizer = p.getName();
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&680 sekund"))) {
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeTimeMessages).replace("{TIME}", e.getCurrentItem().getItemMeta().getDisplayName()));
                p.closeInventory();
                Cage.i = 80;
                Cage.waiting();
                Cage.organizer = p.getName();
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&690 sekund"))) {
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeTimeMessages).replace("{TIME}", e.getCurrentItem().getItemMeta().getDisplayName()));
                p.closeInventory();
                Cage.i = 90;
                Cage.waiting();
                Cage.organizer = p.getName();
                return;
            }
            if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(Util.fixColor("&6100 sekund"))) {
                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.changeTimeMessages).replace("{TIME}", e.getCurrentItem().getItemMeta().getDisplayName()));
                p.closeInventory();
                Cage.i = 100;
                Cage.waiting();
                Cage.organizer = p.getName();
            }
        }
    }
}
