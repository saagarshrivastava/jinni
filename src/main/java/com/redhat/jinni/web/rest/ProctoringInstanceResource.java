package com.redhat.jinni.web.rest;

import com.redhat.jinni.domain.ProctoringInstance;
import com.redhat.jinni.repository.ProctoringInstanceRepository;
import com.redhat.jinni.repository.search.ProctoringInstanceSearchRepository;
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
 * REST controller for managing {@link com.redhat.jinni.domain.ProctoringInstance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProctoringInstanceResource {

    private final Logger log = LoggerFactory.getLogger(ProctoringInstanceResource.class);

    private static final String ENTITY_NAME = "proctoringInstance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProctoringInstanceRepository proctoringInstanceRepository;

    private final ProctoringInstanceSearchRepository proctoringInstanceSearchRepository;

    public ProctoringInstanceResource(ProctoringInstanceRepository proctoringInstanceRepository, ProctoringInstanceSearchRepository proctoringInstanceSearchRepository) {
        this.proctoringInstanceRepository = proctoringInstanceRepository;
        this.proctoringInstanceSearchRepository = proctoringInstanceSearchRepository;
    }

    /**
     * {@code POST  /proctoring-instances} : Create a new proctoringInstance.
     *
     * @param proctoringInstance the proctoringInstance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proctoringInstance, or with status {@code 400 (Bad Request)} if the proctoringInstance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proctoring-instances")
    public ResponseEntity<ProctoringInstance> createProctoringInstance(@RequestBody ProctoringInstance proctoringInstance) throws URISyntaxException {
        log.debug("REST request to save ProctoringInstance : {}", proctoringInstance);
        if (proctoringInstance.getId() != null) {
            throw new BadRequestAlertException("A new proctoringInstance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProctoringInstance result = proctoringInstanceRepository.save(proctoringInstance);
        proctoringInstanceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/proctoring-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /proctoring-instances} : Updates an existing proctoringInstance.
     *
     * @param proctoringInstance the proctoringInstance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proctoringInstance,
     * or with status {@code 400 (Bad Request)} if the proctoringInstance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proctoringInstance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proctoring-instances")
    public ResponseEntity<ProctoringInstance> updateProctoringInstance(@RequestBody ProctoringInstance proctoringInstance) throws URISyntaxException {
        log.debug("REST request to update ProctoringInstance : {}", proctoringInstance);
        if (proctoringInstance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProctoringInstance result = proctoringInstanceRepository.save(proctoringInstance);
        proctoringInstanceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proctoringInstance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /proctoring-instances} : get all the proctoringInstances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proctoringInstances in body.
     */
    @GetMapping("/proctoring-instances")
    public List<ProctoringInstance> getAllProctoringInstances() {
        log.debug("REST request to get all ProctoringInstances");
        return proctoringInstanceRepository.findAll();
    }

    /**
     * {@code GET  /proctoring-instances/:id} : get the "id" proctoringInstance.
     *
     * @param id the id of the proctoringInstance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proctoringInstance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proctoring-instances/{id}")
    public ResponseEntity<ProctoringInstance> getProctoringInstance(@PathVariable Long id) {
        log.debug("REST request to get ProctoringInstance : {}", id);
        Optional<ProctoringInstance> proctoringInstance = proctoringInstanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(proctoringInstance);
    }

    /**
     * {@code DELETE  /proctoring-instances/:id} : delete the "id" proctoringInstance.
     *
     * @param id the id of the proctoringInstance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proctoring-instances/{id}")
    public ResponseEntity<Void> deleteProctoringInstance(@PathVariable Long id) {
        log.debug("REST request to delete ProctoringInstance : {}", id);
        proctoringInstanceRepository.deleteById(id);
        proctoringInstanceSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/proctoring-instances?query=:query} : search for the proctoringInstance corresponding
     * to the query.
     *
     * @param query the query of the proctoringInstance search.
     * @return the result of the search.
     */
    @GetMapping("/_search/proctoring-instances")
    public List<ProctoringInstance> searchProctoringInstances(@RequestParam String query) {
        log.debug("REST request to search ProctoringInstances for query {}", query);
        return StreamSupport
            .stream(proctoringInstanceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
