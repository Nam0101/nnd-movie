package com.nndmove.app.domain;

import static com.nndmove.app.domain.HistoryTestSamples.*;
import static com.nndmove.app.domain.MovieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.nndmove.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(History.class);
        History history1 = getHistorySample1();
        History history2 = new History();
        assertThat(history1).isNotEqualTo(history2);

        history2.setId(history1.getId());
        assertThat(history1).isEqualTo(history2);

        history2 = getHistorySample2();
        assertThat(history1).isNotEqualTo(history2);
    }

    @Test
    void movieTest() throws Exception {
        History history = getHistoryRandomSampleGenerator();
        Movie movieBack = getMovieRandomSampleGenerator();

        history.setMovie(movieBack);
        assertThat(history.getMovie()).isEqualTo(movieBack);

        history.movie(null);
        assertThat(history.getMovie()).isNull();
    }
}
