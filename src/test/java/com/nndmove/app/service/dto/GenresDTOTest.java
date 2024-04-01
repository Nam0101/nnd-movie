package com.nndmove.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.nndmove.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GenresDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenresDTO.class);
        GenresDTO genresDTO1 = new GenresDTO();
        genresDTO1.setId(1L);
        GenresDTO genresDTO2 = new GenresDTO();
        assertThat(genresDTO1).isNotEqualTo(genresDTO2);
        genresDTO2.setId(genresDTO1.getId());
        assertThat(genresDTO1).isEqualTo(genresDTO2);
        genresDTO2.setId(2L);
        assertThat(genresDTO1).isNotEqualTo(genresDTO2);
        genresDTO1.setId(null);
        assertThat(genresDTO1).isNotEqualTo(genresDTO2);
    }
}
