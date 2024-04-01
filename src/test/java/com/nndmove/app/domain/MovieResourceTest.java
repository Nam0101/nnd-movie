package com.nndmove.app.domain;

import static com.nndmove.app.domain.MovieResourceTestSamples.*;
import static com.nndmove.app.domain.MovieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.nndmove.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MovieResourceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieResource.class);
        MovieResource movieResource1 = getMovieResourceSample1();
        MovieResource movieResource2 = new MovieResource();
        assertThat(movieResource1).isNotEqualTo(movieResource2);

        movieResource2.setId(movieResource1.getId());
        assertThat(movieResource1).isEqualTo(movieResource2);

        movieResource2 = getMovieResourceSample2();
        assertThat(movieResource1).isNotEqualTo(movieResource2);
    }

    @Test
    void movieTest() throws Exception {
        MovieResource movieResource = getMovieResourceRandomSampleGenerator();
        Movie movieBack = getMovieRandomSampleGenerator();

        movieResource.setMovie(movieBack);
        assertThat(movieResource.getMovie()).isEqualTo(movieBack);

        movieResource.movie(null);
        assertThat(movieResource.getMovie()).isNull();
    }
}
