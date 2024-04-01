package com.nndmove.app.domain;

import static com.nndmove.app.domain.MovieTestSamples.*;
import static com.nndmove.app.domain.PlaylistTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.nndmove.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlaylistTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Playlist.class);
        Playlist playlist1 = getPlaylistSample1();
        Playlist playlist2 = new Playlist();
        assertThat(playlist1).isNotEqualTo(playlist2);

        playlist2.setId(playlist1.getId());
        assertThat(playlist1).isEqualTo(playlist2);

        playlist2 = getPlaylistSample2();
        assertThat(playlist1).isNotEqualTo(playlist2);
    }

    @Test
    void movieTest() throws Exception {
        Playlist playlist = getPlaylistRandomSampleGenerator();
        Movie movieBack = getMovieRandomSampleGenerator();

        playlist.setMovie(movieBack);
        assertThat(playlist.getMovie()).isEqualTo(movieBack);

        playlist.movie(null);
        assertThat(playlist.getMovie()).isNull();
    }
}
