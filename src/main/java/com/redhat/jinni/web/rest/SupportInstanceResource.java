package com.redhat.jinni.web.rest;

import com.redhat.jinni.domain.SupportInstance;
import com.redhat.jinni.repository.SupportInstanceRepository;
import com.redhat.jinni.repository.search.SupportInstanceSearchRepository;
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
 * REST controller for managing {@link com.redhat.jinni.domain.SupportInstance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SupportInstanceResource {

    private final Logger log = LoggerFactory.getLogger(SupportInstanceResource.class);

    private static final String ENTITY_NAME = "supportInstance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupportInstanceRepository supportInstanceRepository;

    private final SupportInstanceSearchRepository supportInstanceSearchRepository;

    public SupportInstanceResource(SupportInstanceRepository supportInstanceRepository, SupportInstanceSearchRepository supportInstanceSearchRepository) {
        this.supportInstanceRepository = supportInstanceRepository;
        this.supportInstanceSearchRepository = supportInstanceSearchRepository;
    }

    /**
     * {@code POST  /support-instances} : Create a new supportInstance.
     *
     * @param supportInstance the supportInstance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supportInstance, or with status {@code 400 (Bad Request)} if the supportInstance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/support-instances")
    public ResponseEntity<SupportInstance> createSupportInstance(@RequestBody SupportInstance supportInstance) throws URISyntaxException {
        log.debug("REST request to save SupportInstance : {}", supportInstance);
        if (supportInstance.getId() != null) {
            throw new BadRequestAlertException("A new supportInstance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupportInstance result = supportInstanceRepository.save(supportInstance);
        supportInstanceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/support-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /support-instances} : Updates an existing supportInstance.
     *
     * @param supportInstance the supportInstance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supportInstance,
     * or with status {@code 400 (Bad Request)} if the supportInstance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supportInstance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/support-instances")
    public ResponseEntity<SupportInstance> updateSupportInstance(@RequestBody SupportInstance supportInstance) throws URISyntaxException {
        log.debug("REST request to update SupportInstance : {}", supportInstance);
        if (supportInstance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupportInstance result = supportInstanceRepository.save(supportInstance);
        supportInstanceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supportInstance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /support-instances} : get all the supportInstances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supportInstances in body.
     */
    @GetMapping("/support-instances")
    public List<SupportInstance> getAllSupportInstances() {
        log.debug("REST request to get all SupportInstances");
        return supportInstanceRepository.findAll();
    }

    /**
     * {@code GET  /support-instances/:id} : get the "id" supportInstance.
     *
     * @param id the id of the supportInstance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supportInstance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/support-instances/{id}")
    public ResponseEntity<SupportInstance> getSupportInstance(@PathVariable Long id) {
        log.debug("REST request to get SupportInstance : {}", id);
        Optional<SupportInstance> supportInstance = supportInstanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(supportInstance);
    }

    /**
     * {@code DELETE  /support-instances/:id} : delete the "id" supportInstance.
     *
     * @param id the id of the supportInstance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/support-instances/{id}")
    public ResponseEntity<Void> deleteSupportInstance(@PathVariable Long id) {
        log.debug("REST request to delete SupportInstance : {}", id);
        supportInstanceRepository.deleteById(id);
        supportInstanceSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/support-instances?query=:query} : search for the supportInstance corresponding
     * to the query.
     *
     * @param query the query of the supportInstance search.
     * @return the result of the search.
     */
    @GetMapping("/_search/support-instances")
    public List<SupportInstance> searchSupportInstances(@RequestParam String query) {
        log.debug("REST request to search SupportInstances for query {}", query);
        return StreamSupport
            .stream(supportInstanceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
