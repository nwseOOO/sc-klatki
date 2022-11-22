package pl.yspar.cage.tab;

import org.bukkit.entity.Player;
import pl.yspar.cage.utils.Util;

import java.util.UUID;

public class Tab {
    private final Player player;
    private final TabProfile[][] tabProfiles;
    private final String[][] slots;

    public Tab(final Player player) {
        this.tabProfiles = new TabProfile[20][4];
        this.slots = new String[20][4];
        this.player = player;
        final int base = 97;
        for (int row = 0; row < 20; ++row) {
            for (int col = 0; col < 4; ++col) {
                final char first = (char) (base + col);
                final char second = (char) (base + row);
                final String name = "1jeste_" + first + second;
                this.tabProfiles[row][col] = new TabProfile(UUID.randomUUID(), name);
            }
        }
        for (int col2 = 0; col2 < 4; ++col2) {
            for (int row2 = 0; row2 < 20; ++row2) {
                this.slots[row2][col2] = "";
                final TabProfile tabProfile = this.tabProfiles[row2][col2];
                final String slot = this.slots[row2][col2];
                TablistPacket.send(player, tabProfile, slot, PlayerInfoAction.ADD_PLAYER);
            }
        }
    }

    public void set(final int column, final int row, final String text) {
        this.slots[row][column] = Util.fixColor(text);
        TablistPacket.send(this.player, this.tabProfiles[row][column], this.slots[row][column], PlayerInfoAction.UPDATE_DISPLAY_NAME);
    }

    public void set(final String header, final String footer) {
        TablistPacket.send(this.player, header, footer);
    }
}
