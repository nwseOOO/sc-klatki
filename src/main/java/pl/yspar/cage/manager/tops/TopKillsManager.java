package pl.yspar.cage.manager.tops;

import pl.yspar.cage.basic.User;
import pl.yspar.cage.comparator.UserKillsComparator;

import java.util.LinkedList;
import java.util.List;

public class TopKillsManager {
    private static List<User> Killss;

    static {
        TopKillsManager.Killss = new LinkedList<User>();
    }

    public static List<User> getKillss() {
        return TopKillsManager.Killss;
    }

    public static void addKills(final User Kills) {
        TopKillsManager.Killss.add(Kills);
        sortUserKillss();
    }

    public static void removeKills(final User Kills) {
        TopKillsManager.Killss.remove(Kills);
        sortUserKillss();
    }

    public static void sortUserKillss() {
        TopKillsManager.Killss.sort(new UserKillsComparator());
    }

    public static int getPlaceUser(final User user) {
        for (int num = 0; num < TopKillsManager.Killss.size(); ++num) {
            if (TopKillsManager.Killss.get(num).equals(user)) {
                return num + 1;
            }
        }
        return 0;
    }
}
