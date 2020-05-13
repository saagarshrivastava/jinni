package com.redhat.jinni.web.rest;

import com.redhat.jinni.domain.MajorIncidentSource;
import com.redhat.jinni.repository.MajorIncidentSourceRepository;
import com.redhat.jinni.repository.search.MajorIncidentSourceSearchRepository;
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
 * REST controller for managing {@link com.redhat.jinni.domain.MajorIncidentSource}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MajorIncidentSourceResource {

    private final Logger log = LoggerFactory.getLogger(MajorIncidentSourceResource.class);

    private static final String ENTITY_NAME = "majorIncidentSource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MajorIncidentSourceRepository majorIncidentSourceRepository;

    private final MajorIncidentSourceSearchRepository majorIncidentSourceSearchRepository;

    public MajorIncidentSourceResource(MajorIncidentSourceRepository majorIncidentSourceRepository, MajorIncidentSourceSearchRepository majorIncidentSourceSearchRepository) {
        this.majorIncidentSourceRepository = majorIncidentSourceRepository;
        this.majorIncidentSourceSearchRepository = majorIncidentSourceSearchRepository;
    }

    /**
     * {@code POST  /major-incident-sources} : Create a new majorIncidentSource.
     *
     * @param majorIncidentSource the majorIncidentSource to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new majorIncidentSource, or with status {@code 400 (Bad Request)} if the majorIncidentSource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/major-incident-sources")
    public ResponseEntity<MajorIncidentSource> createMajorIncidentSource(@RequestBody MajorIncidentSource majorIncidentSource) throws URISyntaxException {
        log.debug("REST request to save MajorIncidentSource : {}", majorIncidentSource);
        if (majorIncidentSource.getId() != null) {
            throw new BadRequestAlertException("A new majorIncidentSource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MajorIncidentSource result = majorIncidentSourceRepository.save(majorIncidentSource);
        majorIncidentSourceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/major-incident-sources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /major-incident-sources} : Updates an existing majorIncidentSource.
     *
     * @param majorIncidentSource the majorIncidentSource to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated majorIncidentSource,
     * or with status {@code 400 (Bad Request)} if the majorIncidentSource is not valid,
     * or with status {@code 500 (Internal Server Error)} if the majorIncidentSource couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/major-incident-sources")
    public ResponseEntity<MajorIncidentSource> updateMajorIncidentSource(@RequestBody MajorIncidentSource majorIncidentSource) throws URISyntaxException {
        log.debug("REST request to update MajorIncidentSource : {}", majorIncidentSource);
        if (majorIncidentSource.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MajorIncidentSource result = majorIncidentSourceRepository.save(majorIncidentSource);
        majorIncidentSourceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, majorIncidentSource.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /major-incident-sources} : get all the majorIncidentSources.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of majorIncidentSources in body.
     */
    @GetMapping("/major-incident-sources")
    public List<MajorIncidentSource> getAllMajorIncidentSources() {
        log.debug("REST request to get all MajorIncidentSources");
        return majorIncidentSourceRepository.findAll();
    }

    /**
     * {@code GET  /major-incident-sources/:id} : get the "id" majorIncidentSource.
     *
     * @param id the id of the majorIncidentSource to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the majorIncidentSource, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/major-incident-sources/{id}")
    public ResponseEntity<MajorIncidentSource> getMajorIncidentSource(@PathVariable Long id) {
        log.debug("REST request to get MajorIncidentSource : {}", id);
        Optional<MajorIncidentSource> majorIncidentSource = majorIncidentSourceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(majorIncidentSource);
    }

    /**
     * {@code DELETE  /major-incident-sources/:id} : delete the "id" majorIncidentSource.
     *
     * @param id the id of the majorIncidentSource to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/major-incident-sources/{id}")
    public ResponseEntity<Void> deleteMajorIncidentSource(@PathVariable Long id) {
        log.debug("REST request to delete MajorIncidentSource : {}", id);
        majorIncidentSourceRepository.deleteById(id);
        majorIncidentSourceSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/major-incident-sources?query=:query} : search for the majorIncidentSource corresponding
     * to the query.
     *
     * @param query the query of the majorIncidentSource search.
     * @return the result of the search.
     */
    @GetMapping("/_search/major-incident-sources")
    public List<MajorIncidentSource> searchMajorIncidentSources(@RequestParam String query) {
        log.debug("REST request to search MajorIncidentSources for query {}", query);
        return StreamSupport
            .stream(majorIncidentSourceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
