package com.nndmove.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.nndmove.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoryDTO.class);
        HistoryDTO historyDTO1 = new HistoryDTO();
        historyDTO1.setId(1L);
        HistoryDTO historyDTO2 = new HistoryDTO();
        assertThat(historyDTO1).isNotEqualTo(historyDTO2);
        historyDTO2.setId(historyDTO1.getId());
        assertThat(historyDTO1).isEqualTo(historyDTO2);
        historyDTO2.setId(2L);
        assertThat(historyDTO1).isNotEqualTo(historyDTO2);
        historyDTO1.setId(null);
        assertThat(historyDTO1).isNotEqualTo(historyDTO2);
    }
}
