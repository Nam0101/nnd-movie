package com.nndmove.app.service.mapper;

import static com.nndmove.app.domain.PremiumAsserts.*;
import static com.nndmove.app.domain.PremiumTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PremiumMapperTest {

    private PremiumMapper premiumMapper;

    @BeforeEach
    void setUp() {
        premiumMapper = new PremiumMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPremiumSample1();
        var actual = premiumMapper.toEntity(premiumMapper.toDto(expected));
        assertPremiumAllPropertiesEquals(expected, actual);
    }
}
