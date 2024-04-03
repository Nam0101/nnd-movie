package com.nndmove.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class MovieAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMovieAllPropertiesEquals(Movie expected, Movie actual) {
        assertMovieAutoGeneratedPropertiesEquals(expected, actual);
        assertMovieAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMovieAllUpdatablePropertiesEquals(Movie expected, Movie actual) {
        assertMovieUpdatableFieldsEquals(expected, actual);
        assertMovieUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMovieAutoGeneratedPropertiesEquals(Movie expected, Movie actual) {
        assertThat(expected)
            .as("Verify Movie auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMovieUpdatableFieldsEquals(Movie expected, Movie actual) {
        assertThat(expected)
            .as("Verify Movie relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getOriginName()).as("check originName").isEqualTo(actual.getOriginName()))
            .satisfies(e -> assertThat(e.getIsCompleted()).as("check isCompleted").isEqualTo(actual.getIsCompleted()))
            .satisfies(e -> assertThat(e.getSlug()).as("check slug").isEqualTo(actual.getSlug()))
            .satisfies(e -> assertThat(e.getEpisodeCurrent()).as("check episodeCurrent").isEqualTo(actual.getEpisodeCurrent()))
            .satisfies(e -> assertThat(e.getEpisodeTotal()).as("check episodeTotal").isEqualTo(actual.getEpisodeTotal()))
            .satisfies(e -> assertThat(e.getQuality()).as("check quality").isEqualTo(actual.getQuality()))
            .satisfies(e -> assertThat(e.getYear()).as("check year").isEqualTo(actual.getYear()))
            .satisfies(e -> assertThat(e.getTrailerUrl()).as("check trailerUrl").isEqualTo(actual.getTrailerUrl()))
            .satisfies(e -> assertThat(e.getTime()).as("check time").isEqualTo(actual.getTime()))
            .satisfies(e -> assertThat(e.getContent()).as("check content").isEqualTo(actual.getContent()))
            .satisfies(e -> assertThat(e.getIsSingle()).as("check isSingle").isEqualTo(actual.getIsSingle()))
            .satisfies(e -> assertThat(e.getThumbUrl()).as("check thumbUrl").isEqualTo(actual.getThumbUrl()))
            .satisfies(e -> assertThat(e.getPosterUrl()).as("check posterUrl").isEqualTo(actual.getPosterUrl()))
            .satisfies(e -> assertThat(e.getActors()).as("check actors").isEqualTo(actual.getActors()))
            .satisfies(e -> assertThat(e.getCountry()).as("check country").isEqualTo(actual.getCountry()))
            .satisfies(e -> assertThat(e.getPremiumOnly()).as("check premiumOnly").isEqualTo(actual.getPremiumOnly()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMovieUpdatableRelationshipsEquals(Movie expected, Movie actual) {
        assertThat(expected)
            .as("Verify Movie relationships")
            .satisfies(e -> assertThat(e.getGenres()).as("check genres").isEqualTo(actual.getGenres()));
    }
}
