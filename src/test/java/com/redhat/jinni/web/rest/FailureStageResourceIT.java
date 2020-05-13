package com.redhat.jinni.web.rest;

import com.redhat.jinni.JinniApp;
import com.redhat.jinni.domain.FailureStage;
import com.redhat.jinni.repository.FailureStageRepository;
import com.redhat.jinni.repository.search.FailureStageSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FailureStageResource} REST controller.
 */
@SpringBootTest(classes = JinniApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FailureStageResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FailureStageRepository failureStageRepository;

    /**
     * This repository is mocked in the com.redhat.jinni.repository.search test package.
     *
     * @see com.redhat.jinni.repository.search.FailureStageSearchRepositoryMockConfiguration
     */
    @Autowired
    private FailureStageSearchRepository mockFailureStageSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFailureStageMockMvc;

    private FailureStage failureStage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FailureStage createEntity(EntityManager em) {
        FailureStage failureStage = new FailureStage()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return failureStage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FailureStage createUpdatedEntity(EntityManager em) {
        FailureStage failureStage = new FailureStage()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return failureStage;
    }

    @BeforeEach
    public void initTest() {
        failureStage = createEntity(em);
    }

    @Test
    @Transactional
    public void createFailureStage() throws Exception {
        int databaseSizeBeforeCreate = failureStageRepository.findAll().size();

        // Create the FailureStage
        restFailureStageMockMvc.perform(post("/api/failure-stages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(failureStage)))
            .andExpect(status().isCreated());

        // Validate the FailureStage in the database
        List<FailureStage> failureStageList = failureStageRepository.findAll();
        assertThat(failureStageList).hasSize(databaseSizeBeforeCreate + 1);
        FailureStage testFailureStage = failureStageList.get(failureStageList.size() - 1);
        assertThat(testFailureStage.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFailureStage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the FailureStage in Elasticsearch
        verify(mockFailureStageSearchRepository, times(1)).save(testFailureStage);
    }

    @Test
    @Transactional
    public void createFailureStageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = failureStageRepository.findAll().size();

        // Create the FailureStage with an existing ID
        failureStage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFailureStageMockMvc.perform(post("/api/failure-stages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(failureStage)))
            .andExpect(status().isBadRequest());

        // Validate the FailureStage in the database
        List<FailureStage> failureStageList = failureStageRepository.findAll();
        assertThat(failureStageList).hasSize(databaseSizeBeforeCreate);

        // Validate the FailureStage in Elasticsearch
        verify(mockFailureStageSearchRepository, times(0)).save(failureStage);
    }


    @Test
    @Transactional
    public void getAllFailureStages() throws Exception {
        // Initialize the database
        failureStageRepository.saveAndFlush(failureStage);

        // Get all the failureStageList
        restFailureStageMockMvc.perform(get("/api/failure-stages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(failureStage.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getFailureStage() throws Exception {
        // Initialize the database
        failureStageRepository.saveAndFlush(failureStage);

        // Get the failureStage
        restFailureStageMockMvc.perform(get("/api/failure-stages/{id}", failureStage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(failureStage.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingFailureStage() throws Exception {
        // Get the failureStage
        restFailureStageMockMvc.perform(get("/api/failure-stages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFailureStage() throws Exception {
        // Initialize the database
        failureStageRepository.saveAndFlush(failureStage);

        int databaseSizeBeforeUpdate = failureStageRepository.findAll().size();

        // Update the failureStage
        FailureStage updatedFailureStage = failureStageRepository.findById(failureStage.getId()).get();
        // Disconnect from session so that the updates on updatedFailureStage are not directly saved in db
        em.detach(updatedFailureStage);
        updatedFailureStage
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restFailureStageMockMvc.perform(put("/api/failure-stages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFailureStage)))
            .andExpect(status().isOk());

        // Validate the FailureStage in the database
        List<FailureStage> failureStageList = failureStageRepository.findAll();
        assertThat(failureStageList).hasSize(databaseSizeBeforeUpdate);
        FailureStage testFailureStage = failureStageList.get(failureStageList.size() - 1);
        assertThat(testFailureStage.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFailureStage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the FailureStage in Elasticsearch
        verify(mockFailureStageSearchRepository, times(1)).save(testFailureStage);
    }

    @Test
    @Transactional
    public void updateNonExistingFailureStage() throws Exception {
        int databaseSizeBeforeUpdate = failureStageRepository.findAll().size();

        // Create the FailureStage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFailureStageMockMvc.perform(put("/api/failure-stages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(failureStage)))
            .andExpect(status().isBadRequest());

        // Validate the FailureStage in the database
        List<FailureStage> failureStageList = failureStageRepository.findAll();
        assertThat(failureStageList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FailureStage in Elasticsearch
        verify(mockFailureStageSearchRepository, times(0)).save(failureStage);
    }

    @Test
    @Transactional
    public void deleteFailureStage() throws Exception {
        // Initialize the database
        failureStageRepository.saveAndFlush(failureStage);

        int databaseSizeBeforeDelete = failureStageRepository.findAll().size();

        // Delete the failureStage
        restFailureStageMockMvc.perform(delete("/api/failure-stages/{id}", failureStage.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FailureStage> failureStageList = failureStageRepository.findAll();
        assertThat(failureStageList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FailureStage in Elasticsearch
        verify(mockFailureStageSearchRepository, times(1)).deleteById(failureStage.getId());
    }

    @Test
    @Transactional
    public void searchFailureStage() throws Exception {
        // Initialize the database
        failureStageRepository.saveAndFlush(failureStage);
        when(mockFailureStageSearchRepository.search(queryStringQuery("id:" + failureStage.getId())))
            .thenReturn(Collections.singletonList(failureStage));
        // Search the failureStage
        restFailureStageMockMvc.perform(get("/api/_search/failure-stages?query=id:" + failureStage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(failureStage.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
