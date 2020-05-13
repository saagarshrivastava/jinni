package com.redhat.jinni.web.rest;

import com.redhat.jinni.JinniApp;
import com.redhat.jinni.domain.Proctor;
import com.redhat.jinni.repository.ProctorRepository;
import com.redhat.jinni.repository.search.ProctorSearchRepository;

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
 * Integration tests for the {@link ProctorResource} REST controller.
 */
@SpringBootTest(classes = JinniApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProctorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE = "BBBBBBBBBB";

    @Autowired
    private ProctorRepository proctorRepository;

    /**
     * This repository is mocked in the com.redhat.jinni.repository.search test package.
     *
     * @see com.redhat.jinni.repository.search.ProctorSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProctorSearchRepository mockProctorSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProctorMockMvc;

    private Proctor proctor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proctor createEntity(EntityManager em) {
        Proctor proctor = new Proctor()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .office(DEFAULT_OFFICE);
        return proctor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proctor createUpdatedEntity(EntityManager em) {
        Proctor proctor = new Proctor()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .office(UPDATED_OFFICE);
        return proctor;
    }

    @BeforeEach
    public void initTest() {
        proctor = createEntity(em);
    }

    @Test
    @Transactional
    public void createProctor() throws Exception {
        int databaseSizeBeforeCreate = proctorRepository.findAll().size();

        // Create the Proctor
        restProctorMockMvc.perform(post("/api/proctors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proctor)))
            .andExpect(status().isCreated());

        // Validate the Proctor in the database
        List<Proctor> proctorList = proctorRepository.findAll();
        assertThat(proctorList).hasSize(databaseSizeBeforeCreate + 1);
        Proctor testProctor = proctorList.get(proctorList.size() - 1);
        assertThat(testProctor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProctor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProctor.getOffice()).isEqualTo(DEFAULT_OFFICE);

        // Validate the Proctor in Elasticsearch
        verify(mockProctorSearchRepository, times(1)).save(testProctor);
    }

    @Test
    @Transactional
    public void createProctorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proctorRepository.findAll().size();

        // Create the Proctor with an existing ID
        proctor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProctorMockMvc.perform(post("/api/proctors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proctor)))
            .andExpect(status().isBadRequest());

        // Validate the Proctor in the database
        List<Proctor> proctorList = proctorRepository.findAll();
        assertThat(proctorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Proctor in Elasticsearch
        verify(mockProctorSearchRepository, times(0)).save(proctor);
    }


    @Test
    @Transactional
    public void getAllProctors() throws Exception {
        // Initialize the database
        proctorRepository.saveAndFlush(proctor);

        // Get all the proctorList
        restProctorMockMvc.perform(get("/api/proctors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proctor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].office").value(hasItem(DEFAULT_OFFICE)));
    }
    
    @Test
    @Transactional
    public void getProctor() throws Exception {
        // Initialize the database
        proctorRepository.saveAndFlush(proctor);

        // Get the proctor
        restProctorMockMvc.perform(get("/api/proctors/{id}", proctor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proctor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.office").value(DEFAULT_OFFICE));
    }

    @Test
    @Transactional
    public void getNonExistingProctor() throws Exception {
        // Get the proctor
        restProctorMockMvc.perform(get("/api/proctors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProctor() throws Exception {
        // Initialize the database
        proctorRepository.saveAndFlush(proctor);

        int databaseSizeBeforeUpdate = proctorRepository.findAll().size();

        // Update the proctor
        Proctor updatedProctor = proctorRepository.findById(proctor.getId()).get();
        // Disconnect from session so that the updates on updatedProctor are not directly saved in db
        em.detach(updatedProctor);
        updatedProctor
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .office(UPDATED_OFFICE);

        restProctorMockMvc.perform(put("/api/proctors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProctor)))
            .andExpect(status().isOk());

        // Validate the Proctor in the database
        List<Proctor> proctorList = proctorRepository.findAll();
        assertThat(proctorList).hasSize(databaseSizeBeforeUpdate);
        Proctor testProctor = proctorList.get(proctorList.size() - 1);
        assertThat(testProctor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProctor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProctor.getOffice()).isEqualTo(UPDATED_OFFICE);

        // Validate the Proctor in Elasticsearch
        verify(mockProctorSearchRepository, times(1)).save(testProctor);
    }

    @Test
    @Transactional
    public void updateNonExistingProctor() throws Exception {
        int databaseSizeBeforeUpdate = proctorRepository.findAll().size();

        // Create the Proctor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProctorMockMvc.perform(put("/api/proctors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proctor)))
            .andExpect(status().isBadRequest());

        // Validate the Proctor in the database
        List<Proctor> proctorList = proctorRepository.findAll();
        assertThat(proctorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Proctor in Elasticsearch
        verify(mockProctorSearchRepository, times(0)).save(proctor);
    }

    @Test
    @Transactional
    public void deleteProctor() throws Exception {
        // Initialize the database
        proctorRepository.saveAndFlush(proctor);

        int databaseSizeBeforeDelete = proctorRepository.findAll().size();

        // Delete the proctor
        restProctorMockMvc.perform(delete("/api/proctors/{id}", proctor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Proctor> proctorList = proctorRepository.findAll();
        assertThat(proctorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Proctor in Elasticsearch
        verify(mockProctorSearchRepository, times(1)).deleteById(proctor.getId());
    }

    @Test
    @Transactional
    public void searchProctor() throws Exception {
        // Initialize the database
        proctorRepository.saveAndFlush(proctor);
        when(mockProctorSearchRepository.search(queryStringQuery("id:" + proctor.getId())))
            .thenReturn(Collections.singletonList(proctor));
        // Search the proctor
        restProctorMockMvc.perform(get("/api/_search/proctors?query=id:" + proctor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proctor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].office").value(hasItem(DEFAULT_OFFICE)));
    }
}
