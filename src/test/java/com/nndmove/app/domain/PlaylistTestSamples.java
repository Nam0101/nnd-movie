package com.nndmove.app.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PlaylistTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Playlist getPlaylistSample1() {
        return new Playlist().id(1L);
    }

    public static Playlist getPlaylistSample2() {
        return new Playlist().id(2L);
    }

    public static Playlist getPlaylistRandomSampleGenerator() {
        return new Playlist().id(longCount.incrementAndGet());
    }
}
