package pl.yspar.cage.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

public enum TimeUtil {
    TICK("TICK", 0, 50, 50),
    MILLISECOND("MILLISECOND", 1, 1, 1),
    SECOND("SECOND", 2, 1000, 1000),
    MINUTE("MINUTE", 3, 60000, 60),
    HOUR("HOUR", 4, 3600000, 60),
    DAY("DAY", 5, 86400000, 24),
    WEEK("WEEK", 6, 604800000, 7);

    public static final int MPT = 50;
    private final int time;
    private final int timeMulti;

    TimeUtil(final String s, final int n, final int time, final int timeMulti) {
        this.time = time;
        this.timeMulti = timeMulti;
    }

    public static String convertTime(final int input) {
        final int numberOfDays = input / 86400;
        final int numberOfHours = input % 86400 / 3600;
        final int numberOfMinutes = input % 86400 % 3600 / 60;
        final int numberOfSeconds = input % 86400 % 3600 % 60;
        String output = "";
        if (numberOfDays > 0) {
            output = numberOfDays + "d. ";
        }
        if (numberOfHours > 0) {
            output = output + numberOfHours + "h. ";
        }
        if (numberOfMinutes > 0) {
            output = output + numberOfMinutes + "m. ";
        }
        output = output + numberOfSeconds + "s ";
        return output;
    }

    public static long addTime(final int seconds) {
        return getTime1() + seconds * 1000L;
    }

    public static long getTime1() {
        return System.currentTimeMillis();
    }

    public static double outTime(final long sg) {
        final double f = sg / 10L / 100.0;
        return new BigDecimal(f).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    public static long fromTime(final long start) {
        return start - getTime1();
    }

    public static long getMuteTime(final String string) {
        if (string == null || string.isEmpty()) {
            return 0L;
        }
        final Stack<Character> type = new Stack<Character>();
        final StringBuilder value = new StringBuilder();
        boolean calc = false;
        long time = 0L;
        char[] charArray;
        for (int length = (charArray = string.toCharArray()).length, j = 0; j < length; ++j) {
            final char c = charArray[j];
            switch (c) {
                case 'd':
                case 'h':
                case 'm':
                case 's': {
                    if (!calc) {
                        type.push(c);
                        calc = true;
                    }
                    if (calc) {
                        try {
                            final long i = Integer.parseInt(value.toString());
                            switch (type.pop()) {
                                case 'd': {
                                    time += i * 86400000L;
                                    break;
                                }
                                case 'h': {
                                    time += i * 3600000L;
                                    break;
                                }
                                case 'm': {
                                    time += i * 60000L;
                                    break;
                                }
                                case 's': {
                                    time += i * 1000L;
                                    break;
                                }
                            }
                        } catch (NumberFormatException e) {
                            return time;
                        }
                    }
                    type.push(c);
                    calc = true;
                    break;
                }
                default: {
                    value.append(c);
                    break;
                }
            }
        }
        return time;
    }

    public int getMulti() {
        return this.timeMulti;
    }

    public int getTime() {
        return this.time;
    }

    public int getTick() {
        return this.time / 50;
    }

    public int getTime(final int multi) {
        return this.time * multi;
    }

    public int getTick(final int multi) {
        return this.getTick() * multi;
    }
}
