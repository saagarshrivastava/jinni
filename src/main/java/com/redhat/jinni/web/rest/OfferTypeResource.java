package com.redhat.jinni.web.rest;

import com.redhat.jinni.domain.OfferType;
import com.redhat.jinni.repository.OfferTypeRepository;
import com.redhat.jinni.repository.search.OfferTypeSearchRepository;
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
 * REST controller for managing {@link com.redhat.jinni.domain.OfferType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OfferTypeResource {

    private final Logger log = LoggerFactory.getLogger(OfferTypeResource.class);

    private static final String ENTITY_NAME = "offerType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfferTypeRepository offerTypeRepository;

    private final OfferTypeSearchRepository offerTypeSearchRepository;

    public OfferTypeResource(OfferTypeRepository offerTypeRepository, OfferTypeSearchRepository offerTypeSearchRepository) {
        this.offerTypeRepository = offerTypeRepository;
        this.offerTypeSearchRepository = offerTypeSearchRepository;
    }

    /**
     * {@code POST  /offer-types} : Create a new offerType.
     *
     * @param offerType the offerType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offerType, or with status {@code 400 (Bad Request)} if the offerType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/offer-types")
    public ResponseEntity<OfferType> createOfferType(@RequestBody OfferType offerType) throws URISyntaxException {
        log.debug("REST request to save OfferType : {}", offerType);
        if (offerType.getId() != null) {
            throw new BadRequestAlertException("A new offerType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfferType result = offerTypeRepository.save(offerType);
        offerTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/offer-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /offer-types} : Updates an existing offerType.
     *
     * @param offerType the offerType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offerType,
     * or with status {@code 400 (Bad Request)} if the offerType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offerType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/offer-types")
    public ResponseEntity<OfferType> updateOfferType(@RequestBody OfferType offerType) throws URISyntaxException {
        log.debug("REST request to update OfferType : {}", offerType);
        if (offerType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OfferType result = offerTypeRepository.save(offerType);
        offerTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offerType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /offer-types} : get all the offerTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offerTypes in body.
     */
    @GetMapping("/offer-types")
    public List<OfferType> getAllOfferTypes() {
        log.debug("REST request to get all OfferTypes");
        return offerTypeRepository.findAll();
    }

    /**
     * {@code GET  /offer-types/:id} : get the "id" offerType.
     *
     * @param id the id of the offerType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offerType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/offer-types/{id}")
    public ResponseEntity<OfferType> getOfferType(@PathVariable Long id) {
        log.debug("REST request to get OfferType : {}", id);
        Optional<OfferType> offerType = offerTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(offerType);
    }

    /**
     * {@code DELETE  /offer-types/:id} : delete the "id" offerType.
     *
     * @param id the id of the offerType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/offer-types/{id}")
    public ResponseEntity<Void> deleteOfferType(@PathVariable Long id) {
        log.debug("REST request to delete OfferType : {}", id);
        offerTypeRepository.deleteById(id);
        offerTypeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/offer-types?query=:query} : search for the offerType corresponding
     * to the query.
     *
     * @param query the query of the offerType search.
     * @return the result of the search.
     */
    @GetMapping("/_search/offer-types")
    public List<OfferType> searchOfferTypes(@RequestParam String query) {
        log.debug("REST request to search OfferTypes for query {}", query);
        return StreamSupport
            .stream(offerTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
