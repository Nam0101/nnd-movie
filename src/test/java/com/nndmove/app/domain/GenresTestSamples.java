package com.nndmove.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GenresTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Genres getGenresSample1() {
        return new Genres().id(1L).genres("genres1");
    }

    public static Genres getGenresSample2() {
        return new Genres().id(2L).genres("genres2");
    }

    public static Genres getGenresRandomSampleGenerator() {
        return new Genres().id(longCount.incrementAndGet()).genres(UUID.randomUUID().toString());
    }
}
