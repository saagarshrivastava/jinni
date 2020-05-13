package com.redhat.jinni.web.rest;

import com.redhat.jinni.domain.CloudRegion;
import com.redhat.jinni.repository.CloudRegionRepository;
import com.redhat.jinni.repository.search.CloudRegionSearchRepository;
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
 * REST controller for managing {@link com.redhat.jinni.domain.CloudRegion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CloudRegionResource {

    private final Logger log = LoggerFactory.getLogger(CloudRegionResource.class);

    private static final String ENTITY_NAME = "cloudRegion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CloudRegionRepository cloudRegionRepository;

    private final CloudRegionSearchRepository cloudRegionSearchRepository;

    public CloudRegionResource(CloudRegionRepository cloudRegionRepository, CloudRegionSearchRepository cloudRegionSearchRepository) {
        this.cloudRegionRepository = cloudRegionRepository;
        this.cloudRegionSearchRepository = cloudRegionSearchRepository;
    }

    /**
     * {@code POST  /cloud-regions} : Create a new cloudRegion.
     *
     * @param cloudRegion the cloudRegion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cloudRegion, or with status {@code 400 (Bad Request)} if the cloudRegion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cloud-regions")
    public ResponseEntity<CloudRegion> createCloudRegion(@RequestBody CloudRegion cloudRegion) throws URISyntaxException {
        log.debug("REST request to save CloudRegion : {}", cloudRegion);
        if (cloudRegion.getId() != null) {
            throw new BadRequestAlertException("A new cloudRegion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CloudRegion result = cloudRegionRepository.save(cloudRegion);
        cloudRegionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cloud-regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cloud-regions} : Updates an existing cloudRegion.
     *
     * @param cloudRegion the cloudRegion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cloudRegion,
     * or with status {@code 400 (Bad Request)} if the cloudRegion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cloudRegion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cloud-regions")
    public ResponseEntity<CloudRegion> updateCloudRegion(@RequestBody CloudRegion cloudRegion) throws URISyntaxException {
        log.debug("REST request to update CloudRegion : {}", cloudRegion);
        if (cloudRegion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CloudRegion result = cloudRegionRepository.save(cloudRegion);
        cloudRegionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cloudRegion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cloud-regions} : get all the cloudRegions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cloudRegions in body.
     */
    @GetMapping("/cloud-regions")
    public List<CloudRegion> getAllCloudRegions() {
        log.debug("REST request to get all CloudRegions");
        return cloudRegionRepository.findAll();
    }

    /**
     * {@code GET  /cloud-regions/:id} : get the "id" cloudRegion.
     *
     * @param id the id of the cloudRegion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cloudRegion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cloud-regions/{id}")
    public ResponseEntity<CloudRegion> getCloudRegion(@PathVariable Long id) {
        log.debug("REST request to get CloudRegion : {}", id);
        Optional<CloudRegion> cloudRegion = cloudRegionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cloudRegion);
    }

    /**
     * {@code DELETE  /cloud-regions/:id} : delete the "id" cloudRegion.
     *
     * @param id the id of the cloudRegion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cloud-regions/{id}")
    public ResponseEntity<Void> deleteCloudRegion(@PathVariable Long id) {
        log.debug("REST request to delete CloudRegion : {}", id);
        cloudRegionRepository.deleteById(id);
        cloudRegionSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/cloud-regions?query=:query} : search for the cloudRegion corresponding
     * to the query.
     *
     * @param query the query of the cloudRegion search.
     * @return the result of the search.
     */
    @GetMapping("/_search/cloud-regions")
    public List<CloudRegion> searchCloudRegions(@RequestParam String query) {
        log.debug("REST request to search CloudRegions for query {}", query);
        return StreamSupport
            .stream(cloudRegionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
