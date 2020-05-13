package com.redhat.jinni.web.rest;

import com.redhat.jinni.JinniApp;
import com.redhat.jinni.domain.Offer;
import com.redhat.jinni.repository.OfferRepository;
import com.redhat.jinni.repository.search.OfferSearchRepository;

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
 * Integration tests for the {@link OfferResource} REST controller.
 */
@SpringBootTest(classes = JinniApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class OfferResourceIT {

    private static final Integer DEFAULT_INCIDENTID = 1;
    private static final Integer UPDATED_INCIDENTID = 2;

    private static final Integer DEFAULT_OFFERTYPEID = 1;
    private static final Integer UPDATED_OFFERTYPEID = 2;

    private static final Integer DEFAULT_EXAMID = 1;
    private static final Integer UPDATED_EXAMID = 2;

    private static final Integer DEFAULT_DISCOUNTPERCENTAGE = 1;
    private static final Integer UPDATED_DISCOUNTPERCENTAGE = 2;

    @Autowired
    private OfferRepository offerRepository;

    /**
     * This repository is mocked in the com.redhat.jinni.repository.search test package.
     *
     * @see com.redhat.jinni.repository.search.OfferSearchRepositoryMockConfiguration
     */
    @Autowired
    private OfferSearchRepository mockOfferSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfferMockMvc;

    private Offer offer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createEntity(EntityManager em) {
        Offer offer = new Offer()
            .incidentid(DEFAULT_INCIDENTID)
            .offertypeid(DEFAULT_OFFERTYPEID)
            .examid(DEFAULT_EXAMID)
            .discountpercentage(DEFAULT_DISCOUNTPERCENTAGE);
        return offer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createUpdatedEntity(EntityManager em) {
        Offer offer = new Offer()
            .incidentid(UPDATED_INCIDENTID)
            .offertypeid(UPDATED_OFFERTYPEID)
            .examid(UPDATED_EXAMID)
            .discountpercentage(UPDATED_DISCOUNTPERCENTAGE);
        return offer;
    }

    @BeforeEach
    public void initTest() {
        offer = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffer() throws Exception {
        int databaseSizeBeforeCreate = offerRepository.findAll().size();

        // Create the Offer
        restOfferMockMvc.perform(post("/api/offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offer)))
            .andExpect(status().isCreated());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate + 1);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getIncidentid()).isEqualTo(DEFAULT_INCIDENTID);
        assertThat(testOffer.getOffertypeid()).isEqualTo(DEFAULT_OFFERTYPEID);
        assertThat(testOffer.getExamid()).isEqualTo(DEFAULT_EXAMID);
        assertThat(testOffer.getDiscountpercentage()).isEqualTo(DEFAULT_DISCOUNTPERCENTAGE);

        // Validate the Offer in Elasticsearch
        verify(mockOfferSearchRepository, times(1)).save(testOffer);
    }

    @Test
    @Transactional
    public void createOfferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offerRepository.findAll().size();

        // Create the Offer with an existing ID
        offer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferMockMvc.perform(post("/api/offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offer)))
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate);

        // Validate the Offer in Elasticsearch
        verify(mockOfferSearchRepository, times(0)).save(offer);
    }


    @Test
    @Transactional
    public void getAllOffers() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList
        restOfferMockMvc.perform(get("/api/offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offer.getId().intValue())))
            .andExpect(jsonPath("$.[*].incidentid").value(hasItem(DEFAULT_INCIDENTID)))
            .andExpect(jsonPath("$.[*].offertypeid").value(hasItem(DEFAULT_OFFERTYPEID)))
            .andExpect(jsonPath("$.[*].examid").value(hasItem(DEFAULT_EXAMID)))
            .andExpect(jsonPath("$.[*].discountpercentage").value(hasItem(DEFAULT_DISCOUNTPERCENTAGE)));
    }
    
    @Test
    @Transactional
    public void getOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get the offer
        restOfferMockMvc.perform(get("/api/offers/{id}", offer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offer.getId().intValue()))
            .andExpect(jsonPath("$.incidentid").value(DEFAULT_INCIDENTID))
            .andExpect(jsonPath("$.offertypeid").value(DEFAULT_OFFERTYPEID))
            .andExpect(jsonPath("$.examid").value(DEFAULT_EXAMID))
            .andExpect(jsonPath("$.discountpercentage").value(DEFAULT_DISCOUNTPERCENTAGE));
    }

    @Test
    @Transactional
    public void getNonExistingOffer() throws Exception {
        // Get the offer
        restOfferMockMvc.perform(get("/api/offers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer
        Offer updatedOffer = offerRepository.findById(offer.getId()).get();
        // Disconnect from session so that the updates on updatedOffer are not directly saved in db
        em.detach(updatedOffer);
        updatedOffer
            .incidentid(UPDATED_INCIDENTID)
            .offertypeid(UPDATED_OFFERTYPEID)
            .examid(UPDATED_EXAMID)
            .discountpercentage(UPDATED_DISCOUNTPERCENTAGE);

        restOfferMockMvc.perform(put("/api/offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOffer)))
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getIncidentid()).isEqualTo(UPDATED_INCIDENTID);
        assertThat(testOffer.getOffertypeid()).isEqualTo(UPDATED_OFFERTYPEID);
        assertThat(testOffer.getExamid()).isEqualTo(UPDATED_EXAMID);
        assertThat(testOffer.getDiscountpercentage()).isEqualTo(UPDATED_DISCOUNTPERCENTAGE);

        // Validate the Offer in Elasticsearch
        verify(mockOfferSearchRepository, times(1)).save(testOffer);
    }

    @Test
    @Transactional
    public void updateNonExistingOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Create the Offer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferMockMvc.perform(put("/api/offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offer)))
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Offer in Elasticsearch
        verify(mockOfferSearchRepository, times(0)).save(offer);
    }

    @Test
    @Transactional
    public void deleteOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeDelete = offerRepository.findAll().size();

        // Delete the offer
        restOfferMockMvc.perform(delete("/api/offers/{id}", offer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Offer in Elasticsearch
        verify(mockOfferSearchRepository, times(1)).deleteById(offer.getId());
    }

    @Test
    @Transactional
    public void searchOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);
        when(mockOfferSearchRepository.search(queryStringQuery("id:" + offer.getId())))
            .thenReturn(Collections.singletonList(offer));
        // Search the offer
        restOfferMockMvc.perform(get("/api/_search/offers?query=id:" + offer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offer.getId().intValue())))
            .andExpect(jsonPath("$.[*].incidentid").value(hasItem(DEFAULT_INCIDENTID)))
            .andExpect(jsonPath("$.[*].offertypeid").value(hasItem(DEFAULT_OFFERTYPEID)))
            .andExpect(jsonPath("$.[*].examid").value(hasItem(DEFAULT_EXAMID)))
            .andExpect(jsonPath("$.[*].discountpercentage").value(hasItem(DEFAULT_DISCOUNTPERCENTAGE)));
    }
}
