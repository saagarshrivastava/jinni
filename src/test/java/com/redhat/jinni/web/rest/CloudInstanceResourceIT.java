package com.redhat.jinni.web.rest;

import com.redhat.jinni.JinniApp;
import com.redhat.jinni.domain.CloudInstance;
import com.redhat.jinni.repository.CloudInstanceRepository;
import com.redhat.jinni.repository.search.CloudInstanceSearchRepository;

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
 * Integration tests for the {@link CloudInstanceResource} REST controller.
 */
@SpringBootTest(classes = JinniApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CloudInstanceResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CLOUDREGIONID = 1;
    private static final Integer UPDATED_CLOUDREGIONID = 2;

    private static final Integer DEFAULT_EXAMBACKENDID = 1;
    private static final Integer UPDATED_EXAMBACKENDID = 2;

    @Autowired
    private CloudInstanceRepository cloudInstanceRepository;

    /**
     * This repository is mocked in the com.redhat.jinni.repository.search test package.
     *
     * @see com.redhat.jinni.repository.search.CloudInstanceSearchRepositoryMockConfiguration
     */
    @Autowired
    private CloudInstanceSearchRepository mockCloudInstanceSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCloudInstanceMockMvc;

    private CloudInstance cloudInstance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CloudInstance createEntity(EntityManager em) {
        CloudInstance cloudInstance = new CloudInstance()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .cloudregionid(DEFAULT_CLOUDREGIONID)
            .exambackendid(DEFAULT_EXAMBACKENDID);
        return cloudInstance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CloudInstance createUpdatedEntity(EntityManager em) {
        CloudInstance cloudInstance = new CloudInstance()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .cloudregionid(UPDATED_CLOUDREGIONID)
            .exambackendid(UPDATED_EXAMBACKENDID);
        return cloudInstance;
    }

    @BeforeEach
    public void initTest() {
        cloudInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createCloudInstance() throws Exception {
        int databaseSizeBeforeCreate = cloudInstanceRepository.findAll().size();

        // Create the CloudInstance
        restCloudInstanceMockMvc.perform(post("/api/cloud-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cloudInstance)))
            .andExpect(status().isCreated());

        // Validate the CloudInstance in the database
        List<CloudInstance> cloudInstanceList = cloudInstanceRepository.findAll();
        assertThat(cloudInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        CloudInstance testCloudInstance = cloudInstanceList.get(cloudInstanceList.size() - 1);
        assertThat(testCloudInstance.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCloudInstance.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCloudInstance.getCloudregionid()).isEqualTo(DEFAULT_CLOUDREGIONID);
        assertThat(testCloudInstance.getExambackendid()).isEqualTo(DEFAULT_EXAMBACKENDID);

        // Validate the CloudInstance in Elasticsearch
        verify(mockCloudInstanceSearchRepository, times(1)).save(testCloudInstance);
    }

    @Test
    @Transactional
    public void createCloudInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cloudInstanceRepository.findAll().size();

        // Create the CloudInstance with an existing ID
        cloudInstance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCloudInstanceMockMvc.perform(post("/api/cloud-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cloudInstance)))
            .andExpect(status().isBadRequest());

        // Validate the CloudInstance in the database
        List<CloudInstance> cloudInstanceList = cloudInstanceRepository.findAll();
        assertThat(cloudInstanceList).hasSize(databaseSizeBeforeCreate);

        // Validate the CloudInstance in Elasticsearch
        verify(mockCloudInstanceSearchRepository, times(0)).save(cloudInstance);
    }


    @Test
    @Transactional
    public void getAllCloudInstances() throws Exception {
        // Initialize the database
        cloudInstanceRepository.saveAndFlush(cloudInstance);

        // Get all the cloudInstanceList
        restCloudInstanceMockMvc.perform(get("/api/cloud-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cloudInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cloudregionid").value(hasItem(DEFAULT_CLOUDREGIONID)))
            .andExpect(jsonPath("$.[*].exambackendid").value(hasItem(DEFAULT_EXAMBACKENDID)));
    }
    
    @Test
    @Transactional
    public void getCloudInstance() throws Exception {
        // Initialize the database
        cloudInstanceRepository.saveAndFlush(cloudInstance);

        // Get the cloudInstance
        restCloudInstanceMockMvc.perform(get("/api/cloud-instances/{id}", cloudInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cloudInstance.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.cloudregionid").value(DEFAULT_CLOUDREGIONID))
            .andExpect(jsonPath("$.exambackendid").value(DEFAULT_EXAMBACKENDID));
    }

    @Test
    @Transactional
    public void getNonExistingCloudInstance() throws Exception {
        // Get the cloudInstance
        restCloudInstanceMockMvc.perform(get("/api/cloud-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCloudInstance() throws Exception {
        // Initialize the database
        cloudInstanceRepository.saveAndFlush(cloudInstance);

        int databaseSizeBeforeUpdate = cloudInstanceRepository.findAll().size();

        // Update the cloudInstance
        CloudInstance updatedCloudInstance = cloudInstanceRepository.findById(cloudInstance.getId()).get();
        // Disconnect from session so that the updates on updatedCloudInstance are not directly saved in db
        em.detach(updatedCloudInstance);
        updatedCloudInstance
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .cloudregionid(UPDATED_CLOUDREGIONID)
            .exambackendid(UPDATED_EXAMBACKENDID);

        restCloudInstanceMockMvc.perform(put("/api/cloud-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCloudInstance)))
            .andExpect(status().isOk());

        // Validate the CloudInstance in the database
        List<CloudInstance> cloudInstanceList = cloudInstanceRepository.findAll();
        assertThat(cloudInstanceList).hasSize(databaseSizeBeforeUpdate);
        CloudInstance testCloudInstance = cloudInstanceList.get(cloudInstanceList.size() - 1);
        assertThat(testCloudInstance.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCloudInstance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCloudInstance.getCloudregionid()).isEqualTo(UPDATED_CLOUDREGIONID);
        assertThat(testCloudInstance.getExambackendid()).isEqualTo(UPDATED_EXAMBACKENDID);

        // Validate the CloudInstance in Elasticsearch
        verify(mockCloudInstanceSearchRepository, times(1)).save(testCloudInstance);
    }

    @Test
    @Transactional
    public void updateNonExistingCloudInstance() throws Exception {
        int databaseSizeBeforeUpdate = cloudInstanceRepository.findAll().size();

        // Create the CloudInstance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCloudInstanceMockMvc.perform(put("/api/cloud-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cloudInstance)))
            .andExpect(status().isBadRequest());

        // Validate the CloudInstance in the database
        List<CloudInstance> cloudInstanceList = cloudInstanceRepository.findAll();
        assertThat(cloudInstanceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CloudInstance in Elasticsearch
        verify(mockCloudInstanceSearchRepository, times(0)).save(cloudInstance);
    }

    @Test
    @Transactional
    public void deleteCloudInstance() throws Exception {
        // Initialize the database
        cloudInstanceRepository.saveAndFlush(cloudInstance);

        int databaseSizeBeforeDelete = cloudInstanceRepository.findAll().size();

        // Delete the cloudInstance
        restCloudInstanceMockMvc.perform(delete("/api/cloud-instances/{id}", cloudInstance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CloudInstance> cloudInstanceList = cloudInstanceRepository.findAll();
        assertThat(cloudInstanceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CloudInstance in Elasticsearch
        verify(mockCloudInstanceSearchRepository, times(1)).deleteById(cloudInstance.getId());
    }

    @Test
    @Transactional
    public void searchCloudInstance() throws Exception {
        // Initialize the database
        cloudInstanceRepository.saveAndFlush(cloudInstance);
        when(mockCloudInstanceSearchRepository.search(queryStringQuery("id:" + cloudInstance.getId())))
            .thenReturn(Collections.singletonList(cloudInstance));
        // Search the cloudInstance
        restCloudInstanceMockMvc.perform(get("/api/_search/cloud-instances?query=id:" + cloudInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cloudInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cloudregionid").value(hasItem(DEFAULT_CLOUDREGIONID)))
            .andExpect(jsonPath("$.[*].exambackendid").value(hasItem(DEFAULT_EXAMBACKENDID)));
    }
}
