package pl.yspar.cage.comparator;

import pl.yspar.cage.basic.User;

import java.util.Comparator;

public class UserDeathsComparator implements Comparator<User> {
    @Override
    public int compare(final User g0, final User g1) {
        final Integer p0 = g0.getDeaths();
        final Integer p2 = g1.getDeaths();
        return p2.compareTo(p0);
    }
}
