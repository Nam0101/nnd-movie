package com.nndmove.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MovieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Movie getMovieSample1() {
        return new Movie()
            .id(1L)
            .name("name1")
            .originName("originName1")
            .slug("slug1")
            .episodeCurrent("episodeCurrent1")
            .episodeTotal(1)
            .quality("quality1")
            .year(1)
            .trailerUrl("trailerUrl1")
            .time("time1")
            .content("content1")
            .thumbUrl("thumbUrl1")
            .posterUrl("posterUrl1")
            .actor("actor1")
            .country("country1");
    }

    public static Movie getMovieSample2() {
        return new Movie()
            .id(2L)
            .name("name2")
            .originName("originName2")
            .slug("slug2")
            .episodeCurrent("episodeCurrent2")
            .episodeTotal(2)
            .quality("quality2")
            .year(2)
            .trailerUrl("trailerUrl2")
            .time("time2")
            .content("content2")
            .thumbUrl("thumbUrl2")
            .posterUrl("posterUrl2")
            .actor("actor2")
            .country("country2");
    }

    public static Movie getMovieRandomSampleGenerator() {
        return new Movie()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .originName(UUID.randomUUID().toString())
            .slug(UUID.randomUUID().toString())
            .episodeCurrent(UUID.randomUUID().toString())
            .episodeTotal(intCount.incrementAndGet())
            .quality(UUID.randomUUID().toString())
            .year(intCount.incrementAndGet())
            .trailerUrl(UUID.randomUUID().toString())
            .time(UUID.randomUUID().toString())
            .content(UUID.randomUUID().toString())
            .thumbUrl(UUID.randomUUID().toString())
            .posterUrl(UUID.randomUUID().toString())
            .actor(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString());
    }
}
