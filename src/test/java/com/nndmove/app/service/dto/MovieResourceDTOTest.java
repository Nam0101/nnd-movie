package com.nndmove.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.nndmove.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MovieResourceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieResourceDTO.class);
        MovieResourceDTO movieResourceDTO1 = new MovieResourceDTO();
        movieResourceDTO1.setId(1L);
        MovieResourceDTO movieResourceDTO2 = new MovieResourceDTO();
        assertThat(movieResourceDTO1).isNotEqualTo(movieResourceDTO2);
        movieResourceDTO2.setId(movieResourceDTO1.getId());
        assertThat(movieResourceDTO1).isEqualTo(movieResourceDTO2);
        movieResourceDTO2.setId(2L);
        assertThat(movieResourceDTO1).isNotEqualTo(movieResourceDTO2);
        movieResourceDTO1.setId(null);
        assertThat(movieResourceDTO1).isNotEqualTo(movieResourceDTO2);
    }
}
