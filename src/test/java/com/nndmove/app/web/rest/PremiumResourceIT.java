package com.nndmove.app.web.rest;

import static com.nndmove.app.domain.PremiumAsserts.*;
import static com.nndmove.app.web.rest.TestUtil.createUpdateProxyForBean;
import static com.nndmove.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nndmove.app.IntegrationTest;
import com.nndmove.app.domain.Premium;
import com.nndmove.app.repository.PremiumRepository;
import com.nndmove.app.service.dto.PremiumDTO;
import com.nndmove.app.service.mapper.PremiumMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link PremiumResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PremiumResourceIT {

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/premiums";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PremiumRepository premiumRepository;

    @Autowired
    private PremiumMapper premiumMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPremiumMockMvc;

    private Premium premium;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Premium createEntity(EntityManager em) {
        Premium premium = new Premium().startTime(DEFAULT_START_TIME).endTime(DEFAULT_END_TIME);
        return premium;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Premium createUpdatedEntity(EntityManager em) {
        Premium premium = new Premium().startTime(UPDATED_START_TIME).endTime(UPDATED_END_TIME);
        return premium;
    }

    @BeforeEach
    public void initTest() {
        premium = createEntity(em);
    }

    @Test
    @Transactional
    void createPremium() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Premium
        PremiumDTO premiumDTO = premiumMapper.toDto(premium);
        var returnedPremiumDTO = om.readValue(
            restPremiumMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(premiumDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PremiumDTO.class
        );

        // Validate the Premium in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPremium = premiumMapper.toEntity(returnedPremiumDTO);
        assertPremiumUpdatableFieldsEquals(returnedPremium, getPersistedPremium(returnedPremium));
    }

    @Test
    @Transactional
    void createPremiumWithExistingId() throws Exception {
        // Create the Premium with an existing ID
        premium.setId(1L);
        PremiumDTO premiumDTO = premiumMapper.toDto(premium);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPremiumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(premiumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Premium in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStartTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        premium.setStartTime(null);

        // Create the Premium, which fails.
        PremiumDTO premiumDTO = premiumMapper.toDto(premium);

        restPremiumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(premiumDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        premium.setEndTime(null);

        // Create the Premium, which fails.
        PremiumDTO premiumDTO = premiumMapper.toDto(premium);

        restPremiumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(premiumDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPremiums() throws Exception {
        // Initialize the database
        premiumRepository.saveAndFlush(premium);

        // Get all the premiumList
        restPremiumMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(premium.getId().intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))));
    }

    @Test
    @Transactional
    void getPremium() throws Exception {
        // Initialize the database
        premiumRepository.saveAndFlush(premium);

        // Get the premium
        restPremiumMockMvc
            .perform(get(ENTITY_API_URL_ID, premium.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(premium.getId().intValue()))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.endTime").value(sameInstant(DEFAULT_END_TIME)));
    }

    @Test
    @Transactional
    void getNonExistingPremium() throws Exception {
        // Get the premium
        restPremiumMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPremium() throws Exception {
        // Initialize the database
        premiumRepository.saveAndFlush(premium);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the premium
        Premium updatedPremium = premiumRepository.findById(premium.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPremium are not directly saved in db
        em.detach(updatedPremium);
        updatedPremium.startTime(UPDATED_START_TIME).endTime(UPDATED_END_TIME);
        PremiumDTO premiumDTO = premiumMapper.toDto(updatedPremium);

        restPremiumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, premiumDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(premiumDTO))
            )
            .andExpect(status().isOk());

        // Validate the Premium in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPremiumToMatchAllProperties(updatedPremium);
    }

    @Test
    @Transactional
    void putNonExistingPremium() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        premium.setId(longCount.incrementAndGet());

        // Create the Premium
        PremiumDTO premiumDTO = premiumMapper.toDto(premium);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPremiumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, premiumDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(premiumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Premium in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPremium() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        premium.setId(longCount.incrementAndGet());

        // Create the Premium
        PremiumDTO premiumDTO = premiumMapper.toDto(premium);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPremiumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(premiumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Premium in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPremium() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        premium.setId(longCount.incrementAndGet());

        // Create the Premium
        PremiumDTO premiumDTO = premiumMapper.toDto(premium);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPremiumMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(premiumDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Premium in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePremiumWithPatch() throws Exception {
        // Initialize the database
        premiumRepository.saveAndFlush(premium);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the premium using partial update
        Premium partialUpdatedPremium = new Premium();
        partialUpdatedPremium.setId(premium.getId());

        restPremiumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPremium.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPremium))
            )
            .andExpect(status().isOk());

        // Validate the Premium in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPremiumUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPremium, premium), getPersistedPremium(premium));
    }

    @Test
    @Transactional
    void fullUpdatePremiumWithPatch() throws Exception {
        // Initialize the database
        premiumRepository.saveAndFlush(premium);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the premium using partial update
        Premium partialUpdatedPremium = new Premium();
        partialUpdatedPremium.setId(premium.getId());

        partialUpdatedPremium.startTime(UPDATED_START_TIME).endTime(UPDATED_END_TIME);

        restPremiumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPremium.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPremium))
            )
            .andExpect(status().isOk());

        // Validate the Premium in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPremiumUpdatableFieldsEquals(partialUpdatedPremium, getPersistedPremium(partialUpdatedPremium));
    }

    @Test
    @Transactional
    void patchNonExistingPremium() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        premium.setId(longCount.incrementAndGet());

        // Create the Premium
        PremiumDTO premiumDTO = premiumMapper.toDto(premium);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPremiumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, premiumDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(premiumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Premium in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPremium() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        premium.setId(longCount.incrementAndGet());

        // Create the Premium
        PremiumDTO premiumDTO = premiumMapper.toDto(premium);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPremiumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(premiumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Premium in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPremium() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        premium.setId(longCount.incrementAndGet());

        // Create the Premium
        PremiumDTO premiumDTO = premiumMapper.toDto(premium);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPremiumMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(premiumDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Premium in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePremium() throws Exception {
        // Initialize the database
        premiumRepository.saveAndFlush(premium);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the premium
        restPremiumMockMvc
            .perform(delete(ENTITY_API_URL_ID, premium.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return premiumRepository.count();
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

    protected Premium getPersistedPremium(Premium premium) {
        return premiumRepository.findById(premium.getId()).orElseThrow();
    }

    protected void assertPersistedPremiumToMatchAllProperties(Premium expectedPremium) {
        assertPremiumAllPropertiesEquals(expectedPremium, getPersistedPremium(expectedPremium));
    }

    protected void assertPersistedPremiumToMatchUpdatableProperties(Premium expectedPremium) {
        assertPremiumAllUpdatablePropertiesEquals(expectedPremium, getPersistedPremium(expectedPremium));
    }
}
