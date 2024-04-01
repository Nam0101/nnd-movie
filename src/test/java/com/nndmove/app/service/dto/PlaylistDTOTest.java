package com.nndmove.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.nndmove.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlaylistDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaylistDTO.class);
        PlaylistDTO playlistDTO1 = new PlaylistDTO();
        playlistDTO1.setId(1L);
        PlaylistDTO playlistDTO2 = new PlaylistDTO();
        assertThat(playlistDTO1).isNotEqualTo(playlistDTO2);
        playlistDTO2.setId(playlistDTO1.getId());
        assertThat(playlistDTO1).isEqualTo(playlistDTO2);
        playlistDTO2.setId(2L);
        assertThat(playlistDTO1).isNotEqualTo(playlistDTO2);
        playlistDTO1.setId(null);
        assertThat(playlistDTO1).isNotEqualTo(playlistDTO2);
    }
}
