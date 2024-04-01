package com.nndmove.app.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PremiumTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Premium getPremiumSample1() {
        return new Premium().id(1L);
    }

    public static Premium getPremiumSample2() {
        return new Premium().id(2L);
    }

    public static Premium getPremiumRandomSampleGenerator() {
        return new Premium().id(longCount.incrementAndGet());
    }
}
