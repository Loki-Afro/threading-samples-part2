package de.traso.threading;

import java.util.Random;

public final class Util {
    private Util() {
//        util.
    }

    /**
     * @returna long between 0 and 9999
     */
    public static final long getARandomLongToSleep() {
        Random generator = new Random();
        return Math.abs(generator.nextLong() % 10000);
    }
}
