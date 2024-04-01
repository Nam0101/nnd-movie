package com.nndmove.app.service.mapper;

import static com.nndmove.app.domain.HistoryAsserts.*;
import static com.nndmove.app.domain.HistoryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HistoryMapperTest {

    private HistoryMapper historyMapper;

    @BeforeEach
    void setUp() {
        historyMapper = new HistoryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getHistorySample1();
        var actual = historyMapper.toEntity(historyMapper.toDto(expected));
        assertHistoryAllPropertiesEquals(expected, actual);
    }
}
