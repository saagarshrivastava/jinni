package com.redhat.jinni.web.rest;

import com.redhat.jinni.JinniApp;
import com.redhat.jinni.domain.Incident;
import com.redhat.jinni.repository.IncidentRepository;
import com.redhat.jinni.repository.search.IncidentSearchRepository;

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
 * Integration tests for the {@link IncidentResource} REST controller.
 */
@SpringBootTest(classes = JinniApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class IncidentResourceIT {

    private static final Integer DEFAULT_SESSIONID = 1;
    private static final Integer UPDATED_SESSIONID = 2;

    private static final Integer DEFAULT_MAJORINCIDENTID = 1;
    private static final Integer UPDATED_MAJORINCIDENTID = 2;

    private static final Integer DEFAULT_FAILURESTAGEID = 1;
    private static final Integer UPDATED_FAILURESTAGEID = 2;

    private static final String DEFAULT_SUMMARY = "AAAAAAAAAA";
    private static final String UPDATED_SUMMARY = "BBBBBBBBBB";

    private static final String DEFAULT_INVESTIGATIONREPORT = "AAAAAAAAAA";
    private static final String UPDATED_INVESTIGATIONREPORT = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICENOWTICKETID = "AAAAAAAAAA";
    private static final String UPDATED_SERVICENOWTICKETID = "BBBBBBBBBB";

    @Autowired
    private IncidentRepository incidentRepository;

    /**
     * This repository is mocked in the com.redhat.jinni.repository.search test package.
     *
     * @see com.redhat.jinni.repository.search.IncidentSearchRepositoryMockConfiguration
     */
    @Autowired
    private IncidentSearchRepository mockIncidentSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIncidentMockMvc;

    private Incident incident;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incident createEntity(EntityManager em) {
        Incident incident = new Incident()
            .sessionid(DEFAULT_SESSIONID)
            .majorincidentid(DEFAULT_MAJORINCIDENTID)
            .failurestageid(DEFAULT_FAILURESTAGEID)
            .summary(DEFAULT_SUMMARY)
            .investigationreport(DEFAULT_INVESTIGATIONREPORT)
            .servicenowticketid(DEFAULT_SERVICENOWTICKETID);
        return incident;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incident createUpdatedEntity(EntityManager em) {
        Incident incident = new Incident()
            .sessionid(UPDATED_SESSIONID)
            .majorincidentid(UPDATED_MAJORINCIDENTID)
            .failurestageid(UPDATED_FAILURESTAGEID)
            .summary(UPDATED_SUMMARY)
            .investigationreport(UPDATED_INVESTIGATIONREPORT)
            .servicenowticketid(UPDATED_SERVICENOWTICKETID);
        return incident;
    }

    @BeforeEach
    public void initTest() {
        incident = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncident() throws Exception {
        int databaseSizeBeforeCreate = incidentRepository.findAll().size();

        // Create the Incident
        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incident)))
            .andExpect(status().isCreated());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeCreate + 1);
        Incident testIncident = incidentList.get(incidentList.size() - 1);
        assertThat(testIncident.getSessionid()).isEqualTo(DEFAULT_SESSIONID);
        assertThat(testIncident.getMajorincidentid()).isEqualTo(DEFAULT_MAJORINCIDENTID);
        assertThat(testIncident.getFailurestageid()).isEqualTo(DEFAULT_FAILURESTAGEID);
        assertThat(testIncident.getSummary()).isEqualTo(DEFAULT_SUMMARY);
        assertThat(testIncident.getInvestigationreport()).isEqualTo(DEFAULT_INVESTIGATIONREPORT);
        assertThat(testIncident.getServicenowticketid()).isEqualTo(DEFAULT_SERVICENOWTICKETID);

        // Validate the Incident in Elasticsearch
        verify(mockIncidentSearchRepository, times(1)).save(testIncident);
    }

    @Test
    @Transactional
    public void createIncidentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incidentRepository.findAll().size();

        // Create the Incident with an existing ID
        incident.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incident)))
            .andExpect(status().isBadRequest());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeCreate);

        // Validate the Incident in Elasticsearch
        verify(mockIncidentSearchRepository, times(0)).save(incident);
    }


    @Test
    @Transactional
    public void getAllIncidents() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList
        restIncidentMockMvc.perform(get("/api/incidents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incident.getId().intValue())))
            .andExpect(jsonPath("$.[*].sessionid").value(hasItem(DEFAULT_SESSIONID)))
            .andExpect(jsonPath("$.[*].majorincidentid").value(hasItem(DEFAULT_MAJORINCIDENTID)))
            .andExpect(jsonPath("$.[*].failurestageid").value(hasItem(DEFAULT_FAILURESTAGEID)))
            .andExpect(jsonPath("$.[*].summary").value(hasItem(DEFAULT_SUMMARY)))
            .andExpect(jsonPath("$.[*].investigationreport").value(hasItem(DEFAULT_INVESTIGATIONREPORT)))
            .andExpect(jsonPath("$.[*].servicenowticketid").value(hasItem(DEFAULT_SERVICENOWTICKETID)));
    }
    
    @Test
    @Transactional
    public void getIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get the incident
        restIncidentMockMvc.perform(get("/api/incidents/{id}", incident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(incident.getId().intValue()))
            .andExpect(jsonPath("$.sessionid").value(DEFAULT_SESSIONID))
            .andExpect(jsonPath("$.majorincidentid").value(DEFAULT_MAJORINCIDENTID))
            .andExpect(jsonPath("$.failurestageid").value(DEFAULT_FAILURESTAGEID))
            .andExpect(jsonPath("$.summary").value(DEFAULT_SUMMARY))
            .andExpect(jsonPath("$.investigationreport").value(DEFAULT_INVESTIGATIONREPORT))
            .andExpect(jsonPath("$.servicenowticketid").value(DEFAULT_SERVICENOWTICKETID));
    }

    @Test
    @Transactional
    public void getNonExistingIncident() throws Exception {
        // Get the incident
        restIncidentMockMvc.perform(get("/api/incidents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        int databaseSizeBeforeUpdate = incidentRepository.findAll().size();

        // Update the incident
        Incident updatedIncident = incidentRepository.findById(incident.getId()).get();
        // Disconnect from session so that the updates on updatedIncident are not directly saved in db
        em.detach(updatedIncident);
        updatedIncident
            .sessionid(UPDATED_SESSIONID)
            .majorincidentid(UPDATED_MAJORINCIDENTID)
            .failurestageid(UPDATED_FAILURESTAGEID)
            .summary(UPDATED_SUMMARY)
            .investigationreport(UPDATED_INVESTIGATIONREPORT)
            .servicenowticketid(UPDATED_SERVICENOWTICKETID);

        restIncidentMockMvc.perform(put("/api/incidents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIncident)))
            .andExpect(status().isOk());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeUpdate);
        Incident testIncident = incidentList.get(incidentList.size() - 1);
        assertThat(testIncident.getSessionid()).isEqualTo(UPDATED_SESSIONID);
        assertThat(testIncident.getMajorincidentid()).isEqualTo(UPDATED_MAJORINCIDENTID);
        assertThat(testIncident.getFailurestageid()).isEqualTo(UPDATED_FAILURESTAGEID);
        assertThat(testIncident.getSummary()).isEqualTo(UPDATED_SUMMARY);
        assertThat(testIncident.getInvestigationreport()).isEqualTo(UPDATED_INVESTIGATIONREPORT);
        assertThat(testIncident.getServicenowticketid()).isEqualTo(UPDATED_SERVICENOWTICKETID);

        // Validate the Incident in Elasticsearch
        verify(mockIncidentSearchRepository, times(1)).save(testIncident);
    }

    @Test
    @Transactional
    public void updateNonExistingIncident() throws Exception {
        int databaseSizeBeforeUpdate = incidentRepository.findAll().size();

        // Create the Incident

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncidentMockMvc.perform(put("/api/incidents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incident)))
            .andExpect(status().isBadRequest());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Incident in Elasticsearch
        verify(mockIncidentSearchRepository, times(0)).save(incident);
    }

    @Test
    @Transactional
    public void deleteIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        int databaseSizeBeforeDelete = incidentRepository.findAll().size();

        // Delete the incident
        restIncidentMockMvc.perform(delete("/api/incidents/{id}", incident.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Incident in Elasticsearch
        verify(mockIncidentSearchRepository, times(1)).deleteById(incident.getId());
    }

    @Test
    @Transactional
    public void searchIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);
        when(mockIncidentSearchRepository.search(queryStringQuery("id:" + incident.getId())))
            .thenReturn(Collections.singletonList(incident));
        // Search the incident
        restIncidentMockMvc.perform(get("/api/_search/incidents?query=id:" + incident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incident.getId().intValue())))
            .andExpect(jsonPath("$.[*].sessionid").value(hasItem(DEFAULT_SESSIONID)))
            .andExpect(jsonPath("$.[*].majorincidentid").value(hasItem(DEFAULT_MAJORINCIDENTID)))
            .andExpect(jsonPath("$.[*].failurestageid").value(hasItem(DEFAULT_FAILURESTAGEID)))
            .andExpect(jsonPath("$.[*].summary").value(hasItem(DEFAULT_SUMMARY)))
            .andExpect(jsonPath("$.[*].investigationreport").value(hasItem(DEFAULT_INVESTIGATIONREPORT)))
            .andExpect(jsonPath("$.[*].servicenowticketid").value(hasItem(DEFAULT_SERVICENOWTICKETID)));
    }
}
