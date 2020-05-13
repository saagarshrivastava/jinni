package com.redhat.jinni.web.rest;

import com.redhat.jinni.JinniApp;
import com.redhat.jinni.domain.OfferType;
import com.redhat.jinni.repository.OfferTypeRepository;
import com.redhat.jinni.repository.search.OfferTypeSearchRepository;

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
 * Integration tests for the {@link OfferTypeResource} REST controller.
 */
@SpringBootTest(classes = JinniApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class OfferTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OfferTypeRepository offerTypeRepository;

    /**
     * This repository is mocked in the com.redhat.jinni.repository.search test package.
     *
     * @see com.redhat.jinni.repository.search.OfferTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private OfferTypeSearchRepository mockOfferTypeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfferTypeMockMvc;

    private OfferType offerType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfferType createEntity(EntityManager em) {
        OfferType offerType = new OfferType()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return offerType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfferType createUpdatedEntity(EntityManager em) {
        OfferType offerType = new OfferType()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return offerType;
    }

    @BeforeEach
    public void initTest() {
        offerType = createEntity(em);
    }

    @Test
    @Transactional
    public void createOfferType() throws Exception {
        int databaseSizeBeforeCreate = offerTypeRepository.findAll().size();

        // Create the OfferType
        restOfferTypeMockMvc.perform(post("/api/offer-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerType)))
            .andExpect(status().isCreated());

        // Validate the OfferType in the database
        List<OfferType> offerTypeList = offerTypeRepository.findAll();
        assertThat(offerTypeList).hasSize(databaseSizeBeforeCreate + 1);
        OfferType testOfferType = offerTypeList.get(offerTypeList.size() - 1);
        assertThat(testOfferType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOfferType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the OfferType in Elasticsearch
        verify(mockOfferTypeSearchRepository, times(1)).save(testOfferType);
    }

    @Test
    @Transactional
    public void createOfferTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offerTypeRepository.findAll().size();

        // Create the OfferType with an existing ID
        offerType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferTypeMockMvc.perform(post("/api/offer-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerType)))
            .andExpect(status().isBadRequest());

        // Validate the OfferType in the database
        List<OfferType> offerTypeList = offerTypeRepository.findAll();
        assertThat(offerTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the OfferType in Elasticsearch
        verify(mockOfferTypeSearchRepository, times(0)).save(offerType);
    }


    @Test
    @Transactional
    public void getAllOfferTypes() throws Exception {
        // Initialize the database
        offerTypeRepository.saveAndFlush(offerType);

        // Get all the offerTypeList
        restOfferTypeMockMvc.perform(get("/api/offer-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offerType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getOfferType() throws Exception {
        // Initialize the database
        offerTypeRepository.saveAndFlush(offerType);

        // Get the offerType
        restOfferTypeMockMvc.perform(get("/api/offer-types/{id}", offerType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offerType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingOfferType() throws Exception {
        // Get the offerType
        restOfferTypeMockMvc.perform(get("/api/offer-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfferType() throws Exception {
        // Initialize the database
        offerTypeRepository.saveAndFlush(offerType);

        int databaseSizeBeforeUpdate = offerTypeRepository.findAll().size();

        // Update the offerType
        OfferType updatedOfferType = offerTypeRepository.findById(offerType.getId()).get();
        // Disconnect from session so that the updates on updatedOfferType are not directly saved in db
        em.detach(updatedOfferType);
        updatedOfferType
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restOfferTypeMockMvc.perform(put("/api/offer-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOfferType)))
            .andExpect(status().isOk());

        // Validate the OfferType in the database
        List<OfferType> offerTypeList = offerTypeRepository.findAll();
        assertThat(offerTypeList).hasSize(databaseSizeBeforeUpdate);
        OfferType testOfferType = offerTypeList.get(offerTypeList.size() - 1);
        assertThat(testOfferType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOfferType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the OfferType in Elasticsearch
        verify(mockOfferTypeSearchRepository, times(1)).save(testOfferType);
    }

    @Test
    @Transactional
    public void updateNonExistingOfferType() throws Exception {
        int databaseSizeBeforeUpdate = offerTypeRepository.findAll().size();

        // Create the OfferType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferTypeMockMvc.perform(put("/api/offer-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerType)))
            .andExpect(status().isBadRequest());

        // Validate the OfferType in the database
        List<OfferType> offerTypeList = offerTypeRepository.findAll();
        assertThat(offerTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OfferType in Elasticsearch
        verify(mockOfferTypeSearchRepository, times(0)).save(offerType);
    }

    @Test
    @Transactional
    public void deleteOfferType() throws Exception {
        // Initialize the database
        offerTypeRepository.saveAndFlush(offerType);

        int databaseSizeBeforeDelete = offerTypeRepository.findAll().size();

        // Delete the offerType
        restOfferTypeMockMvc.perform(delete("/api/offer-types/{id}", offerType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OfferType> offerTypeList = offerTypeRepository.findAll();
        assertThat(offerTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OfferType in Elasticsearch
        verify(mockOfferTypeSearchRepository, times(1)).deleteById(offerType.getId());
    }

    @Test
    @Transactional
    public void searchOfferType() throws Exception {
        // Initialize the database
        offerTypeRepository.saveAndFlush(offerType);
        when(mockOfferTypeSearchRepository.search(queryStringQuery("id:" + offerType.getId())))
            .thenReturn(Collections.singletonList(offerType));
        // Search the offerType
        restOfferTypeMockMvc.perform(get("/api/_search/offer-types?query=id:" + offerType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offerType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
