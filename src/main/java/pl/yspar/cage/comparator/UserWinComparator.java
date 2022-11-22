package pl.yspar.cage.comparator;

import pl.yspar.cage.basic.User;

import java.util.Comparator;

public class UserWinComparator implements Comparator<User> {
    @Override
    public int compare(final User g0, final User g1) {
        final Integer p0 = g0.getWins();
        final Integer p2 = g1.getWins();
        return p2.compareTo(p0);
    }
}
