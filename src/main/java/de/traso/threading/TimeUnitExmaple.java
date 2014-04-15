package de.traso.threading;

import java.util.concurrent.TimeUnit;

public class TimeUnitExmaple {

    public static void main(final String[] args) throws InterruptedException {
        // eats always and just ms
        Thread.sleep(Util.getARandomLongToSleep());
        // ability to sleep longer without stupid calculations - much more verbose
        TimeUnit.MILLISECONDS.sleep(Util.getARandomLongToSleep());
    }
}
