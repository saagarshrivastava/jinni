package com.redhat.jinni.web.rest;

import com.redhat.jinni.JinniApp;
import com.redhat.jinni.domain.Session;
import com.redhat.jinni.repository.SessionRepository;
import com.redhat.jinni.repository.search.SessionSearchRepository;

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
 * Integration tests for the {@link SessionResource} REST controller.
 */
@SpringBootTest(classes = JinniApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SessionResourceIT {

    private static final Integer DEFAULT_SCHEDULEID = 1;
    private static final Integer UPDATED_SCHEDULEID = 2;

    private static final Integer DEFAULT_CANDIDATEID = 1;
    private static final Integer UPDATED_CANDIDATEID = 2;

    private static final Integer DEFAULT_LOCATIONID = 1;
    private static final Integer UPDATED_LOCATIONID = 2;

    private static final Integer DEFAULT_EXAMTYPEID = 1;
    private static final Integer UPDATED_EXAMTYPEID = 2;

    private static final Integer DEFAULT_DELIVERYTYPEID = 1;
    private static final Integer UPDATED_DELIVERYTYPEID = 2;

    private static final Integer DEFAULT_DELIVERYSTATUSID = 1;
    private static final Integer UPDATED_DELIVERYSTATUSID = 2;

    private static final Integer DEFAULT_EXAMID = 1;
    private static final Integer UPDATED_EXAMID = 2;

    private static final Integer DEFAULT_EXAMBACKENDID = 1;
    private static final Integer UPDATED_EXAMBACKENDID = 2;

    private static final String DEFAULT_RESERVATIONID = "AAAAAAAAAA";
    private static final String UPDATED_RESERVATIONID = "BBBBBBBBBB";

    @Autowired
    private SessionRepository sessionRepository;

    /**
     * This repository is mocked in the com.redhat.jinni.repository.search test package.
     *
     * @see com.redhat.jinni.repository.search.SessionSearchRepositoryMockConfiguration
     */
    @Autowired
    private SessionSearchRepository mockSessionSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSessionMockMvc;

    private Session session;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Session createEntity(EntityManager em) {
        Session session = new Session()
            .scheduleid(DEFAULT_SCHEDULEID)
            .candidateid(DEFAULT_CANDIDATEID)
            .locationid(DEFAULT_LOCATIONID)
            .examtypeid(DEFAULT_EXAMTYPEID)
            .deliverytypeid(DEFAULT_DELIVERYTYPEID)
            .deliverystatusid(DEFAULT_DELIVERYSTATUSID)
            .examid(DEFAULT_EXAMID)
            .exambackendid(DEFAULT_EXAMBACKENDID)
            .reservationid(DEFAULT_RESERVATIONID);
        return session;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Session createUpdatedEntity(EntityManager em) {
        Session session = new Session()
            .scheduleid(UPDATED_SCHEDULEID)
            .candidateid(UPDATED_CANDIDATEID)
            .locationid(UPDATED_LOCATIONID)
            .examtypeid(UPDATED_EXAMTYPEID)
            .deliverytypeid(UPDATED_DELIVERYTYPEID)
            .deliverystatusid(UPDATED_DELIVERYSTATUSID)
            .examid(UPDATED_EXAMID)
            .exambackendid(UPDATED_EXAMBACKENDID)
            .reservationid(UPDATED_RESERVATIONID);
        return session;
    }

    @BeforeEach
    public void initTest() {
        session = createEntity(em);
    }

    @Test
    @Transactional
    public void createSession() throws Exception {
        int databaseSizeBeforeCreate = sessionRepository.findAll().size();

        // Create the Session
        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(session)))
            .andExpect(status().isCreated());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeCreate + 1);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getScheduleid()).isEqualTo(DEFAULT_SCHEDULEID);
        assertThat(testSession.getCandidateid()).isEqualTo(DEFAULT_CANDIDATEID);
        assertThat(testSession.getLocationid()).isEqualTo(DEFAULT_LOCATIONID);
        assertThat(testSession.getExamtypeid()).isEqualTo(DEFAULT_EXAMTYPEID);
        assertThat(testSession.getDeliverytypeid()).isEqualTo(DEFAULT_DELIVERYTYPEID);
        assertThat(testSession.getDeliverystatusid()).isEqualTo(DEFAULT_DELIVERYSTATUSID);
        assertThat(testSession.getExamid()).isEqualTo(DEFAULT_EXAMID);
        assertThat(testSession.getExambackendid()).isEqualTo(DEFAULT_EXAMBACKENDID);
        assertThat(testSession.getReservationid()).isEqualTo(DEFAULT_RESERVATIONID);

        // Validate the Session in Elasticsearch
        verify(mockSessionSearchRepository, times(1)).save(testSession);
    }

    @Test
    @Transactional
    public void createSessionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sessionRepository.findAll().size();

        // Create the Session with an existing ID
        session.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(session)))
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Session in Elasticsearch
        verify(mockSessionSearchRepository, times(0)).save(session);
    }


    @Test
    @Transactional
    public void getAllSessions() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        // Get all the sessionList
        restSessionMockMvc.perform(get("/api/sessions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(session.getId().intValue())))
            .andExpect(jsonPath("$.[*].scheduleid").value(hasItem(DEFAULT_SCHEDULEID)))
            .andExpect(jsonPath("$.[*].candidateid").value(hasItem(DEFAULT_CANDIDATEID)))
            .andExpect(jsonPath("$.[*].locationid").value(hasItem(DEFAULT_LOCATIONID)))
            .andExpect(jsonPath("$.[*].examtypeid").value(hasItem(DEFAULT_EXAMTYPEID)))
            .andExpect(jsonPath("$.[*].deliverytypeid").value(hasItem(DEFAULT_DELIVERYTYPEID)))
            .andExpect(jsonPath("$.[*].deliverystatusid").value(hasItem(DEFAULT_DELIVERYSTATUSID)))
            .andExpect(jsonPath("$.[*].examid").value(hasItem(DEFAULT_EXAMID)))
            .andExpect(jsonPath("$.[*].exambackendid").value(hasItem(DEFAULT_EXAMBACKENDID)))
            .andExpect(jsonPath("$.[*].reservationid").value(hasItem(DEFAULT_RESERVATIONID)));
    }
    
    @Test
    @Transactional
    public void getSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        // Get the session
        restSessionMockMvc.perform(get("/api/sessions/{id}", session.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(session.getId().intValue()))
            .andExpect(jsonPath("$.scheduleid").value(DEFAULT_SCHEDULEID))
            .andExpect(jsonPath("$.candidateid").value(DEFAULT_CANDIDATEID))
            .andExpect(jsonPath("$.locationid").value(DEFAULT_LOCATIONID))
            .andExpect(jsonPath("$.examtypeid").value(DEFAULT_EXAMTYPEID))
            .andExpect(jsonPath("$.deliverytypeid").value(DEFAULT_DELIVERYTYPEID))
            .andExpect(jsonPath("$.deliverystatusid").value(DEFAULT_DELIVERYSTATUSID))
            .andExpect(jsonPath("$.examid").value(DEFAULT_EXAMID))
            .andExpect(jsonPath("$.exambackendid").value(DEFAULT_EXAMBACKENDID))
            .andExpect(jsonPath("$.reservationid").value(DEFAULT_RESERVATIONID));
    }

    @Test
    @Transactional
    public void getNonExistingSession() throws Exception {
        // Get the session
        restSessionMockMvc.perform(get("/api/sessions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Update the session
        Session updatedSession = sessionRepository.findById(session.getId()).get();
        // Disconnect from session so that the updates on updatedSession are not directly saved in db
        em.detach(updatedSession);
        updatedSession
            .scheduleid(UPDATED_SCHEDULEID)
            .candidateid(UPDATED_CANDIDATEID)
            .locationid(UPDATED_LOCATIONID)
            .examtypeid(UPDATED_EXAMTYPEID)
            .deliverytypeid(UPDATED_DELIVERYTYPEID)
            .deliverystatusid(UPDATED_DELIVERYSTATUSID)
            .examid(UPDATED_EXAMID)
            .exambackendid(UPDATED_EXAMBACKENDID)
            .reservationid(UPDATED_RESERVATIONID);

        restSessionMockMvc.perform(put("/api/sessions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSession)))
            .andExpect(status().isOk());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getScheduleid()).isEqualTo(UPDATED_SCHEDULEID);
        assertThat(testSession.getCandidateid()).isEqualTo(UPDATED_CANDIDATEID);
        assertThat(testSession.getLocationid()).isEqualTo(UPDATED_LOCATIONID);
        assertThat(testSession.getExamtypeid()).isEqualTo(UPDATED_EXAMTYPEID);
        assertThat(testSession.getDeliverytypeid()).isEqualTo(UPDATED_DELIVERYTYPEID);
        assertThat(testSession.getDeliverystatusid()).isEqualTo(UPDATED_DELIVERYSTATUSID);
        assertThat(testSession.getExamid()).isEqualTo(UPDATED_EXAMID);
        assertThat(testSession.getExambackendid()).isEqualTo(UPDATED_EXAMBACKENDID);
        assertThat(testSession.getReservationid()).isEqualTo(UPDATED_RESERVATIONID);

        // Validate the Session in Elasticsearch
        verify(mockSessionSearchRepository, times(1)).save(testSession);
    }

    @Test
    @Transactional
    public void updateNonExistingSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Create the Session

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionMockMvc.perform(put("/api/sessions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(session)))
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Session in Elasticsearch
        verify(mockSessionSearchRepository, times(0)).save(session);
    }

    @Test
    @Transactional
    public void deleteSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeDelete = sessionRepository.findAll().size();

        // Delete the session
        restSessionMockMvc.perform(delete("/api/sessions/{id}", session.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Session in Elasticsearch
        verify(mockSessionSearchRepository, times(1)).deleteById(session.getId());
    }

    @Test
    @Transactional
    public void searchSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);
        when(mockSessionSearchRepository.search(queryStringQuery("id:" + session.getId())))
            .thenReturn(Collections.singletonList(session));
        // Search the session
        restSessionMockMvc.perform(get("/api/_search/sessions?query=id:" + session.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(session.getId().intValue())))
            .andExpect(jsonPath("$.[*].scheduleid").value(hasItem(DEFAULT_SCHEDULEID)))
            .andExpect(jsonPath("$.[*].candidateid").value(hasItem(DEFAULT_CANDIDATEID)))
            .andExpect(jsonPath("$.[*].locationid").value(hasItem(DEFAULT_LOCATIONID)))
            .andExpect(jsonPath("$.[*].examtypeid").value(hasItem(DEFAULT_EXAMTYPEID)))
            .andExpect(jsonPath("$.[*].deliverytypeid").value(hasItem(DEFAULT_DELIVERYTYPEID)))
            .andExpect(jsonPath("$.[*].deliverystatusid").value(hasItem(DEFAULT_DELIVERYSTATUSID)))
            .andExpect(jsonPath("$.[*].examid").value(hasItem(DEFAULT_EXAMID)))
            .andExpect(jsonPath("$.[*].exambackendid").value(hasItem(DEFAULT_EXAMBACKENDID)))
            .andExpect(jsonPath("$.[*].reservationid").value(hasItem(DEFAULT_RESERVATIONID)));
    }
}
