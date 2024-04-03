package com.nndmove.app.web.rest;

import static com.nndmove.app.domain.HistoryAsserts.*;
import static com.nndmove.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nndmove.app.IntegrationTest;
import com.nndmove.app.domain.History;
import com.nndmove.app.repository.HistoryRepository;
import com.nndmove.app.service.dto.HistoryDTO;
import com.nndmove.app.service.mapper.HistoryMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HistoryResourceIT {

    private static final Integer DEFAULT_PART = 1;
    private static final Integer UPDATED_PART = 2;

    private static final Integer DEFAULT_STOP_TIME = 1;
    private static final Integer UPDATED_STOP_TIME = 2;

    private static final String ENTITY_API_URL = "/api/histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private HistoryMapper historyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistoryMockMvc;

    private History history;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static History createEntity(EntityManager em) {
        History history = new History().part(DEFAULT_PART).stopTime(DEFAULT_STOP_TIME);
        return history;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static History createUpdatedEntity(EntityManager em) {
        History history = new History().part(UPDATED_PART).stopTime(UPDATED_STOP_TIME);
        return history;
    }

    @BeforeEach
    public void initTest() {
        history = createEntity(em);
    }

    @Test
    @Transactional
    void createHistory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the History
        HistoryDTO historyDTO = historyMapper.toDto(history);
        var returnedHistoryDTO = om.readValue(
            restHistoryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historyDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HistoryDTO.class
        );

        // Validate the History in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedHistory = historyMapper.toEntity(returnedHistoryDTO);
        assertHistoryUpdatableFieldsEquals(returnedHistory, getPersistedHistory(returnedHistory));
    }

    @Test
    @Transactional
    void createHistoryWithExistingId() throws Exception {
        // Create the History with an existing ID
        history.setId(1L);
        HistoryDTO historyDTO = historyMapper.toDto(history);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the History in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPartIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        history.setPart(null);

        // Create the History, which fails.
        HistoryDTO historyDTO = historyMapper.toDto(history);

        restHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historyDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStopTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        history.setStopTime(null);

        // Create the History, which fails.
        HistoryDTO historyDTO = historyMapper.toDto(history);

        restHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historyDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHistories() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);

        // Get all the historyList
        restHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(history.getId().intValue())))
            .andExpect(jsonPath("$.[*].part").value(hasItem(DEFAULT_PART)))
            .andExpect(jsonPath("$.[*].stopTime").value(hasItem(DEFAULT_STOP_TIME)));
    }

    @Test
    @Transactional
    void getHistory() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);

        // Get the history
        restHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, history.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(history.getId().intValue()))
            .andExpect(jsonPath("$.part").value(DEFAULT_PART))
            .andExpect(jsonPath("$.stopTime").value(DEFAULT_STOP_TIME));
    }

    @Test
    @Transactional
    void getNonExistingHistory() throws Exception {
        // Get the history
        restHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHistory() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the history
        History updatedHistory = historyRepository.findById(history.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHistory are not directly saved in db
        em.detach(updatedHistory);
        updatedHistory.part(UPDATED_PART).stopTime(UPDATED_STOP_TIME);
        HistoryDTO historyDTO = historyMapper.toDto(updatedHistory);

        restHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historyDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historyDTO))
            )
            .andExpect(status().isOk());

        // Validate the History in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHistoryToMatchAllProperties(updatedHistory);
    }

    @Test
    @Transactional
    void putNonExistingHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        history.setId(longCount.incrementAndGet());

        // Create the History
        HistoryDTO historyDTO = historyMapper.toDto(history);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historyDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the History in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        history.setId(longCount.incrementAndGet());

        // Create the History
        HistoryDTO historyDTO = historyMapper.toDto(history);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the History in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        history.setId(longCount.incrementAndGet());

        // Create the History
        HistoryDTO historyDTO = historyMapper.toDto(history);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the History in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHistoryWithPatch() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the history using partial update
        History partialUpdatedHistory = new History();
        partialUpdatedHistory.setId(history.getId());

        partialUpdatedHistory.part(UPDATED_PART);

        restHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHistory))
            )
            .andExpect(status().isOk());

        // Validate the History in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHistoryUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedHistory, history), getPersistedHistory(history));
    }

    @Test
    @Transactional
    void fullUpdateHistoryWithPatch() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the history using partial update
        History partialUpdatedHistory = new History();
        partialUpdatedHistory.setId(history.getId());

        partialUpdatedHistory.part(UPDATED_PART).stopTime(UPDATED_STOP_TIME);

        restHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHistory))
            )
            .andExpect(status().isOk());

        // Validate the History in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHistoryUpdatableFieldsEquals(partialUpdatedHistory, getPersistedHistory(partialUpdatedHistory));
    }

    @Test
    @Transactional
    void patchNonExistingHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        history.setId(longCount.incrementAndGet());

        // Create the History
        HistoryDTO historyDTO = historyMapper.toDto(history);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, historyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(historyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the History in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        history.setId(longCount.incrementAndGet());

        // Create the History
        HistoryDTO historyDTO = historyMapper.toDto(history);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(historyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the History in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        history.setId(longCount.incrementAndGet());

        // Create the History
        HistoryDTO historyDTO = historyMapper.toDto(history);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(historyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the History in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHistory() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the history
        restHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, history.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return historyRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected History getPersistedHistory(History history) {
        return historyRepository.findById(history.getId()).orElseThrow();
    }

    protected void assertPersistedHistoryToMatchAllProperties(History expectedHistory) {
        assertHistoryAllPropertiesEquals(expectedHistory, getPersistedHistory(expectedHistory));
    }

    protected void assertPersistedHistoryToMatchUpdatableProperties(History expectedHistory) {
        assertHistoryAllUpdatablePropertiesEquals(expectedHistory, getPersistedHistory(expectedHistory));
    }
}
