package com.nndmove.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MovieResourceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MovieResource getMovieResourceSample1() {
        return new MovieResource().id(1L).part(1).linkEmbed("linkEmbed1").linkM3u8("linkM3u81");
    }

    public static MovieResource getMovieResourceSample2() {
        return new MovieResource().id(2L).part(2).linkEmbed("linkEmbed2").linkM3u8("linkM3u82");
    }

    public static MovieResource getMovieResourceRandomSampleGenerator() {
        return new MovieResource()
            .id(longCount.incrementAndGet())
            .part(intCount.incrementAndGet())
            .linkEmbed(UUID.randomUUID().toString())
            .linkM3u8(UUID.randomUUID().toString());
    }
}
