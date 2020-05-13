package com.redhat.jinni.web.rest;

import com.redhat.jinni.JinniApp;
import com.redhat.jinni.domain.MajorIncident;
import com.redhat.jinni.repository.MajorIncidentRepository;
import com.redhat.jinni.repository.search.MajorIncidentSearchRepository;

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
 * Integration tests for the {@link MajorIncidentResource} REST controller.
 */
@SpringBootTest(classes = JinniApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MajorIncidentResourceIT {

    private static final Integer DEFAULT_MAJORINCIDENTSOURCEID = 1;
    private static final Integer UPDATED_MAJORINCIDENTSOURCEID = 2;

    private static final LocalDate DEFAULT_STARTTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ENDTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENDTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    @Autowired
    private MajorIncidentRepository majorIncidentRepository;

    /**
     * This repository is mocked in the com.redhat.jinni.repository.search test package.
     *
     * @see com.redhat.jinni.repository.search.MajorIncidentSearchRepositoryMockConfiguration
     */
    @Autowired
    private MajorIncidentSearchRepository mockMajorIncidentSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMajorIncidentMockMvc;

    private MajorIncident majorIncident;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MajorIncident createEntity(EntityManager em) {
        MajorIncident majorIncident = new MajorIncident()
            .majorincidentsourceid(DEFAULT_MAJORINCIDENTSOURCEID)
            .starttime(DEFAULT_STARTTIME)
            .endtime(DEFAULT_ENDTIME)
            .date(DEFAULT_DATE)
            .details(DEFAULT_DETAILS);
        return majorIncident;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MajorIncident createUpdatedEntity(EntityManager em) {
        MajorIncident majorIncident = new MajorIncident()
            .majorincidentsourceid(UPDATED_MAJORINCIDENTSOURCEID)
            .starttime(UPDATED_STARTTIME)
            .endtime(UPDATED_ENDTIME)
            .date(UPDATED_DATE)
            .details(UPDATED_DETAILS);
        return majorIncident;
    }

    @BeforeEach
    public void initTest() {
        majorIncident = createEntity(em);
    }

    @Test
    @Transactional
    public void createMajorIncident() throws Exception {
        int databaseSizeBeforeCreate = majorIncidentRepository.findAll().size();

        // Create the MajorIncident
        restMajorIncidentMockMvc.perform(post("/api/major-incidents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(majorIncident)))
            .andExpect(status().isCreated());

        // Validate the MajorIncident in the database
        List<MajorIncident> majorIncidentList = majorIncidentRepository.findAll();
        assertThat(majorIncidentList).hasSize(databaseSizeBeforeCreate + 1);
        MajorIncident testMajorIncident = majorIncidentList.get(majorIncidentList.size() - 1);
        assertThat(testMajorIncident.getMajorincidentsourceid()).isEqualTo(DEFAULT_MAJORINCIDENTSOURCEID);
        assertThat(testMajorIncident.getStarttime()).isEqualTo(DEFAULT_STARTTIME);
        assertThat(testMajorIncident.getEndtime()).isEqualTo(DEFAULT_ENDTIME);
        assertThat(testMajorIncident.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMajorIncident.getDetails()).isEqualTo(DEFAULT_DETAILS);

        // Validate the MajorIncident in Elasticsearch
        verify(mockMajorIncidentSearchRepository, times(1)).save(testMajorIncident);
    }

    @Test
    @Transactional
    public void createMajorIncidentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = majorIncidentRepository.findAll().size();

        // Create the MajorIncident with an existing ID
        majorIncident.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMajorIncidentMockMvc.perform(post("/api/major-incidents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(majorIncident)))
            .andExpect(status().isBadRequest());

        // Validate the MajorIncident in the database
        List<MajorIncident> majorIncidentList = majorIncidentRepository.findAll();
        assertThat(majorIncidentList).hasSize(databaseSizeBeforeCreate);

        // Validate the MajorIncident in Elasticsearch
        verify(mockMajorIncidentSearchRepository, times(0)).save(majorIncident);
    }


    @Test
    @Transactional
    public void getAllMajorIncidents() throws Exception {
        // Initialize the database
        majorIncidentRepository.saveAndFlush(majorIncident);

        // Get all the majorIncidentList
        restMajorIncidentMockMvc.perform(get("/api/major-incidents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(majorIncident.getId().intValue())))
            .andExpect(jsonPath("$.[*].majorincidentsourceid").value(hasItem(DEFAULT_MAJORINCIDENTSOURCEID)))
            .andExpect(jsonPath("$.[*].starttime").value(hasItem(DEFAULT_STARTTIME.toString())))
            .andExpect(jsonPath("$.[*].endtime").value(hasItem(DEFAULT_ENDTIME.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)));
    }
    
    @Test
    @Transactional
    public void getMajorIncident() throws Exception {
        // Initialize the database
        majorIncidentRepository.saveAndFlush(majorIncident);

        // Get the majorIncident
        restMajorIncidentMockMvc.perform(get("/api/major-incidents/{id}", majorIncident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(majorIncident.getId().intValue()))
            .andExpect(jsonPath("$.majorincidentsourceid").value(DEFAULT_MAJORINCIDENTSOURCEID))
            .andExpect(jsonPath("$.starttime").value(DEFAULT_STARTTIME.toString()))
            .andExpect(jsonPath("$.endtime").value(DEFAULT_ENDTIME.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS));
    }

    @Test
    @Transactional
    public void getNonExistingMajorIncident() throws Exception {
        // Get the majorIncident
        restMajorIncidentMockMvc.perform(get("/api/major-incidents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMajorIncident() throws Exception {
        // Initialize the database
        majorIncidentRepository.saveAndFlush(majorIncident);

        int databaseSizeBeforeUpdate = majorIncidentRepository.findAll().size();

        // Update the majorIncident
        MajorIncident updatedMajorIncident = majorIncidentRepository.findById(majorIncident.getId()).get();
        // Disconnect from session so that the updates on updatedMajorIncident are not directly saved in db
        em.detach(updatedMajorIncident);
        updatedMajorIncident
            .majorincidentsourceid(UPDATED_MAJORINCIDENTSOURCEID)
            .starttime(UPDATED_STARTTIME)
            .endtime(UPDATED_ENDTIME)
            .date(UPDATED_DATE)
            .details(UPDATED_DETAILS);

        restMajorIncidentMockMvc.perform(put("/api/major-incidents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMajorIncident)))
            .andExpect(status().isOk());

        // Validate the MajorIncident in the database
        List<MajorIncident> majorIncidentList = majorIncidentRepository.findAll();
        assertThat(majorIncidentList).hasSize(databaseSizeBeforeUpdate);
        MajorIncident testMajorIncident = majorIncidentList.get(majorIncidentList.size() - 1);
        assertThat(testMajorIncident.getMajorincidentsourceid()).isEqualTo(UPDATED_MAJORINCIDENTSOURCEID);
        assertThat(testMajorIncident.getStarttime()).isEqualTo(UPDATED_STARTTIME);
        assertThat(testMajorIncident.getEndtime()).isEqualTo(UPDATED_ENDTIME);
        assertThat(testMajorIncident.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMajorIncident.getDetails()).isEqualTo(UPDATED_DETAILS);

        // Validate the MajorIncident in Elasticsearch
        verify(mockMajorIncidentSearchRepository, times(1)).save(testMajorIncident);
    }

    @Test
    @Transactional
    public void updateNonExistingMajorIncident() throws Exception {
        int databaseSizeBeforeUpdate = majorIncidentRepository.findAll().size();

        // Create the MajorIncident

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMajorIncidentMockMvc.perform(put("/api/major-incidents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(majorIncident)))
            .andExpect(status().isBadRequest());

        // Validate the MajorIncident in the database
        List<MajorIncident> majorIncidentList = majorIncidentRepository.findAll();
        assertThat(majorIncidentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MajorIncident in Elasticsearch
        verify(mockMajorIncidentSearchRepository, times(0)).save(majorIncident);
    }

    @Test
    @Transactional
    public void deleteMajorIncident() throws Exception {
        // Initialize the database
        majorIncidentRepository.saveAndFlush(majorIncident);

        int databaseSizeBeforeDelete = majorIncidentRepository.findAll().size();

        // Delete the majorIncident
        restMajorIncidentMockMvc.perform(delete("/api/major-incidents/{id}", majorIncident.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MajorIncident> majorIncidentList = majorIncidentRepository.findAll();
        assertThat(majorIncidentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MajorIncident in Elasticsearch
        verify(mockMajorIncidentSearchRepository, times(1)).deleteById(majorIncident.getId());
    }

    @Test
    @Transactional
    public void searchMajorIncident() throws Exception {
        // Initialize the database
        majorIncidentRepository.saveAndFlush(majorIncident);
        when(mockMajorIncidentSearchRepository.search(queryStringQuery("id:" + majorIncident.getId())))
            .thenReturn(Collections.singletonList(majorIncident));
        // Search the majorIncident
        restMajorIncidentMockMvc.perform(get("/api/_search/major-incidents?query=id:" + majorIncident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(majorIncident.getId().intValue())))
            .andExpect(jsonPath("$.[*].majorincidentsourceid").value(hasItem(DEFAULT_MAJORINCIDENTSOURCEID)))
            .andExpect(jsonPath("$.[*].starttime").value(hasItem(DEFAULT_STARTTIME.toString())))
            .andExpect(jsonPath("$.[*].endtime").value(hasItem(DEFAULT_ENDTIME.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)));
    }
}
