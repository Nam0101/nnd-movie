package com.nndmove.app.domain;

import static com.nndmove.app.domain.GenresTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.nndmove.app.web.rest.TestUtil;
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
}
