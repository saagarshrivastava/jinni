package com.redhat.jinni.web.rest;

import com.redhat.jinni.JinniApp;
import com.redhat.jinni.domain.Schedule;
import com.redhat.jinni.repository.ScheduleRepository;
import com.redhat.jinni.repository.search.ScheduleSearchRepository;

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
 * Integration tests for the {@link ScheduleResource} REST controller.
 */
@SpringBootTest(classes = JinniApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ScheduleResourceIT {

    private static final LocalDate DEFAULT_SCHEDULEDSETUPSTARTTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SCHEDULEDSETUPSTARTTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACTUALSETUPSTARTTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTUALSETUPSTARTTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SCHEDULEDSETUPENDTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SCHEDULEDSETUPENDTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACTUALSETUPENDTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTUALSETUPENDTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SCHEDULEDCANDIDATEARRIVALTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SCHEDULEDCANDIDATEARRIVALTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACTUALCANDIDATEARRIVALTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTUALCANDIDATEARRIVALTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SCHEDULEDPROCTORARRIVALTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SCHEDULEDPROCTORARRIVALTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACTUALPROCTORARRIVALTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTUALPROCTORARRIVALTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SCHEDULEDONBOARDINGSTARTTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SCHEDULEDONBOARDINGSTARTTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACTUALONBOARDINGSTARTTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTUALONBOARDINGSTARTTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SCHEDULEDONBOARDINGENDTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SCHEDULEDONBOARDINGENDTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACTUALONBOARDINGENDTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTUALONBOARDINGENDTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SCHEDULEDEXAMSTARTTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SCHEDULEDEXAMSTARTTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACTUALEXAMSTARTTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTUALEXAMSTARTTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SCHEDULEDEXAMENDTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SCHEDULEDEXAMENDTIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACTUALEXAMENDTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTUALEXAMENDTIME = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ScheduleRepository scheduleRepository;

    /**
     * This repository is mocked in the com.redhat.jinni.repository.search test package.
     *
     * @see com.redhat.jinni.repository.search.ScheduleSearchRepositoryMockConfiguration
     */
    @Autowired
    private ScheduleSearchRepository mockScheduleSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScheduleMockMvc;

    private Schedule schedule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Schedule createEntity(EntityManager em) {
        Schedule schedule = new Schedule()
            .scheduledsetupstarttime(DEFAULT_SCHEDULEDSETUPSTARTTIME)
            .actualsetupstarttime(DEFAULT_ACTUALSETUPSTARTTIME)
            .scheduledsetupendtime(DEFAULT_SCHEDULEDSETUPENDTIME)
            .actualsetupendtime(DEFAULT_ACTUALSETUPENDTIME)
            .scheduledcandidatearrivaltime(DEFAULT_SCHEDULEDCANDIDATEARRIVALTIME)
            .actualcandidatearrivaltime(DEFAULT_ACTUALCANDIDATEARRIVALTIME)
            .scheduledproctorarrivaltime(DEFAULT_SCHEDULEDPROCTORARRIVALTIME)
            .actualproctorarrivaltime(DEFAULT_ACTUALPROCTORARRIVALTIME)
            .scheduledonboardingstarttime(DEFAULT_SCHEDULEDONBOARDINGSTARTTIME)
            .actualonboardingstarttime(DEFAULT_ACTUALONBOARDINGSTARTTIME)
            .scheduledonboardingendtime(DEFAULT_SCHEDULEDONBOARDINGENDTIME)
            .actualonboardingendtime(DEFAULT_ACTUALONBOARDINGENDTIME)
            .scheduledexamstarttime(DEFAULT_SCHEDULEDEXAMSTARTTIME)
            .actualexamstarttime(DEFAULT_ACTUALEXAMSTARTTIME)
            .scheduledexamendtime(DEFAULT_SCHEDULEDEXAMENDTIME)
            .actualexamendtime(DEFAULT_ACTUALEXAMENDTIME);
        return schedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Schedule createUpdatedEntity(EntityManager em) {
        Schedule schedule = new Schedule()
            .scheduledsetupstarttime(UPDATED_SCHEDULEDSETUPSTARTTIME)
            .actualsetupstarttime(UPDATED_ACTUALSETUPSTARTTIME)
            .scheduledsetupendtime(UPDATED_SCHEDULEDSETUPENDTIME)
            .actualsetupendtime(UPDATED_ACTUALSETUPENDTIME)
            .scheduledcandidatearrivaltime(UPDATED_SCHEDULEDCANDIDATEARRIVALTIME)
            .actualcandidatearrivaltime(UPDATED_ACTUALCANDIDATEARRIVALTIME)
            .scheduledproctorarrivaltime(UPDATED_SCHEDULEDPROCTORARRIVALTIME)
            .actualproctorarrivaltime(UPDATED_ACTUALPROCTORARRIVALTIME)
            .scheduledonboardingstarttime(UPDATED_SCHEDULEDONBOARDINGSTARTTIME)
            .actualonboardingstarttime(UPDATED_ACTUALONBOARDINGSTARTTIME)
            .scheduledonboardingendtime(UPDATED_SCHEDULEDONBOARDINGENDTIME)
            .actualonboardingendtime(UPDATED_ACTUALONBOARDINGENDTIME)
            .scheduledexamstarttime(UPDATED_SCHEDULEDEXAMSTARTTIME)
            .actualexamstarttime(UPDATED_ACTUALEXAMSTARTTIME)
            .scheduledexamendtime(UPDATED_SCHEDULEDEXAMENDTIME)
            .actualexamendtime(UPDATED_ACTUALEXAMENDTIME);
        return schedule;
    }

    @BeforeEach
    public void initTest() {
        schedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchedule() throws Exception {
        int databaseSizeBeforeCreate = scheduleRepository.findAll().size();

        // Create the Schedule
        restScheduleMockMvc.perform(post("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedule)))
            .andExpect(status().isCreated());

        // Validate the Schedule in the database
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeCreate + 1);
        Schedule testSchedule = scheduleList.get(scheduleList.size() - 1);
        assertThat(testSchedule.getScheduledsetupstarttime()).isEqualTo(DEFAULT_SCHEDULEDSETUPSTARTTIME);
        assertThat(testSchedule.getActualsetupstarttime()).isEqualTo(DEFAULT_ACTUALSETUPSTARTTIME);
        assertThat(testSchedule.getScheduledsetupendtime()).isEqualTo(DEFAULT_SCHEDULEDSETUPENDTIME);
        assertThat(testSchedule.getActualsetupendtime()).isEqualTo(DEFAULT_ACTUALSETUPENDTIME);
        assertThat(testSchedule.getScheduledcandidatearrivaltime()).isEqualTo(DEFAULT_SCHEDULEDCANDIDATEARRIVALTIME);
        assertThat(testSchedule.getActualcandidatearrivaltime()).isEqualTo(DEFAULT_ACTUALCANDIDATEARRIVALTIME);
        assertThat(testSchedule.getScheduledproctorarrivaltime()).isEqualTo(DEFAULT_SCHEDULEDPROCTORARRIVALTIME);
        assertThat(testSchedule.getActualproctorarrivaltime()).isEqualTo(DEFAULT_ACTUALPROCTORARRIVALTIME);
        assertThat(testSchedule.getScheduledonboardingstarttime()).isEqualTo(DEFAULT_SCHEDULEDONBOARDINGSTARTTIME);
        assertThat(testSchedule.getActualonboardingstarttime()).isEqualTo(DEFAULT_ACTUALONBOARDINGSTARTTIME);
        assertThat(testSchedule.getScheduledonboardingendtime()).isEqualTo(DEFAULT_SCHEDULEDONBOARDINGENDTIME);
        assertThat(testSchedule.getActualonboardingendtime()).isEqualTo(DEFAULT_ACTUALONBOARDINGENDTIME);
        assertThat(testSchedule.getScheduledexamstarttime()).isEqualTo(DEFAULT_SCHEDULEDEXAMSTARTTIME);
        assertThat(testSchedule.getActualexamstarttime()).isEqualTo(DEFAULT_ACTUALEXAMSTARTTIME);
        assertThat(testSchedule.getScheduledexamendtime()).isEqualTo(DEFAULT_SCHEDULEDEXAMENDTIME);
        assertThat(testSchedule.getActualexamendtime()).isEqualTo(DEFAULT_ACTUALEXAMENDTIME);

        // Validate the Schedule in Elasticsearch
        verify(mockScheduleSearchRepository, times(1)).save(testSchedule);
    }

    @Test
    @Transactional
    public void createScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scheduleRepository.findAll().size();

        // Create the Schedule with an existing ID
        schedule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheduleMockMvc.perform(post("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedule)))
            .andExpect(status().isBadRequest());

        // Validate the Schedule in the database
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeCreate);

        // Validate the Schedule in Elasticsearch
        verify(mockScheduleSearchRepository, times(0)).save(schedule);
    }


    @Test
    @Transactional
    public void getAllSchedules() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        // Get all the scheduleList
        restScheduleMockMvc.perform(get("/api/schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].scheduledsetupstarttime").value(hasItem(DEFAULT_SCHEDULEDSETUPSTARTTIME.toString())))
            .andExpect(jsonPath("$.[*].actualsetupstarttime").value(hasItem(DEFAULT_ACTUALSETUPSTARTTIME.toString())))
            .andExpect(jsonPath("$.[*].scheduledsetupendtime").value(hasItem(DEFAULT_SCHEDULEDSETUPENDTIME.toString())))
            .andExpect(jsonPath("$.[*].actualsetupendtime").value(hasItem(DEFAULT_ACTUALSETUPENDTIME.toString())))
            .andExpect(jsonPath("$.[*].scheduledcandidatearrivaltime").value(hasItem(DEFAULT_SCHEDULEDCANDIDATEARRIVALTIME.toString())))
            .andExpect(jsonPath("$.[*].actualcandidatearrivaltime").value(hasItem(DEFAULT_ACTUALCANDIDATEARRIVALTIME.toString())))
            .andExpect(jsonPath("$.[*].scheduledproctorarrivaltime").value(hasItem(DEFAULT_SCHEDULEDPROCTORARRIVALTIME.toString())))
            .andExpect(jsonPath("$.[*].actualproctorarrivaltime").value(hasItem(DEFAULT_ACTUALPROCTORARRIVALTIME.toString())))
            .andExpect(jsonPath("$.[*].scheduledonboardingstarttime").value(hasItem(DEFAULT_SCHEDULEDONBOARDINGSTARTTIME.toString())))
            .andExpect(jsonPath("$.[*].actualonboardingstarttime").value(hasItem(DEFAULT_ACTUALONBOARDINGSTARTTIME.toString())))
            .andExpect(jsonPath("$.[*].scheduledonboardingendtime").value(hasItem(DEFAULT_SCHEDULEDONBOARDINGENDTIME.toString())))
            .andExpect(jsonPath("$.[*].actualonboardingendtime").value(hasItem(DEFAULT_ACTUALONBOARDINGENDTIME.toString())))
            .andExpect(jsonPath("$.[*].scheduledexamstarttime").value(hasItem(DEFAULT_SCHEDULEDEXAMSTARTTIME.toString())))
            .andExpect(jsonPath("$.[*].actualexamstarttime").value(hasItem(DEFAULT_ACTUALEXAMSTARTTIME.toString())))
            .andExpect(jsonPath("$.[*].scheduledexamendtime").value(hasItem(DEFAULT_SCHEDULEDEXAMENDTIME.toString())))
            .andExpect(jsonPath("$.[*].actualexamendtime").value(hasItem(DEFAULT_ACTUALEXAMENDTIME.toString())));
    }
    
    @Test
    @Transactional
    public void getSchedule() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        // Get the schedule
        restScheduleMockMvc.perform(get("/api/schedules/{id}", schedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schedule.getId().intValue()))
            .andExpect(jsonPath("$.scheduledsetupstarttime").value(DEFAULT_SCHEDULEDSETUPSTARTTIME.toString()))
            .andExpect(jsonPath("$.actualsetupstarttime").value(DEFAULT_ACTUALSETUPSTARTTIME.toString()))
            .andExpect(jsonPath("$.scheduledsetupendtime").value(DEFAULT_SCHEDULEDSETUPENDTIME.toString()))
            .andExpect(jsonPath("$.actualsetupendtime").value(DEFAULT_ACTUALSETUPENDTIME.toString()))
            .andExpect(jsonPath("$.scheduledcandidatearrivaltime").value(DEFAULT_SCHEDULEDCANDIDATEARRIVALTIME.toString()))
            .andExpect(jsonPath("$.actualcandidatearrivaltime").value(DEFAULT_ACTUALCANDIDATEARRIVALTIME.toString()))
            .andExpect(jsonPath("$.scheduledproctorarrivaltime").value(DEFAULT_SCHEDULEDPROCTORARRIVALTIME.toString()))
            .andExpect(jsonPath("$.actualproctorarrivaltime").value(DEFAULT_ACTUALPROCTORARRIVALTIME.toString()))
            .andExpect(jsonPath("$.scheduledonboardingstarttime").value(DEFAULT_SCHEDULEDONBOARDINGSTARTTIME.toString()))
            .andExpect(jsonPath("$.actualonboardingstarttime").value(DEFAULT_ACTUALONBOARDINGSTARTTIME.toString()))
            .andExpect(jsonPath("$.scheduledonboardingendtime").value(DEFAULT_SCHEDULEDONBOARDINGENDTIME.toString()))
            .andExpect(jsonPath("$.actualonboardingendtime").value(DEFAULT_ACTUALONBOARDINGENDTIME.toString()))
            .andExpect(jsonPath("$.scheduledexamstarttime").value(DEFAULT_SCHEDULEDEXAMSTARTTIME.toString()))
            .andExpect(jsonPath("$.actualexamstarttime").value(DEFAULT_ACTUALEXAMSTARTTIME.toString()))
            .andExpect(jsonPath("$.scheduledexamendtime").value(DEFAULT_SCHEDULEDEXAMENDTIME.toString()))
            .andExpect(jsonPath("$.actualexamendtime").value(DEFAULT_ACTUALEXAMENDTIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchedule() throws Exception {
        // Get the schedule
        restScheduleMockMvc.perform(get("/api/schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchedule() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        int databaseSizeBeforeUpdate = scheduleRepository.findAll().size();

        // Update the schedule
        Schedule updatedSchedule = scheduleRepository.findById(schedule.getId()).get();
        // Disconnect from session so that the updates on updatedSchedule are not directly saved in db
        em.detach(updatedSchedule);
        updatedSchedule
            .scheduledsetupstarttime(UPDATED_SCHEDULEDSETUPSTARTTIME)
            .actualsetupstarttime(UPDATED_ACTUALSETUPSTARTTIME)
            .scheduledsetupendtime(UPDATED_SCHEDULEDSETUPENDTIME)
            .actualsetupendtime(UPDATED_ACTUALSETUPENDTIME)
            .scheduledcandidatearrivaltime(UPDATED_SCHEDULEDCANDIDATEARRIVALTIME)
            .actualcandidatearrivaltime(UPDATED_ACTUALCANDIDATEARRIVALTIME)
            .scheduledproctorarrivaltime(UPDATED_SCHEDULEDPROCTORARRIVALTIME)
            .actualproctorarrivaltime(UPDATED_ACTUALPROCTORARRIVALTIME)
            .scheduledonboardingstarttime(UPDATED_SCHEDULEDONBOARDINGSTARTTIME)
            .actualonboardingstarttime(UPDATED_ACTUALONBOARDINGSTARTTIME)
            .scheduledonboardingendtime(UPDATED_SCHEDULEDONBOARDINGENDTIME)
            .actualonboardingendtime(UPDATED_ACTUALONBOARDINGENDTIME)
            .scheduledexamstarttime(UPDATED_SCHEDULEDEXAMSTARTTIME)
            .actualexamstarttime(UPDATED_ACTUALEXAMSTARTTIME)
            .scheduledexamendtime(UPDATED_SCHEDULEDEXAMENDTIME)
            .actualexamendtime(UPDATED_ACTUALEXAMENDTIME);

        restScheduleMockMvc.perform(put("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSchedule)))
            .andExpect(status().isOk());

        // Validate the Schedule in the database
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeUpdate);
        Schedule testSchedule = scheduleList.get(scheduleList.size() - 1);
        assertThat(testSchedule.getScheduledsetupstarttime()).isEqualTo(UPDATED_SCHEDULEDSETUPSTARTTIME);
        assertThat(testSchedule.getActualsetupstarttime()).isEqualTo(UPDATED_ACTUALSETUPSTARTTIME);
        assertThat(testSchedule.getScheduledsetupendtime()).isEqualTo(UPDATED_SCHEDULEDSETUPENDTIME);
        assertThat(testSchedule.getActualsetupendtime()).isEqualTo(UPDATED_ACTUALSETUPENDTIME);
        assertThat(testSchedule.getScheduledcandidatearrivaltime()).isEqualTo(UPDATED_SCHEDULEDCANDIDATEARRIVALTIME);
        assertThat(testSchedule.getActualcandidatearrivaltime()).isEqualTo(UPDATED_ACTUALCANDIDATEARRIVALTIME);
        assertThat(testSchedule.getScheduledproctorarrivaltime()).isEqualTo(UPDATED_SCHEDULEDPROCTORARRIVALTIME);
        assertThat(testSchedule.getActualproctorarrivaltime()).isEqualTo(UPDATED_ACTUALPROCTORARRIVALTIME);
        assertThat(testSchedule.getScheduledonboardingstarttime()).isEqualTo(UPDATED_SCHEDULEDONBOARDINGSTARTTIME);
        assertThat(testSchedule.getActualonboardingstarttime()).isEqualTo(UPDATED_ACTUALONBOARDINGSTARTTIME);
        assertThat(testSchedule.getScheduledonboardingendtime()).isEqualTo(UPDATED_SCHEDULEDONBOARDINGENDTIME);
        assertThat(testSchedule.getActualonboardingendtime()).isEqualTo(UPDATED_ACTUALONBOARDINGENDTIME);
        assertThat(testSchedule.getScheduledexamstarttime()).isEqualTo(UPDATED_SCHEDULEDEXAMSTARTTIME);
        assertThat(testSchedule.getActualexamstarttime()).isEqualTo(UPDATED_ACTUALEXAMSTARTTIME);
        assertThat(testSchedule.getScheduledexamendtime()).isEqualTo(UPDATED_SCHEDULEDEXAMENDTIME);
        assertThat(testSchedule.getActualexamendtime()).isEqualTo(UPDATED_ACTUALEXAMENDTIME);

        // Validate the Schedule in Elasticsearch
        verify(mockScheduleSearchRepository, times(1)).save(testSchedule);
    }

    @Test
    @Transactional
    public void updateNonExistingSchedule() throws Exception {
        int databaseSizeBeforeUpdate = scheduleRepository.findAll().size();

        // Create the Schedule

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheduleMockMvc.perform(put("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedule)))
            .andExpect(status().isBadRequest());

        // Validate the Schedule in the database
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Schedule in Elasticsearch
        verify(mockScheduleSearchRepository, times(0)).save(schedule);
    }

    @Test
    @Transactional
    public void deleteSchedule() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        int databaseSizeBeforeDelete = scheduleRepository.findAll().size();

        // Delete the schedule
        restScheduleMockMvc.perform(delete("/api/schedules/{id}", schedule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Schedule in Elasticsearch
        verify(mockScheduleSearchRepository, times(1)).deleteById(schedule.getId());
    }

    @Test
    @Transactional
    public void searchSchedule() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);
        when(mockScheduleSearchRepository.search(queryStringQuery("id:" + schedule.getId())))
            .thenReturn(Collections.singletonList(schedule));
        // Search the schedule
        restScheduleMockMvc.perform(get("/api/_search/schedules?query=id:" + schedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].scheduledsetupstarttime").value(hasItem(DEFAULT_SCHEDULEDSETUPSTARTTIME.toString())))
            .andExpect(jsonPath("$.[*].actualsetupstarttime").value(hasItem(DEFAULT_ACTUALSETUPSTARTTIME.toString())))
            .andExpect(jsonPath("$.[*].scheduledsetupendtime").value(hasItem(DEFAULT_SCHEDULEDSETUPENDTIME.toString())))
            .andExpect(jsonPath("$.[*].actualsetupendtime").value(hasItem(DEFAULT_ACTUALSETUPENDTIME.toString())))
            .andExpect(jsonPath("$.[*].scheduledcandidatearrivaltime").value(hasItem(DEFAULT_SCHEDULEDCANDIDATEARRIVALTIME.toString())))
            .andExpect(jsonPath("$.[*].actualcandidatearrivaltime").value(hasItem(DEFAULT_ACTUALCANDIDATEARRIVALTIME.toString())))
            .andExpect(jsonPath("$.[*].scheduledproctorarrivaltime").value(hasItem(DEFAULT_SCHEDULEDPROCTORARRIVALTIME.toString())))
            .andExpect(jsonPath("$.[*].actualproctorarrivaltime").value(hasItem(DEFAULT_ACTUALPROCTORARRIVALTIME.toString())))
            .andExpect(jsonPath("$.[*].scheduledonboardingstarttime").value(hasItem(DEFAULT_SCHEDULEDONBOARDINGSTARTTIME.toString())))
            .andExpect(jsonPath("$.[*].actualonboardingstarttime").value(hasItem(DEFAULT_ACTUALONBOARDINGSTARTTIME.toString())))
            .andExpect(jsonPath("$.[*].scheduledonboardingendtime").value(hasItem(DEFAULT_SCHEDULEDONBOARDINGENDTIME.toString())))
            .andExpect(jsonPath("$.[*].actualonboardingendtime").value(hasItem(DEFAULT_ACTUALONBOARDINGENDTIME.toString())))
            .andExpect(jsonPath("$.[*].scheduledexamstarttime").value(hasItem(DEFAULT_SCHEDULEDEXAMSTARTTIME.toString())))
            .andExpect(jsonPath("$.[*].actualexamstarttime").value(hasItem(DEFAULT_ACTUALEXAMSTARTTIME.toString())))
            .andExpect(jsonPath("$.[*].scheduledexamendtime").value(hasItem(DEFAULT_SCHEDULEDEXAMENDTIME.toString())))
            .andExpect(jsonPath("$.[*].actualexamendtime").value(hasItem(DEFAULT_ACTUALEXAMENDTIME.toString())));
    }
}
