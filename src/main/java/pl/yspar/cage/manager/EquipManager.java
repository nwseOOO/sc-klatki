package pl.yspar.cage.manager;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.yspar.cage.basic.Cage;
import pl.yspar.cage.builder.ItemBuilder;
import pl.yspar.cage.utils.Util;

import java.io.IOException;

public class EquipManager {
    public static void give(final Player pl) {
        if (Cage.DIAMENTOWE) {
            pl.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
            pl.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
            pl.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
            pl.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
            Util.giveItems(pl, new ItemBuilder(Material.IRON_SWORD).build());
            Util.giveItems(pl, new ItemBuilder(Material.GOLDEN_APPLE, 1).build());
            return;
        }
        if (Cage.ZELAZNE) {
            pl.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
            pl.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
            pl.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
            pl.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS, 1));
            Util.giveItems(pl, new ItemBuilder(Material.IRON_SWORD).build());
            Util.giveItems(pl, new ItemBuilder(Material.GOLDEN_APPLE, 1).build());
            return;
        }
        if (Cage.PVPHYPE) {
            pl.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
            pl.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
            pl.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
            pl.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
            Util.giveItems(pl, new ItemBuilder(Material.IRON_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 3).build());
            Util.giveItems(pl, new ItemBuilder(Material.GOLDEN_APPLE, 1).build());
            Util.giveItems(pl, new ItemBuilder(Material.SNOW_BALL, 16).build());
            return;
        }
        if (Cage.SKORZANE) {
            pl.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
            pl.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
            pl.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
            pl.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
            Util.giveItems(pl, new ItemBuilder(Material.STONE_SWORD).build());
            return;
        }
        if (Cage.custom) {
            try {
                Cage.restore(pl);
                pl.updateInventory();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
