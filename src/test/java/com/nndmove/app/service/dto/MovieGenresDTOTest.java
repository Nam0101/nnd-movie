package com.nndmove.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.nndmove.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MovieGenresDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieGenresDTO.class);
        MovieGenresDTO movieGenresDTO1 = new MovieGenresDTO();
        movieGenresDTO1.setId(1L);
        MovieGenresDTO movieGenresDTO2 = new MovieGenresDTO();
        assertThat(movieGenresDTO1).isNotEqualTo(movieGenresDTO2);
        movieGenresDTO2.setId(movieGenresDTO1.getId());
        assertThat(movieGenresDTO1).isEqualTo(movieGenresDTO2);
        movieGenresDTO2.setId(2L);
        assertThat(movieGenresDTO1).isNotEqualTo(movieGenresDTO2);
        movieGenresDTO1.setId(null);
        assertThat(movieGenresDTO1).isNotEqualTo(movieGenresDTO2);
    }
}
