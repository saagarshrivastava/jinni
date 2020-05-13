package com.redhat.jinni.web.rest;

import com.redhat.jinni.JinniApp;
import com.redhat.jinni.domain.SupportPerson;
import com.redhat.jinni.repository.SupportPersonRepository;
import com.redhat.jinni.repository.search.SupportPersonSearchRepository;

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
 * Integration tests for the {@link SupportPersonResource} REST controller.
 */
@SpringBootTest(classes = JinniApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SupportPersonResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE = "BBBBBBBBBB";

    @Autowired
    private SupportPersonRepository supportPersonRepository;

    /**
     * This repository is mocked in the com.redhat.jinni.repository.search test package.
     *
     * @see com.redhat.jinni.repository.search.SupportPersonSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupportPersonSearchRepository mockSupportPersonSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupportPersonMockMvc;

    private SupportPerson supportPerson;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupportPerson createEntity(EntityManager em) {
        SupportPerson supportPerson = new SupportPerson()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .office(DEFAULT_OFFICE);
        return supportPerson;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupportPerson createUpdatedEntity(EntityManager em) {
        SupportPerson supportPerson = new SupportPerson()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .office(UPDATED_OFFICE);
        return supportPerson;
    }

    @BeforeEach
    public void initTest() {
        supportPerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupportPerson() throws Exception {
        int databaseSizeBeforeCreate = supportPersonRepository.findAll().size();

        // Create the SupportPerson
        restSupportPersonMockMvc.perform(post("/api/support-people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supportPerson)))
            .andExpect(status().isCreated());

        // Validate the SupportPerson in the database
        List<SupportPerson> supportPersonList = supportPersonRepository.findAll();
        assertThat(supportPersonList).hasSize(databaseSizeBeforeCreate + 1);
        SupportPerson testSupportPerson = supportPersonList.get(supportPersonList.size() - 1);
        assertThat(testSupportPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSupportPerson.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSupportPerson.getOffice()).isEqualTo(DEFAULT_OFFICE);

        // Validate the SupportPerson in Elasticsearch
        verify(mockSupportPersonSearchRepository, times(1)).save(testSupportPerson);
    }

    @Test
    @Transactional
    public void createSupportPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supportPersonRepository.findAll().size();

        // Create the SupportPerson with an existing ID
        supportPerson.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupportPersonMockMvc.perform(post("/api/support-people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supportPerson)))
            .andExpect(status().isBadRequest());

        // Validate the SupportPerson in the database
        List<SupportPerson> supportPersonList = supportPersonRepository.findAll();
        assertThat(supportPersonList).hasSize(databaseSizeBeforeCreate);

        // Validate the SupportPerson in Elasticsearch
        verify(mockSupportPersonSearchRepository, times(0)).save(supportPerson);
    }


    @Test
    @Transactional
    public void getAllSupportPeople() throws Exception {
        // Initialize the database
        supportPersonRepository.saveAndFlush(supportPerson);

        // Get all the supportPersonList
        restSupportPersonMockMvc.perform(get("/api/support-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supportPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].office").value(hasItem(DEFAULT_OFFICE)));
    }
    
    @Test
    @Transactional
    public void getSupportPerson() throws Exception {
        // Initialize the database
        supportPersonRepository.saveAndFlush(supportPerson);

        // Get the supportPerson
        restSupportPersonMockMvc.perform(get("/api/support-people/{id}", supportPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supportPerson.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.office").value(DEFAULT_OFFICE));
    }

    @Test
    @Transactional
    public void getNonExistingSupportPerson() throws Exception {
        // Get the supportPerson
        restSupportPersonMockMvc.perform(get("/api/support-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupportPerson() throws Exception {
        // Initialize the database
        supportPersonRepository.saveAndFlush(supportPerson);

        int databaseSizeBeforeUpdate = supportPersonRepository.findAll().size();

        // Update the supportPerson
        SupportPerson updatedSupportPerson = supportPersonRepository.findById(supportPerson.getId()).get();
        // Disconnect from session so that the updates on updatedSupportPerson are not directly saved in db
        em.detach(updatedSupportPerson);
        updatedSupportPerson
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .office(UPDATED_OFFICE);

        restSupportPersonMockMvc.perform(put("/api/support-people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSupportPerson)))
            .andExpect(status().isOk());

        // Validate the SupportPerson in the database
        List<SupportPerson> supportPersonList = supportPersonRepository.findAll();
        assertThat(supportPersonList).hasSize(databaseSizeBeforeUpdate);
        SupportPerson testSupportPerson = supportPersonList.get(supportPersonList.size() - 1);
        assertThat(testSupportPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSupportPerson.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSupportPerson.getOffice()).isEqualTo(UPDATED_OFFICE);

        // Validate the SupportPerson in Elasticsearch
        verify(mockSupportPersonSearchRepository, times(1)).save(testSupportPerson);
    }

    @Test
    @Transactional
    public void updateNonExistingSupportPerson() throws Exception {
        int databaseSizeBeforeUpdate = supportPersonRepository.findAll().size();

        // Create the SupportPerson

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupportPersonMockMvc.perform(put("/api/support-people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supportPerson)))
            .andExpect(status().isBadRequest());

        // Validate the SupportPerson in the database
        List<SupportPerson> supportPersonList = supportPersonRepository.findAll();
        assertThat(supportPersonList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SupportPerson in Elasticsearch
        verify(mockSupportPersonSearchRepository, times(0)).save(supportPerson);
    }

    @Test
    @Transactional
    public void deleteSupportPerson() throws Exception {
        // Initialize the database
        supportPersonRepository.saveAndFlush(supportPerson);

        int databaseSizeBeforeDelete = supportPersonRepository.findAll().size();

        // Delete the supportPerson
        restSupportPersonMockMvc.perform(delete("/api/support-people/{id}", supportPerson.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupportPerson> supportPersonList = supportPersonRepository.findAll();
        assertThat(supportPersonList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SupportPerson in Elasticsearch
        verify(mockSupportPersonSearchRepository, times(1)).deleteById(supportPerson.getId());
    }

    @Test
    @Transactional
    public void searchSupportPerson() throws Exception {
        // Initialize the database
        supportPersonRepository.saveAndFlush(supportPerson);
        when(mockSupportPersonSearchRepository.search(queryStringQuery("id:" + supportPerson.getId())))
            .thenReturn(Collections.singletonList(supportPerson));
        // Search the supportPerson
        restSupportPersonMockMvc.perform(get("/api/_search/support-people?query=id:" + supportPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supportPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].office").value(hasItem(DEFAULT_OFFICE)));
    }
}
