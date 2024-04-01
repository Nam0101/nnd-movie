package com.nndmove.app.domain;

import static com.nndmove.app.domain.MovieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.nndmove.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MovieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Movie.class);
        Movie movie1 = getMovieSample1();
        Movie movie2 = new Movie();
        assertThat(movie1).isNotEqualTo(movie2);

        movie2.setId(movie1.getId());
        assertThat(movie1).isEqualTo(movie2);

        movie2 = getMovieSample2();
        assertThat(movie1).isNotEqualTo(movie2);
    }
}
