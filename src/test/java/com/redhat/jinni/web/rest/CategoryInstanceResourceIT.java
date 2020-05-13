package com.redhat.jinni.web.rest;

import com.redhat.jinni.JinniApp;
import com.redhat.jinni.domain.CategoryInstance;
import com.redhat.jinni.repository.CategoryInstanceRepository;
import com.redhat.jinni.repository.search.CategoryInstanceSearchRepository;

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
 * Integration tests for the {@link CategoryInstanceResource} REST controller.
 */
@SpringBootTest(classes = JinniApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CategoryInstanceResourceIT {

    private static final Integer DEFAULT_INCIDENTID = 1;
    private static final Integer UPDATED_INCIDENTID = 2;

    private static final Integer DEFAULT_CATEGORYID = 1;
    private static final Integer UPDATED_CATEGORYID = 2;

    private static final Integer DEFAULT_RANK = 1;
    private static final Integer UPDATED_RANK = 2;

    @Autowired
    private CategoryInstanceRepository categoryInstanceRepository;

    /**
     * This repository is mocked in the com.redhat.jinni.repository.search test package.
     *
     * @see com.redhat.jinni.repository.search.CategoryInstanceSearchRepositoryMockConfiguration
     */
    @Autowired
    private CategoryInstanceSearchRepository mockCategoryInstanceSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryInstanceMockMvc;

    private CategoryInstance categoryInstance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryInstance createEntity(EntityManager em) {
        CategoryInstance categoryInstance = new CategoryInstance()
            .incidentid(DEFAULT_INCIDENTID)
            .categoryid(DEFAULT_CATEGORYID)
            .rank(DEFAULT_RANK);
        return categoryInstance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryInstance createUpdatedEntity(EntityManager em) {
        CategoryInstance categoryInstance = new CategoryInstance()
            .incidentid(UPDATED_INCIDENTID)
            .categoryid(UPDATED_CATEGORYID)
            .rank(UPDATED_RANK);
        return categoryInstance;
    }

    @BeforeEach
    public void initTest() {
        categoryInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoryInstance() throws Exception {
        int databaseSizeBeforeCreate = categoryInstanceRepository.findAll().size();

        // Create the CategoryInstance
        restCategoryInstanceMockMvc.perform(post("/api/category-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryInstance)))
            .andExpect(status().isCreated());

        // Validate the CategoryInstance in the database
        List<CategoryInstance> categoryInstanceList = categoryInstanceRepository.findAll();
        assertThat(categoryInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryInstance testCategoryInstance = categoryInstanceList.get(categoryInstanceList.size() - 1);
        assertThat(testCategoryInstance.getIncidentid()).isEqualTo(DEFAULT_INCIDENTID);
        assertThat(testCategoryInstance.getCategoryid()).isEqualTo(DEFAULT_CATEGORYID);
        assertThat(testCategoryInstance.getRank()).isEqualTo(DEFAULT_RANK);

        // Validate the CategoryInstance in Elasticsearch
        verify(mockCategoryInstanceSearchRepository, times(1)).save(testCategoryInstance);
    }

    @Test
    @Transactional
    public void createCategoryInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryInstanceRepository.findAll().size();

        // Create the CategoryInstance with an existing ID
        categoryInstance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryInstanceMockMvc.perform(post("/api/category-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryInstance)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryInstance in the database
        List<CategoryInstance> categoryInstanceList = categoryInstanceRepository.findAll();
        assertThat(categoryInstanceList).hasSize(databaseSizeBeforeCreate);

        // Validate the CategoryInstance in Elasticsearch
        verify(mockCategoryInstanceSearchRepository, times(0)).save(categoryInstance);
    }


    @Test
    @Transactional
    public void getAllCategoryInstances() throws Exception {
        // Initialize the database
        categoryInstanceRepository.saveAndFlush(categoryInstance);

        // Get all the categoryInstanceList
        restCategoryInstanceMockMvc.perform(get("/api/category-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].incidentid").value(hasItem(DEFAULT_INCIDENTID)))
            .andExpect(jsonPath("$.[*].categoryid").value(hasItem(DEFAULT_CATEGORYID)))
            .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK)));
    }
    
    @Test
    @Transactional
    public void getCategoryInstance() throws Exception {
        // Initialize the database
        categoryInstanceRepository.saveAndFlush(categoryInstance);

        // Get the categoryInstance
        restCategoryInstanceMockMvc.perform(get("/api/category-instances/{id}", categoryInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoryInstance.getId().intValue()))
            .andExpect(jsonPath("$.incidentid").value(DEFAULT_INCIDENTID))
            .andExpect(jsonPath("$.categoryid").value(DEFAULT_CATEGORYID))
            .andExpect(jsonPath("$.rank").value(DEFAULT_RANK));
    }

    @Test
    @Transactional
    public void getNonExistingCategoryInstance() throws Exception {
        // Get the categoryInstance
        restCategoryInstanceMockMvc.perform(get("/api/category-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryInstance() throws Exception {
        // Initialize the database
        categoryInstanceRepository.saveAndFlush(categoryInstance);

        int databaseSizeBeforeUpdate = categoryInstanceRepository.findAll().size();

        // Update the categoryInstance
        CategoryInstance updatedCategoryInstance = categoryInstanceRepository.findById(categoryInstance.getId()).get();
        // Disconnect from session so that the updates on updatedCategoryInstance are not directly saved in db
        em.detach(updatedCategoryInstance);
        updatedCategoryInstance
            .incidentid(UPDATED_INCIDENTID)
            .categoryid(UPDATED_CATEGORYID)
            .rank(UPDATED_RANK);

        restCategoryInstanceMockMvc.perform(put("/api/category-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategoryInstance)))
            .andExpect(status().isOk());

        // Validate the CategoryInstance in the database
        List<CategoryInstance> categoryInstanceList = categoryInstanceRepository.findAll();
        assertThat(categoryInstanceList).hasSize(databaseSizeBeforeUpdate);
        CategoryInstance testCategoryInstance = categoryInstanceList.get(categoryInstanceList.size() - 1);
        assertThat(testCategoryInstance.getIncidentid()).isEqualTo(UPDATED_INCIDENTID);
        assertThat(testCategoryInstance.getCategoryid()).isEqualTo(UPDATED_CATEGORYID);
        assertThat(testCategoryInstance.getRank()).isEqualTo(UPDATED_RANK);

        // Validate the CategoryInstance in Elasticsearch
        verify(mockCategoryInstanceSearchRepository, times(1)).save(testCategoryInstance);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoryInstance() throws Exception {
        int databaseSizeBeforeUpdate = categoryInstanceRepository.findAll().size();

        // Create the CategoryInstance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryInstanceMockMvc.perform(put("/api/category-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryInstance)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryInstance in the database
        List<CategoryInstance> categoryInstanceList = categoryInstanceRepository.findAll();
        assertThat(categoryInstanceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CategoryInstance in Elasticsearch
        verify(mockCategoryInstanceSearchRepository, times(0)).save(categoryInstance);
    }

    @Test
    @Transactional
    public void deleteCategoryInstance() throws Exception {
        // Initialize the database
        categoryInstanceRepository.saveAndFlush(categoryInstance);

        int databaseSizeBeforeDelete = categoryInstanceRepository.findAll().size();

        // Delete the categoryInstance
        restCategoryInstanceMockMvc.perform(delete("/api/category-instances/{id}", categoryInstance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoryInstance> categoryInstanceList = categoryInstanceRepository.findAll();
        assertThat(categoryInstanceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CategoryInstance in Elasticsearch
        verify(mockCategoryInstanceSearchRepository, times(1)).deleteById(categoryInstance.getId());
    }

    @Test
    @Transactional
    public void searchCategoryInstance() throws Exception {
        // Initialize the database
        categoryInstanceRepository.saveAndFlush(categoryInstance);
        when(mockCategoryInstanceSearchRepository.search(queryStringQuery("id:" + categoryInstance.getId())))
            .thenReturn(Collections.singletonList(categoryInstance));
        // Search the categoryInstance
        restCategoryInstanceMockMvc.perform(get("/api/_search/category-instances?query=id:" + categoryInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].incidentid").value(hasItem(DEFAULT_INCIDENTID)))
            .andExpect(jsonPath("$.[*].categoryid").value(hasItem(DEFAULT_CATEGORYID)))
            .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK)));
    }
}
