package com.nndmove.app.domain;

import static com.nndmove.app.domain.GenresTestSamples.*;
import static com.nndmove.app.domain.MovieGenresTestSamples.*;
import static com.nndmove.app.domain.MovieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.nndmove.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MovieGenresTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieGenres.class);
        MovieGenres movieGenres1 = getMovieGenresSample1();
        MovieGenres movieGenres2 = new MovieGenres();
        assertThat(movieGenres1).isNotEqualTo(movieGenres2);

        movieGenres2.setId(movieGenres1.getId());
        assertThat(movieGenres1).isEqualTo(movieGenres2);

        movieGenres2 = getMovieGenresSample2();
        assertThat(movieGenres1).isNotEqualTo(movieGenres2);
    }

    @Test
    void movieTest() throws Exception {
        MovieGenres movieGenres = getMovieGenresRandomSampleGenerator();
        Movie movieBack = getMovieRandomSampleGenerator();

        movieGenres.setMovie(movieBack);
        assertThat(movieGenres.getMovie()).isEqualTo(movieBack);

        movieGenres.movie(null);
        assertThat(movieGenres.getMovie()).isNull();
    }

    @Test
    void genresTest() throws Exception {
        MovieGenres movieGenres = getMovieGenresRandomSampleGenerator();
        Genres genresBack = getGenresRandomSampleGenerator();

        movieGenres.setGenres(genresBack);
        assertThat(movieGenres.getGenres()).isEqualTo(genresBack);

        movieGenres.genres(null);
        assertThat(movieGenres.getGenres()).isNull();
    }
}
