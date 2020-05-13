package com.redhat.jinni.web.rest;

import com.redhat.jinni.domain.SubcategoryInstance;
import com.redhat.jinni.repository.SubcategoryInstanceRepository;
import com.redhat.jinni.repository.search.SubcategoryInstanceSearchRepository;
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
 * REST controller for managing {@link com.redhat.jinni.domain.SubcategoryInstance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SubcategoryInstanceResource {

    private final Logger log = LoggerFactory.getLogger(SubcategoryInstanceResource.class);

    private static final String ENTITY_NAME = "subcategoryInstance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubcategoryInstanceRepository subcategoryInstanceRepository;

    private final SubcategoryInstanceSearchRepository subcategoryInstanceSearchRepository;

    public SubcategoryInstanceResource(SubcategoryInstanceRepository subcategoryInstanceRepository, SubcategoryInstanceSearchRepository subcategoryInstanceSearchRepository) {
        this.subcategoryInstanceRepository = subcategoryInstanceRepository;
        this.subcategoryInstanceSearchRepository = subcategoryInstanceSearchRepository;
    }

    /**
     * {@code POST  /subcategory-instances} : Create a new subcategoryInstance.
     *
     * @param subcategoryInstance the subcategoryInstance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subcategoryInstance, or with status {@code 400 (Bad Request)} if the subcategoryInstance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subcategory-instances")
    public ResponseEntity<SubcategoryInstance> createSubcategoryInstance(@RequestBody SubcategoryInstance subcategoryInstance) throws URISyntaxException {
        log.debug("REST request to save SubcategoryInstance : {}", subcategoryInstance);
        if (subcategoryInstance.getId() != null) {
            throw new BadRequestAlertException("A new subcategoryInstance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubcategoryInstance result = subcategoryInstanceRepository.save(subcategoryInstance);
        subcategoryInstanceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/subcategory-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subcategory-instances} : Updates an existing subcategoryInstance.
     *
     * @param subcategoryInstance the subcategoryInstance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subcategoryInstance,
     * or with status {@code 400 (Bad Request)} if the subcategoryInstance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subcategoryInstance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subcategory-instances")
    public ResponseEntity<SubcategoryInstance> updateSubcategoryInstance(@RequestBody SubcategoryInstance subcategoryInstance) throws URISyntaxException {
        log.debug("REST request to update SubcategoryInstance : {}", subcategoryInstance);
        if (subcategoryInstance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubcategoryInstance result = subcategoryInstanceRepository.save(subcategoryInstance);
        subcategoryInstanceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subcategoryInstance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subcategory-instances} : get all the subcategoryInstances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subcategoryInstances in body.
     */
    @GetMapping("/subcategory-instances")
    public List<SubcategoryInstance> getAllSubcategoryInstances() {
        log.debug("REST request to get all SubcategoryInstances");
        return subcategoryInstanceRepository.findAll();
    }

    /**
     * {@code GET  /subcategory-instances/:id} : get the "id" subcategoryInstance.
     *
     * @param id the id of the subcategoryInstance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subcategoryInstance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subcategory-instances/{id}")
    public ResponseEntity<SubcategoryInstance> getSubcategoryInstance(@PathVariable Long id) {
        log.debug("REST request to get SubcategoryInstance : {}", id);
        Optional<SubcategoryInstance> subcategoryInstance = subcategoryInstanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subcategoryInstance);
    }

    /**
     * {@code DELETE  /subcategory-instances/:id} : delete the "id" subcategoryInstance.
     *
     * @param id the id of the subcategoryInstance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subcategory-instances/{id}")
    public ResponseEntity<Void> deleteSubcategoryInstance(@PathVariable Long id) {
        log.debug("REST request to delete SubcategoryInstance : {}", id);
        subcategoryInstanceRepository.deleteById(id);
        subcategoryInstanceSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/subcategory-instances?query=:query} : search for the subcategoryInstance corresponding
     * to the query.
     *
     * @param query the query of the subcategoryInstance search.
     * @return the result of the search.
     */
    @GetMapping("/_search/subcategory-instances")
    public List<SubcategoryInstance> searchSubcategoryInstances(@RequestParam String query) {
        log.debug("REST request to search SubcategoryInstances for query {}", query);
        return StreamSupport
            .stream(subcategoryInstanceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
