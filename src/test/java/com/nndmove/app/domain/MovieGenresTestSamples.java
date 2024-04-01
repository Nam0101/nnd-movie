package com.nndmove.app.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class MovieGenresTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MovieGenres getMovieGenresSample1() {
        return new MovieGenres().id(1L);
    }

    public static MovieGenres getMovieGenresSample2() {
        return new MovieGenres().id(2L);
    }

    public static MovieGenres getMovieGenresRandomSampleGenerator() {
        return new MovieGenres().id(longCount.incrementAndGet());
    }
}
