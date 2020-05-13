package com.redhat.jinni.web.rest;

import com.redhat.jinni.JinniApp;
import com.redhat.jinni.domain.MajorIncidentSource;
import com.redhat.jinni.repository.MajorIncidentSourceRepository;
import com.redhat.jinni.repository.search.MajorIncidentSourceSearchRepository;

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
 * Integration tests for the {@link MajorIncidentSourceResource} REST controller.
 */
@SpringBootTest(classes = JinniApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MajorIncidentSourceResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private MajorIncidentSourceRepository majorIncidentSourceRepository;

    /**
     * This repository is mocked in the com.redhat.jinni.repository.search test package.
     *
     * @see com.redhat.jinni.repository.search.MajorIncidentSourceSearchRepositoryMockConfiguration
     */
    @Autowired
    private MajorIncidentSourceSearchRepository mockMajorIncidentSourceSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMajorIncidentSourceMockMvc;

    private MajorIncidentSource majorIncidentSource;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MajorIncidentSource createEntity(EntityManager em) {
        MajorIncidentSource majorIncidentSource = new MajorIncidentSource()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return majorIncidentSource;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MajorIncidentSource createUpdatedEntity(EntityManager em) {
        MajorIncidentSource majorIncidentSource = new MajorIncidentSource()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return majorIncidentSource;
    }

    @BeforeEach
    public void initTest() {
        majorIncidentSource = createEntity(em);
    }

    @Test
    @Transactional
    public void createMajorIncidentSource() throws Exception {
        int databaseSizeBeforeCreate = majorIncidentSourceRepository.findAll().size();

        // Create the MajorIncidentSource
        restMajorIncidentSourceMockMvc.perform(post("/api/major-incident-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(majorIncidentSource)))
            .andExpect(status().isCreated());

        // Validate the MajorIncidentSource in the database
        List<MajorIncidentSource> majorIncidentSourceList = majorIncidentSourceRepository.findAll();
        assertThat(majorIncidentSourceList).hasSize(databaseSizeBeforeCreate + 1);
        MajorIncidentSource testMajorIncidentSource = majorIncidentSourceList.get(majorIncidentSourceList.size() - 1);
        assertThat(testMajorIncidentSource.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMajorIncidentSource.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the MajorIncidentSource in Elasticsearch
        verify(mockMajorIncidentSourceSearchRepository, times(1)).save(testMajorIncidentSource);
    }

    @Test
    @Transactional
    public void createMajorIncidentSourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = majorIncidentSourceRepository.findAll().size();

        // Create the MajorIncidentSource with an existing ID
        majorIncidentSource.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMajorIncidentSourceMockMvc.perform(post("/api/major-incident-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(majorIncidentSource)))
            .andExpect(status().isBadRequest());

        // Validate the MajorIncidentSource in the database
        List<MajorIncidentSource> majorIncidentSourceList = majorIncidentSourceRepository.findAll();
        assertThat(majorIncidentSourceList).hasSize(databaseSizeBeforeCreate);

        // Validate the MajorIncidentSource in Elasticsearch
        verify(mockMajorIncidentSourceSearchRepository, times(0)).save(majorIncidentSource);
    }


    @Test
    @Transactional
    public void getAllMajorIncidentSources() throws Exception {
        // Initialize the database
        majorIncidentSourceRepository.saveAndFlush(majorIncidentSource);

        // Get all the majorIncidentSourceList
        restMajorIncidentSourceMockMvc.perform(get("/api/major-incident-sources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(majorIncidentSource.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getMajorIncidentSource() throws Exception {
        // Initialize the database
        majorIncidentSourceRepository.saveAndFlush(majorIncidentSource);

        // Get the majorIncidentSource
        restMajorIncidentSourceMockMvc.perform(get("/api/major-incident-sources/{id}", majorIncidentSource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(majorIncidentSource.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingMajorIncidentSource() throws Exception {
        // Get the majorIncidentSource
        restMajorIncidentSourceMockMvc.perform(get("/api/major-incident-sources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMajorIncidentSource() throws Exception {
        // Initialize the database
        majorIncidentSourceRepository.saveAndFlush(majorIncidentSource);

        int databaseSizeBeforeUpdate = majorIncidentSourceRepository.findAll().size();

        // Update the majorIncidentSource
        MajorIncidentSource updatedMajorIncidentSource = majorIncidentSourceRepository.findById(majorIncidentSource.getId()).get();
        // Disconnect from session so that the updates on updatedMajorIncidentSource are not directly saved in db
        em.detach(updatedMajorIncidentSource);
        updatedMajorIncidentSource
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restMajorIncidentSourceMockMvc.perform(put("/api/major-incident-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMajorIncidentSource)))
            .andExpect(status().isOk());

        // Validate the MajorIncidentSource in the database
        List<MajorIncidentSource> majorIncidentSourceList = majorIncidentSourceRepository.findAll();
        assertThat(majorIncidentSourceList).hasSize(databaseSizeBeforeUpdate);
        MajorIncidentSource testMajorIncidentSource = majorIncidentSourceList.get(majorIncidentSourceList.size() - 1);
        assertThat(testMajorIncidentSource.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMajorIncidentSource.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the MajorIncidentSource in Elasticsearch
        verify(mockMajorIncidentSourceSearchRepository, times(1)).save(testMajorIncidentSource);
    }

    @Test
    @Transactional
    public void updateNonExistingMajorIncidentSource() throws Exception {
        int databaseSizeBeforeUpdate = majorIncidentSourceRepository.findAll().size();

        // Create the MajorIncidentSource

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMajorIncidentSourceMockMvc.perform(put("/api/major-incident-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(majorIncidentSource)))
            .andExpect(status().isBadRequest());

        // Validate the MajorIncidentSource in the database
        List<MajorIncidentSource> majorIncidentSourceList = majorIncidentSourceRepository.findAll();
        assertThat(majorIncidentSourceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MajorIncidentSource in Elasticsearch
        verify(mockMajorIncidentSourceSearchRepository, times(0)).save(majorIncidentSource);
    }

    @Test
    @Transactional
    public void deleteMajorIncidentSource() throws Exception {
        // Initialize the database
        majorIncidentSourceRepository.saveAndFlush(majorIncidentSource);

        int databaseSizeBeforeDelete = majorIncidentSourceRepository.findAll().size();

        // Delete the majorIncidentSource
        restMajorIncidentSourceMockMvc.perform(delete("/api/major-incident-sources/{id}", majorIncidentSource.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MajorIncidentSource> majorIncidentSourceList = majorIncidentSourceRepository.findAll();
        assertThat(majorIncidentSourceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MajorIncidentSource in Elasticsearch
        verify(mockMajorIncidentSourceSearchRepository, times(1)).deleteById(majorIncidentSource.getId());
    }

    @Test
    @Transactional
    public void searchMajorIncidentSource() throws Exception {
        // Initialize the database
        majorIncidentSourceRepository.saveAndFlush(majorIncidentSource);
        when(mockMajorIncidentSourceSearchRepository.search(queryStringQuery("id:" + majorIncidentSource.getId())))
            .thenReturn(Collections.singletonList(majorIncidentSource));
        // Search the majorIncidentSource
        restMajorIncidentSourceMockMvc.perform(get("/api/_search/major-incident-sources?query=id:" + majorIncidentSource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(majorIncidentSource.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
