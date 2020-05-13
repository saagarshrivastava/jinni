package com.redhat.jinni.web.rest;

import com.redhat.jinni.JinniApp;
import com.redhat.jinni.domain.DeliveryStatus;
import com.redhat.jinni.repository.DeliveryStatusRepository;
import com.redhat.jinni.repository.search.DeliveryStatusSearchRepository;

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
 * Integration tests for the {@link DeliveryStatusResource} REST controller.
 */
@SpringBootTest(classes = JinniApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DeliveryStatusResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DeliveryStatusRepository deliveryStatusRepository;

    /**
     * This repository is mocked in the com.redhat.jinni.repository.search test package.
     *
     * @see com.redhat.jinni.repository.search.DeliveryStatusSearchRepositoryMockConfiguration
     */
    @Autowired
    private DeliveryStatusSearchRepository mockDeliveryStatusSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeliveryStatusMockMvc;

    private DeliveryStatus deliveryStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryStatus createEntity(EntityManager em) {
        DeliveryStatus deliveryStatus = new DeliveryStatus()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return deliveryStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryStatus createUpdatedEntity(EntityManager em) {
        DeliveryStatus deliveryStatus = new DeliveryStatus()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return deliveryStatus;
    }

    @BeforeEach
    public void initTest() {
        deliveryStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeliveryStatus() throws Exception {
        int databaseSizeBeforeCreate = deliveryStatusRepository.findAll().size();

        // Create the DeliveryStatus
        restDeliveryStatusMockMvc.perform(post("/api/delivery-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deliveryStatus)))
            .andExpect(status().isCreated());

        // Validate the DeliveryStatus in the database
        List<DeliveryStatus> deliveryStatusList = deliveryStatusRepository.findAll();
        assertThat(deliveryStatusList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryStatus testDeliveryStatus = deliveryStatusList.get(deliveryStatusList.size() - 1);
        assertThat(testDeliveryStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDeliveryStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the DeliveryStatus in Elasticsearch
        verify(mockDeliveryStatusSearchRepository, times(1)).save(testDeliveryStatus);
    }

    @Test
    @Transactional
    public void createDeliveryStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveryStatusRepository.findAll().size();

        // Create the DeliveryStatus with an existing ID
        deliveryStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryStatusMockMvc.perform(post("/api/delivery-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deliveryStatus)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryStatus in the database
        List<DeliveryStatus> deliveryStatusList = deliveryStatusRepository.findAll();
        assertThat(deliveryStatusList).hasSize(databaseSizeBeforeCreate);

        // Validate the DeliveryStatus in Elasticsearch
        verify(mockDeliveryStatusSearchRepository, times(0)).save(deliveryStatus);
    }


    @Test
    @Transactional
    public void getAllDeliveryStatuses() throws Exception {
        // Initialize the database
        deliveryStatusRepository.saveAndFlush(deliveryStatus);

        // Get all the deliveryStatusList
        restDeliveryStatusMockMvc.perform(get("/api/delivery-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getDeliveryStatus() throws Exception {
        // Initialize the database
        deliveryStatusRepository.saveAndFlush(deliveryStatus);

        // Get the deliveryStatus
        restDeliveryStatusMockMvc.perform(get("/api/delivery-statuses/{id}", deliveryStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingDeliveryStatus() throws Exception {
        // Get the deliveryStatus
        restDeliveryStatusMockMvc.perform(get("/api/delivery-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeliveryStatus() throws Exception {
        // Initialize the database
        deliveryStatusRepository.saveAndFlush(deliveryStatus);

        int databaseSizeBeforeUpdate = deliveryStatusRepository.findAll().size();

        // Update the deliveryStatus
        DeliveryStatus updatedDeliveryStatus = deliveryStatusRepository.findById(deliveryStatus.getId()).get();
        // Disconnect from session so that the updates on updatedDeliveryStatus are not directly saved in db
        em.detach(updatedDeliveryStatus);
        updatedDeliveryStatus
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restDeliveryStatusMockMvc.perform(put("/api/delivery-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeliveryStatus)))
            .andExpect(status().isOk());

        // Validate the DeliveryStatus in the database
        List<DeliveryStatus> deliveryStatusList = deliveryStatusRepository.findAll();
        assertThat(deliveryStatusList).hasSize(databaseSizeBeforeUpdate);
        DeliveryStatus testDeliveryStatus = deliveryStatusList.get(deliveryStatusList.size() - 1);
        assertThat(testDeliveryStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDeliveryStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the DeliveryStatus in Elasticsearch
        verify(mockDeliveryStatusSearchRepository, times(1)).save(testDeliveryStatus);
    }

    @Test
    @Transactional
    public void updateNonExistingDeliveryStatus() throws Exception {
        int databaseSizeBeforeUpdate = deliveryStatusRepository.findAll().size();

        // Create the DeliveryStatus

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryStatusMockMvc.perform(put("/api/delivery-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deliveryStatus)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryStatus in the database
        List<DeliveryStatus> deliveryStatusList = deliveryStatusRepository.findAll();
        assertThat(deliveryStatusList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DeliveryStatus in Elasticsearch
        verify(mockDeliveryStatusSearchRepository, times(0)).save(deliveryStatus);
    }

    @Test
    @Transactional
    public void deleteDeliveryStatus() throws Exception {
        // Initialize the database
        deliveryStatusRepository.saveAndFlush(deliveryStatus);

        int databaseSizeBeforeDelete = deliveryStatusRepository.findAll().size();

        // Delete the deliveryStatus
        restDeliveryStatusMockMvc.perform(delete("/api/delivery-statuses/{id}", deliveryStatus.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeliveryStatus> deliveryStatusList = deliveryStatusRepository.findAll();
        assertThat(deliveryStatusList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DeliveryStatus in Elasticsearch
        verify(mockDeliveryStatusSearchRepository, times(1)).deleteById(deliveryStatus.getId());
    }

    @Test
    @Transactional
    public void searchDeliveryStatus() throws Exception {
        // Initialize the database
        deliveryStatusRepository.saveAndFlush(deliveryStatus);
        when(mockDeliveryStatusSearchRepository.search(queryStringQuery("id:" + deliveryStatus.getId())))
            .thenReturn(Collections.singletonList(deliveryStatus));
        // Search the deliveryStatus
        restDeliveryStatusMockMvc.perform(get("/api/_search/delivery-statuses?query=id:" + deliveryStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
