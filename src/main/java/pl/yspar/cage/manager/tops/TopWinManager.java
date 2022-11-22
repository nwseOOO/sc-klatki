package pl.yspar.cage.manager.tops;

import pl.yspar.cage.basic.User;
import pl.yspar.cage.comparator.UserWinComparator;

import java.util.LinkedList;
import java.util.List;

public class TopWinManager {
    private static List<User> Medals;

    static {
        TopWinManager.Medals = new LinkedList<User>();
    }

    public static List<User> getMedals() {
        return TopWinManager.Medals;
    }

    public static void addMedal(final User Medal) {
        TopWinManager.Medals.add(Medal);
        sortUserMedals();
    }

    public static void removeMedal(final User Medal) {
        TopWinManager.Medals.remove(Medal);
        sortUserMedals();
    }

    public static void sortUserMedals() {
        TopWinManager.Medals.sort(new UserWinComparator());
    }

    public static int getPlaceUser(final User user) {
        for (int num = 0; num < TopWinManager.Medals.size(); ++num) {
            if (TopWinManager.Medals.get(num).equals(user)) {
                return num + 1;
            }
        }
        return 0;
    }
}
