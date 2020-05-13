package com.redhat.jinni.web.rest;

import com.redhat.jinni.domain.Schedule;
import com.redhat.jinni.repository.ScheduleRepository;
import com.redhat.jinni.repository.search.ScheduleSearchRepository;
import com.redhat.jinni.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.redhat.jinni.domain.Schedule}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ScheduleResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleResource.class);

    private static final String ENTITY_NAME = "schedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScheduleRepository scheduleRepository;

    private final ScheduleSearchRepository scheduleSearchRepository;

    public ScheduleResource(ScheduleRepository scheduleRepository, ScheduleSearchRepository scheduleSearchRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleSearchRepository = scheduleSearchRepository;
    }

    /**
     * {@code POST  /schedules} : Create a new schedule.
     *
     * @param schedule the schedule to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schedule, or with status {@code 400 (Bad Request)} if the schedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/schedules")
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) throws URISyntaxException {
        log.debug("REST request to save Schedule : {}", schedule);
        if (schedule.getId() != null) {
            throw new BadRequestAlertException("A new schedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Schedule result = scheduleRepository.save(schedule);
        scheduleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /schedules} : Updates an existing schedule.
     *
     * @param schedule the schedule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schedule,
     * or with status {@code 400 (Bad Request)} if the schedule is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schedule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/schedules")
    public ResponseEntity<Schedule> updateSchedule(@RequestBody Schedule schedule) throws URISyntaxException {
        log.debug("REST request to update Schedule : {}", schedule);
        if (schedule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Schedule result = scheduleRepository.save(schedule);
        scheduleSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schedule.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /schedules} : get all the schedules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schedules in body.
     */
    @GetMapping("/schedules")
    public List<Schedule> getAllSchedules() {
        log.debug("REST request to get all Schedules");
        return scheduleRepository.findAll();
    }

    /**
     * {@code GET  /schedules/:id} : get the "id" schedule.
     *
     * @param id the id of the schedule to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schedule, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/schedules/{id}")
    public ResponseEntity<Schedule> getSchedule(@PathVariable Long id) {
        log.debug("REST request to get Schedule : {}", id);
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(schedule);
    }

    /**
     * {@code DELETE  /schedules/:id} : delete the "id" schedule.
     *
     * @param id the id of the schedule to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        log.debug("REST request to delete Schedule : {}", id);
        scheduleRepository.deleteById(id);
        scheduleSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/schedules?query=:query} : search for the schedule corresponding
     * to the query.
     *
     * @param query the query of the schedule search.
     * @return the result of the search.
     */
    @GetMapping("/_search/schedules")
    public List<Schedule> searchSchedules(@RequestParam String query) {
        log.debug("REST request to search Schedules for query {}", query);
        return StreamSupport
            .stream(scheduleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
