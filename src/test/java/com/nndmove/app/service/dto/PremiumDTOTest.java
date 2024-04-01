package com.nndmove.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.nndmove.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PremiumDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PremiumDTO.class);
        PremiumDTO premiumDTO1 = new PremiumDTO();
        premiumDTO1.setId(1L);
        PremiumDTO premiumDTO2 = new PremiumDTO();
        assertThat(premiumDTO1).isNotEqualTo(premiumDTO2);
        premiumDTO2.setId(premiumDTO1.getId());
        assertThat(premiumDTO1).isEqualTo(premiumDTO2);
        premiumDTO2.setId(2L);
        assertThat(premiumDTO1).isNotEqualTo(premiumDTO2);
        premiumDTO1.setId(null);
        assertThat(premiumDTO1).isNotEqualTo(premiumDTO2);
    }
}
