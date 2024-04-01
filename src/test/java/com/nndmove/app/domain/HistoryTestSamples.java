package com.nndmove.app.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class HistoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static History getHistorySample1() {
        return new History().id(1L).part(1).stopTime(1);
    }

    public static History getHistorySample2() {
        return new History().id(2L).part(2).stopTime(2);
    }

    public static History getHistoryRandomSampleGenerator() {
        return new History().id(longCount.incrementAndGet()).part(intCount.incrementAndGet()).stopTime(intCount.incrementAndGet());
    }
}
