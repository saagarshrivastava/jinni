package com.redhat.jinni.web.rest;

import com.redhat.jinni.JinniApp;
import com.redhat.jinni.domain.SessionBreaks;
import com.redhat.jinni.repository.SessionBreaksRepository;
import com.redhat.jinni.repository.search.SessionBreaksSearchRepository;

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
 * Integration tests for the {@link SessionBreaksResource} REST controller.
 */
@SpringBootTest(classes = JinniApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SessionBreaksResourceIT {

    private static final LocalDate DEFAULT_STARTTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ENDTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENDTIME = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PROCTORINGINSTANCEID = 1;
    private static final Integer UPDATED_PROCTORINGINSTANCEID = 2;

    @Autowired
    private SessionBreaksRepository sessionBreaksRepository;

    /**
     * This repository is mocked in the com.redhat.jinni.repository.search test package.
     *
     * @see com.redhat.jinni.repository.search.SessionBreaksSearchRepositoryMockConfiguration
     */
    @Autowired
    private SessionBreaksSearchRepository mockSessionBreaksSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSessionBreaksMockMvc;

    private SessionBreaks sessionBreaks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SessionBreaks createEntity(EntityManager em) {
        SessionBreaks sessionBreaks = new SessionBreaks()
            .starttime(DEFAULT_STARTTIME)
            .endtime(DEFAULT_ENDTIME)
            .proctoringinstanceid(DEFAULT_PROCTORINGINSTANCEID);
        return sessionBreaks;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SessionBreaks createUpdatedEntity(EntityManager em) {
        SessionBreaks sessionBreaks = new SessionBreaks()
            .starttime(UPDATED_STARTTIME)
            .endtime(UPDATED_ENDTIME)
            .proctoringinstanceid(UPDATED_PROCTORINGINSTANCEID);
        return sessionBreaks;
    }

    @BeforeEach
    public void initTest() {
        sessionBreaks = createEntity(em);
    }

    @Test
    @Transactional
    public void createSessionBreaks() throws Exception {
        int databaseSizeBeforeCreate = sessionBreaksRepository.findAll().size();

        // Create the SessionBreaks
        restSessionBreaksMockMvc.perform(post("/api/session-breaks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionBreaks)))
            .andExpect(status().isCreated());

        // Validate the SessionBreaks in the database
        List<SessionBreaks> sessionBreaksList = sessionBreaksRepository.findAll();
        assertThat(sessionBreaksList).hasSize(databaseSizeBeforeCreate + 1);
        SessionBreaks testSessionBreaks = sessionBreaksList.get(sessionBreaksList.size() - 1);
        assertThat(testSessionBreaks.getStarttime()).isEqualTo(DEFAULT_STARTTIME);
        assertThat(testSessionBreaks.getEndtime()).isEqualTo(DEFAULT_ENDTIME);
        assertThat(testSessionBreaks.getProctoringinstanceid()).isEqualTo(DEFAULT_PROCTORINGINSTANCEID);

        // Validate the SessionBreaks in Elasticsearch
        verify(mockSessionBreaksSearchRepository, times(1)).save(testSessionBreaks);
    }

    @Test
    @Transactional
    public void createSessionBreaksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sessionBreaksRepository.findAll().size();

        // Create the SessionBreaks with an existing ID
        sessionBreaks.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSessionBreaksMockMvc.perform(post("/api/session-breaks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionBreaks)))
            .andExpect(status().isBadRequest());

        // Validate the SessionBreaks in the database
        List<SessionBreaks> sessionBreaksList = sessionBreaksRepository.findAll();
        assertThat(sessionBreaksList).hasSize(databaseSizeBeforeCreate);

        // Validate the SessionBreaks in Elasticsearch
        verify(mockSessionBreaksSearchRepository, times(0)).save(sessionBreaks);
    }


    @Test
    @Transactional
    public void getAllSessionBreaks() throws Exception {
        // Initialize the database
        sessionBreaksRepository.saveAndFlush(sessionBreaks);

        // Get all the sessionBreaksList
        restSessionBreaksMockMvc.perform(get("/api/session-breaks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sessionBreaks.getId().intValue())))
            .andExpect(jsonPath("$.[*].starttime").value(hasItem(DEFAULT_STARTTIME.toString())))
            .andExpect(jsonPath("$.[*].endtime").value(hasItem(DEFAULT_ENDTIME.toString())))
            .andExpect(jsonPath("$.[*].proctoringinstanceid").value(hasItem(DEFAULT_PROCTORINGINSTANCEID)));
    }
    
    @Test
    @Transactional
    public void getSessionBreaks() throws Exception {
        // Initialize the database
        sessionBreaksRepository.saveAndFlush(sessionBreaks);

        // Get the sessionBreaks
        restSessionBreaksMockMvc.perform(get("/api/session-breaks/{id}", sessionBreaks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sessionBreaks.getId().intValue()))
            .andExpect(jsonPath("$.starttime").value(DEFAULT_STARTTIME.toString()))
            .andExpect(jsonPath("$.endtime").value(DEFAULT_ENDTIME.toString()))
            .andExpect(jsonPath("$.proctoringinstanceid").value(DEFAULT_PROCTORINGINSTANCEID));
    }

    @Test
    @Transactional
    public void getNonExistingSessionBreaks() throws Exception {
        // Get the sessionBreaks
        restSessionBreaksMockMvc.perform(get("/api/session-breaks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSessionBreaks() throws Exception {
        // Initialize the database
        sessionBreaksRepository.saveAndFlush(sessionBreaks);

        int databaseSizeBeforeUpdate = sessionBreaksRepository.findAll().size();

        // Update the sessionBreaks
        SessionBreaks updatedSessionBreaks = sessionBreaksRepository.findById(sessionBreaks.getId()).get();
        // Disconnect from session so that the updates on updatedSessionBreaks are not directly saved in db
        em.detach(updatedSessionBreaks);
        updatedSessionBreaks
            .starttime(UPDATED_STARTTIME)
            .endtime(UPDATED_ENDTIME)
            .proctoringinstanceid(UPDATED_PROCTORINGINSTANCEID);

        restSessionBreaksMockMvc.perform(put("/api/session-breaks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSessionBreaks)))
            .andExpect(status().isOk());

        // Validate the SessionBreaks in the database
        List<SessionBreaks> sessionBreaksList = sessionBreaksRepository.findAll();
        assertThat(sessionBreaksList).hasSize(databaseSizeBeforeUpdate);
        SessionBreaks testSessionBreaks = sessionBreaksList.get(sessionBreaksList.size() - 1);
        assertThat(testSessionBreaks.getStarttime()).isEqualTo(UPDATED_STARTTIME);
        assertThat(testSessionBreaks.getEndtime()).isEqualTo(UPDATED_ENDTIME);
        assertThat(testSessionBreaks.getProctoringinstanceid()).isEqualTo(UPDATED_PROCTORINGINSTANCEID);

        // Validate the SessionBreaks in Elasticsearch
        verify(mockSessionBreaksSearchRepository, times(1)).save(testSessionBreaks);
    }

    @Test
    @Transactional
    public void updateNonExistingSessionBreaks() throws Exception {
        int databaseSizeBeforeUpdate = sessionBreaksRepository.findAll().size();

        // Create the SessionBreaks

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionBreaksMockMvc.perform(put("/api/session-breaks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionBreaks)))
            .andExpect(status().isBadRequest());

        // Validate the SessionBreaks in the database
        List<SessionBreaks> sessionBreaksList = sessionBreaksRepository.findAll();
        assertThat(sessionBreaksList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SessionBreaks in Elasticsearch
        verify(mockSessionBreaksSearchRepository, times(0)).save(sessionBreaks);
    }

    @Test
    @Transactional
    public void deleteSessionBreaks() throws Exception {
        // Initialize the database
        sessionBreaksRepository.saveAndFlush(sessionBreaks);

        int databaseSizeBeforeDelete = sessionBreaksRepository.findAll().size();

        // Delete the sessionBreaks
        restSessionBreaksMockMvc.perform(delete("/api/session-breaks/{id}", sessionBreaks.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SessionBreaks> sessionBreaksList = sessionBreaksRepository.findAll();
        assertThat(sessionBreaksList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SessionBreaks in Elasticsearch
        verify(mockSessionBreaksSearchRepository, times(1)).deleteById(sessionBreaks.getId());
    }

    @Test
    @Transactional
    public void searchSessionBreaks() throws Exception {
        // Initialize the database
        sessionBreaksRepository.saveAndFlush(sessionBreaks);
        when(mockSessionBreaksSearchRepository.search(queryStringQuery("id:" + sessionBreaks.getId())))
            .thenReturn(Collections.singletonList(sessionBreaks));
        // Search the sessionBreaks
        restSessionBreaksMockMvc.perform(get("/api/_search/session-breaks?query=id:" + sessionBreaks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sessionBreaks.getId().intValue())))
            .andExpect(jsonPath("$.[*].starttime").value(hasItem(DEFAULT_STARTTIME.toString())))
            .andExpect(jsonPath("$.[*].endtime").value(hasItem(DEFAULT_ENDTIME.toString())))
            .andExpect(jsonPath("$.[*].proctoringinstanceid").value(hasItem(DEFAULT_PROCTORINGINSTANCEID)));
    }
}
