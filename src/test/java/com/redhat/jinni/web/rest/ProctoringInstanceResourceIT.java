package com.redhat.jinni.web.rest;

import com.redhat.jinni.JinniApp;
import com.redhat.jinni.domain.ProctoringInstance;
import com.redhat.jinni.repository.ProctoringInstanceRepository;
import com.redhat.jinni.repository.search.ProctoringInstanceSearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProctoringInstanceResource} REST controller.
 */
@SpringBootTest(classes = JinniApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProctoringInstanceResourceIT {

    private static final LocalDate DEFAULT_PROCTORSTARTTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PROCTORSTARTTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PROCTORENDTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PROCTORENDTIME = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PROCTORID = 1;
    private static final Integer UPDATED_PROCTORID = 2;

    private static final Integer DEFAULT_SESSIONID = 1;
    private static final Integer UPDATED_SESSIONID = 2;

    private static final String DEFAULT_SESSIONNOTES = "AAAAAAAAAA";
    private static final String UPDATED_SESSIONNOTES = "BBBBBBBBBB";

    private static final String DEFAULT_PROCTORCHAT = "AAAAAAAAAA";
    private static final String UPDATED_PROCTORCHAT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SUSPENDED = false;
    private static final Boolean UPDATED_SUSPENDED = true;

    private static final Boolean DEFAULT_TERMINATED = false;
    private static final Boolean UPDATED_TERMINATED = true;

    private static final Integer DEFAULT_NUMBEROFBREAKS = 1;
    private static final Integer UPDATED_NUMBEROFBREAKS = 2;

    @Autowired
    private ProctoringInstanceRepository proctoringInstanceRepository;

    /**
     * This repository is mocked in the com.redhat.jinni.repository.search test package.
     *
     * @see com.redhat.jinni.repository.search.ProctoringInstanceSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProctoringInstanceSearchRepository mockProctoringInstanceSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProctoringInstanceMockMvc;

    private ProctoringInstance proctoringInstance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProctoringInstance createEntity(EntityManager em) {
        ProctoringInstance proctoringInstance = new ProctoringInstance()
            .proctorstarttime(DEFAULT_PROCTORSTARTTIME)
            .proctorendtime(DEFAULT_PROCTORENDTIME)
            .proctorid(DEFAULT_PROCTORID)
            .sessionid(DEFAULT_SESSIONID)
            .sessionnotes(DEFAULT_SESSIONNOTES)
            .proctorchat(DEFAULT_PROCTORCHAT)
            .suspended(DEFAULT_SUSPENDED)
            .terminated(DEFAULT_TERMINATED)
            .numberofbreaks(DEFAULT_NUMBEROFBREAKS);
        return proctoringInstance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProctoringInstance createUpdatedEntity(EntityManager em) {
        ProctoringInstance proctoringInstance = new ProctoringInstance()
            .proctorstarttime(UPDATED_PROCTORSTARTTIME)
            .proctorendtime(UPDATED_PROCTORENDTIME)
            .proctorid(UPDATED_PROCTORID)
            .sessionid(UPDATED_SESSIONID)
            .sessionnotes(UPDATED_SESSIONNOTES)
            .proctorchat(UPDATED_PROCTORCHAT)
            .suspended(UPDATED_SUSPENDED)
            .terminated(UPDATED_TERMINATED)
            .numberofbreaks(UPDATED_NUMBEROFBREAKS);
        return proctoringInstance;
    }

    @BeforeEach
    public void initTest() {
        proctoringInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createProctoringInstance() throws Exception {
        int databaseSizeBeforeCreate = proctoringInstanceRepository.findAll().size();

        // Create the ProctoringInstance
        restProctoringInstanceMockMvc.perform(post("/api/proctoring-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proctoringInstance)))
            .andExpect(status().isCreated());

        // Validate the ProctoringInstance in the database
        List<ProctoringInstance> proctoringInstanceList = proctoringInstanceRepository.findAll();
        assertThat(proctoringInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        ProctoringInstance testProctoringInstance = proctoringInstanceList.get(proctoringInstanceList.size() - 1);
        assertThat(testProctoringInstance.getProctorstarttime()).isEqualTo(DEFAULT_PROCTORSTARTTIME);
        assertThat(testProctoringInstance.getProctorendtime()).isEqualTo(DEFAULT_PROCTORENDTIME);
        assertThat(testProctoringInstance.getProctorid()).isEqualTo(DEFAULT_PROCTORID);
        assertThat(testProctoringInstance.getSessionid()).isEqualTo(DEFAULT_SESSIONID);
        assertThat(testProctoringInstance.getSessionnotes()).isEqualTo(DEFAULT_SESSIONNOTES);
        assertThat(testProctoringInstance.getProctorchat()).isEqualTo(DEFAULT_PROCTORCHAT);
        assertThat(testProctoringInstance.isSuspended()).isEqualTo(DEFAULT_SUSPENDED);
        assertThat(testProctoringInstance.isTerminated()).isEqualTo(DEFAULT_TERMINATED);
        assertThat(testProctoringInstance.getNumberofbreaks()).isEqualTo(DEFAULT_NUMBEROFBREAKS);

        // Validate the ProctoringInstance in Elasticsearch
        verify(mockProctoringInstanceSearchRepository, times(1)).save(testProctoringInstance);
    }

    @Test
    @Transactional
    public void createProctoringInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proctoringInstanceRepository.findAll().size();

        // Create the ProctoringInstance with an existing ID
        proctoringInstance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProctoringInstanceMockMvc.perform(post("/api/proctoring-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proctoringInstance)))
            .andExpect(status().isBadRequest());

        // Validate the ProctoringInstance in the database
        List<ProctoringInstance> proctoringInstanceList = proctoringInstanceRepository.findAll();
        assertThat(proctoringInstanceList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProctoringInstance in Elasticsearch
        verify(mockProctoringInstanceSearchRepository, times(0)).save(proctoringInstance);
    }


    @Test
    @Transactional
    public void getAllProctoringInstances() throws Exception {
        // Initialize the database
        proctoringInstanceRepository.saveAndFlush(proctoringInstance);

        // Get all the proctoringInstanceList
        restProctoringInstanceMockMvc.perform(get("/api/proctoring-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proctoringInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].proctorstarttime").value(hasItem(DEFAULT_PROCTORSTARTTIME.toString())))
            .andExpect(jsonPath("$.[*].proctorendtime").value(hasItem(DEFAULT_PROCTORENDTIME.toString())))
            .andExpect(jsonPath("$.[*].proctorid").value(hasItem(DEFAULT_PROCTORID)))
            .andExpect(jsonPath("$.[*].sessionid").value(hasItem(DEFAULT_SESSIONID)))
            .andExpect(jsonPath("$.[*].sessionnotes").value(hasItem(DEFAULT_SESSIONNOTES)))
            .andExpect(jsonPath("$.[*].proctorchat").value(hasItem(DEFAULT_PROCTORCHAT)))
            .andExpect(jsonPath("$.[*].suspended").value(hasItem(DEFAULT_SUSPENDED.booleanValue())))
            .andExpect(jsonPath("$.[*].terminated").value(hasItem(DEFAULT_TERMINATED.booleanValue())))
            .andExpect(jsonPath("$.[*].numberofbreaks").value(hasItem(DEFAULT_NUMBEROFBREAKS)));
    }
    
    @Test
    @Transactional
    public void getProctoringInstance() throws Exception {
        // Initialize the database
        proctoringInstanceRepository.saveAndFlush(proctoringInstance);

        // Get the proctoringInstance
        restProctoringInstanceMockMvc.perform(get("/api/proctoring-instances/{id}", proctoringInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proctoringInstance.getId().intValue()))
            .andExpect(jsonPath("$.proctorstarttime").value(DEFAULT_PROCTORSTARTTIME.toString()))
            .andExpect(jsonPath("$.proctorendtime").value(DEFAULT_PROCTORENDTIME.toString()))
            .andExpect(jsonPath("$.proctorid").value(DEFAULT_PROCTORID))
            .andExpect(jsonPath("$.sessionid").value(DEFAULT_SESSIONID))
            .andExpect(jsonPath("$.sessionnotes").value(DEFAULT_SESSIONNOTES))
            .andExpect(jsonPath("$.proctorchat").value(DEFAULT_PROCTORCHAT))
            .andExpect(jsonPath("$.suspended").value(DEFAULT_SUSPENDED.booleanValue()))
            .andExpect(jsonPath("$.terminated").value(DEFAULT_TERMINATED.booleanValue()))
            .andExpect(jsonPath("$.numberofbreaks").value(DEFAULT_NUMBEROFBREAKS));
    }

    @Test
    @Transactional
    public void getNonExistingProctoringInstance() throws Exception {
        // Get the proctoringInstance
        restProctoringInstanceMockMvc.perform(get("/api/proctoring-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProctoringInstance() throws Exception {
        // Initialize the database
        proctoringInstanceRepository.saveAndFlush(proctoringInstance);

        int databaseSizeBeforeUpdate = proctoringInstanceRepository.findAll().size();

        // Update the proctoringInstance
        ProctoringInstance updatedProctoringInstance = proctoringInstanceRepository.findById(proctoringInstance.getId()).get();
        // Disconnect from session so that the updates on updatedProctoringInstance are not directly saved in db
        em.detach(updatedProctoringInstance);
        updatedProctoringInstance
            .proctorstarttime(UPDATED_PROCTORSTARTTIME)
            .proctorendtime(UPDATED_PROCTORENDTIME)
            .proctorid(UPDATED_PROCTORID)
            .sessionid(UPDATED_SESSIONID)
            .sessionnotes(UPDATED_SESSIONNOTES)
            .proctorchat(UPDATED_PROCTORCHAT)
            .suspended(UPDATED_SUSPENDED)
            .terminated(UPDATED_TERMINATED)
            .numberofbreaks(UPDATED_NUMBEROFBREAKS);

        restProctoringInstanceMockMvc.perform(put("/api/proctoring-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProctoringInstance)))
            .andExpect(status().isOk());

        // Validate the ProctoringInstance in the database
        List<ProctoringInstance> proctoringInstanceList = proctoringInstanceRepository.findAll();
        assertThat(proctoringInstanceList).hasSize(databaseSizeBeforeUpdate);
        ProctoringInstance testProctoringInstance = proctoringInstanceList.get(proctoringInstanceList.size() - 1);
        assertThat(testProctoringInstance.getProctorstarttime()).isEqualTo(UPDATED_PROCTORSTARTTIME);
        assertThat(testProctoringInstance.getProctorendtime()).isEqualTo(UPDATED_PROCTORENDTIME);
        assertThat(testProctoringInstance.getProctorid()).isEqualTo(UPDATED_PROCTORID);
        assertThat(testProctoringInstance.getSessionid()).isEqualTo(UPDATED_SESSIONID);
        assertThat(testProctoringInstance.getSessionnotes()).isEqualTo(UPDATED_SESSIONNOTES);
        assertThat(testProctoringInstance.getProctorchat()).isEqualTo(UPDATED_PROCTORCHAT);
        assertThat(testProctoringInstance.isSuspended()).isEqualTo(UPDATED_SUSPENDED);
        assertThat(testProctoringInstance.isTerminated()).isEqualTo(UPDATED_TERMINATED);
        assertThat(testProctoringInstance.getNumberofbreaks()).isEqualTo(UPDATED_NUMBEROFBREAKS);

        // Validate the ProctoringInstance in Elasticsearch
        verify(mockProctoringInstanceSearchRepository, times(1)).save(testProctoringInstance);
    }

    @Test
    @Transactional
    public void updateNonExistingProctoringInstance() throws Exception {
        int databaseSizeBeforeUpdate = proctoringInstanceRepository.findAll().size();

        // Create the ProctoringInstance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProctoringInstanceMockMvc.perform(put("/api/proctoring-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proctoringInstance)))
            .andExpect(status().isBadRequest());

        // Validate the ProctoringInstance in the database
        List<ProctoringInstance> proctoringInstanceList = proctoringInstanceRepository.findAll();
        assertThat(proctoringInstanceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProctoringInstance in Elasticsearch
        verify(mockProctoringInstanceSearchRepository, times(0)).save(proctoringInstance);
    }

    @Test
    @Transactional
    public void deleteProctoringInstance() throws Exception {
        // Initialize the database
        proctoringInstanceRepository.saveAndFlush(proctoringInstance);

        int databaseSizeBeforeDelete = proctoringInstanceRepository.findAll().size();

        // Delete the proctoringInstance
        restProctoringInstanceMockMvc.perform(delete("/api/proctoring-instances/{id}", proctoringInstance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProctoringInstance> proctoringInstanceList = proctoringInstanceRepository.findAll();
        assertThat(proctoringInstanceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProctoringInstance in Elasticsearch
        verify(mockProctoringInstanceSearchRepository, times(1)).deleteById(proctoringInstance.getId());
    }

    @Test
    @Transactional
    public void searchProctoringInstance() throws Exception {
        // Initialize the database
        proctoringInstanceRepository.saveAndFlush(proctoringInstance);
        when(mockProctoringInstanceSearchRepository.search(queryStringQuery("id:" + proctoringInstance.getId())))
            .thenReturn(Collections.singletonList(proctoringInstance));
        // Search the proctoringInstance
        restProctoringInstanceMockMvc.perform(get("/api/_search/proctoring-instances?query=id:" + proctoringInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proctoringInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].proctorstarttime").value(hasItem(DEFAULT_PROCTORSTARTTIME.toString())))
            .andExpect(jsonPath("$.[*].proctorendtime").value(hasItem(DEFAULT_PROCTORENDTIME.toString())))
            .andExpect(jsonPath("$.[*].proctorid").value(hasItem(DEFAULT_PROCTORID)))
            .andExpect(jsonPath("$.[*].sessionid").value(hasItem(DEFAULT_SESSIONID)))
            .andExpect(jsonPath("$.[*].sessionnotes").value(hasItem(DEFAULT_SESSIONNOTES)))
            .andExpect(jsonPath("$.[*].proctorchat").value(hasItem(DEFAULT_PROCTORCHAT)))
            .andExpect(jsonPath("$.[*].suspended").value(hasItem(DEFAULT_SUSPENDED.booleanValue())))
            .andExpect(jsonPath("$.[*].terminated").value(hasItem(DEFAULT_TERMINATED.booleanValue())))
            .andExpect(jsonPath("$.[*].numberofbreaks").value(hasItem(DEFAULT_NUMBEROFBREAKS)));
    }
}
