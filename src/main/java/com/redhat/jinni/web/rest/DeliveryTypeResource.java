package com.redhat.jinni.web.rest;

import com.redhat.jinni.domain.DeliveryType;
import com.redhat.jinni.repository.DeliveryTypeRepository;
import com.redhat.jinni.repository.search.DeliveryTypeSearchRepository;
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
 * REST controller for managing {@link com.redhat.jinni.domain.DeliveryType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DeliveryTypeResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryTypeResource.class);

    private static final String ENTITY_NAME = "deliveryType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeliveryTypeRepository deliveryTypeRepository;

    private final DeliveryTypeSearchRepository deliveryTypeSearchRepository;

    public DeliveryTypeResource(DeliveryTypeRepository deliveryTypeRepository, DeliveryTypeSearchRepository deliveryTypeSearchRepository) {
        this.deliveryTypeRepository = deliveryTypeRepository;
        this.deliveryTypeSearchRepository = deliveryTypeSearchRepository;
    }

    /**
     * {@code POST  /delivery-types} : Create a new deliveryType.
     *
     * @param deliveryType the deliveryType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deliveryType, or with status {@code 400 (Bad Request)} if the deliveryType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delivery-types")
    public ResponseEntity<DeliveryType> createDeliveryType(@RequestBody DeliveryType deliveryType) throws URISyntaxException {
        log.debug("REST request to save DeliveryType : {}", deliveryType);
        if (deliveryType.getId() != null) {
            throw new BadRequestAlertException("A new deliveryType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeliveryType result = deliveryTypeRepository.save(deliveryType);
        deliveryTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/delivery-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delivery-types} : Updates an existing deliveryType.
     *
     * @param deliveryType the deliveryType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deliveryType,
     * or with status {@code 400 (Bad Request)} if the deliveryType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deliveryType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delivery-types")
    public ResponseEntity<DeliveryType> updateDeliveryType(@RequestBody DeliveryType deliveryType) throws URISyntaxException {
        log.debug("REST request to update DeliveryType : {}", deliveryType);
        if (deliveryType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeliveryType result = deliveryTypeRepository.save(deliveryType);
        deliveryTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deliveryType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /delivery-types} : get all the deliveryTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deliveryTypes in body.
     */
    @GetMapping("/delivery-types")
    public List<DeliveryType> getAllDeliveryTypes() {
        log.debug("REST request to get all DeliveryTypes");
        return deliveryTypeRepository.findAll();
    }

    /**
     * {@code GET  /delivery-types/:id} : get the "id" deliveryType.
     *
     * @param id the id of the deliveryType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deliveryType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delivery-types/{id}")
    public ResponseEntity<DeliveryType> getDeliveryType(@PathVariable Long id) {
        log.debug("REST request to get DeliveryType : {}", id);
        Optional<DeliveryType> deliveryType = deliveryTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deliveryType);
    }

    /**
     * {@code DELETE  /delivery-types/:id} : delete the "id" deliveryType.
     *
     * @param id the id of the deliveryType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delivery-types/{id}")
    public ResponseEntity<Void> deleteDeliveryType(@PathVariable Long id) {
        log.debug("REST request to delete DeliveryType : {}", id);
        deliveryTypeRepository.deleteById(id);
        deliveryTypeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/delivery-types?query=:query} : search for the deliveryType corresponding
     * to the query.
     *
     * @param query the query of the deliveryType search.
     * @return the result of the search.
     */
    @GetMapping("/_search/delivery-types")
    public List<DeliveryType> searchDeliveryTypes(@RequestParam String query) {
        log.debug("REST request to search DeliveryTypes for query {}", query);
        return StreamSupport
            .stream(deliveryTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
