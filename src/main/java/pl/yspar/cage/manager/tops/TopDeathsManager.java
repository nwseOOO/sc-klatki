package pl.yspar.cage.manager.tops;

import pl.yspar.cage.basic.User;
import pl.yspar.cage.comparator.UserDeathsComparator;

import java.util.LinkedList;
import java.util.List;

public class TopDeathsManager {
    private static List<User> Deathss;

    static {
        TopDeathsManager.Deathss = new LinkedList<User>();
    }

    public static List<User> getDeathss() {
        return TopDeathsManager.Deathss;
    }

    public static void addDeaths(final User Deaths) {
        TopDeathsManager.Deathss.add(Deaths);
        sortUserDeathss();
    }

    public static void removeDeaths(final User Deaths) {
        TopDeathsManager.Deathss.remove(Deaths);
        sortUserDeathss();
    }

    public static void sortUserDeathss() {
        TopDeathsManager.Deathss.sort(new UserDeathsComparator());
    }

    public static int getPlaceUser(final User user) {
        for (int num = 0; num < TopDeathsManager.Deathss.size(); ++num) {
            if (TopDeathsManager.Deathss.get(num).equals(user)) {
                return num + 1;
            }
        }
        return 0;
    }
}
