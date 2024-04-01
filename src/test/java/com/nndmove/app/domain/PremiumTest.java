package com.nndmove.app.domain;

import static com.nndmove.app.domain.PremiumTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.nndmove.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PremiumTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Premium.class);
        Premium premium1 = getPremiumSample1();
        Premium premium2 = new Premium();
        assertThat(premium1).isNotEqualTo(premium2);

        premium2.setId(premium1.getId());
        assertThat(premium1).isEqualTo(premium2);

        premium2 = getPremiumSample2();
        assertThat(premium1).isNotEqualTo(premium2);
    }
}
