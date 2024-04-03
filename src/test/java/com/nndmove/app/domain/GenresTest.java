package com.nndmove.app.domain;

import static com.nndmove.app.domain.GenresTestSamples.*;
import static com.nndmove.app.domain.MovieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.nndmove.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GenresTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Genres.class);
        Genres genres1 = getGenresSample1();
        Genres genres2 = new Genres();
        assertThat(genres1).isNotEqualTo(genres2);

        genres2.setId(genres1.getId());
        assertThat(genres1).isEqualTo(genres2);

        genres2 = getGenresSample2();
        assertThat(genres1).isNotEqualTo(genres2);
    }

    @Test
    void movieTest() throws Exception {
        Genres genres = getGenresRandomSampleGenerator();
        Movie movieBack = getMovieRandomSampleGenerator();

        genres.addMovie(movieBack);
        assertThat(genres.getMovies()).containsOnly(movieBack);
        assertThat(movieBack.getGenres()).containsOnly(genres);

        genres.removeMovie(movieBack);
        assertThat(genres.getMovies()).doesNotContain(movieBack);
        assertThat(movieBack.getGenres()).doesNotContain(genres);

        genres.movies(new HashSet<>(Set.of(movieBack)));
        assertThat(genres.getMovies()).containsOnly(movieBack);
        assertThat(movieBack.getGenres()).containsOnly(genres);

        genres.setMovies(new HashSet<>());
        assertThat(genres.getMovies()).doesNotContain(movieBack);
        assertThat(movieBack.getGenres()).doesNotContain(genres);
    }
}
