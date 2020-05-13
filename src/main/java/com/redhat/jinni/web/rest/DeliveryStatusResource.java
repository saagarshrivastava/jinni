package com.redhat.jinni.web.rest;

import com.redhat.jinni.domain.DeliveryStatus;
import com.redhat.jinni.repository.DeliveryStatusRepository;
import com.redhat.jinni.repository.search.DeliveryStatusSearchRepository;
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
 * REST controller for managing {@link com.redhat.jinni.domain.DeliveryStatus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DeliveryStatusResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryStatusResource.class);

    private static final String ENTITY_NAME = "deliveryStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeliveryStatusRepository deliveryStatusRepository;

    private final DeliveryStatusSearchRepository deliveryStatusSearchRepository;

    public DeliveryStatusResource(DeliveryStatusRepository deliveryStatusRepository, DeliveryStatusSearchRepository deliveryStatusSearchRepository) {
        this.deliveryStatusRepository = deliveryStatusRepository;
        this.deliveryStatusSearchRepository = deliveryStatusSearchRepository;
    }

    /**
     * {@code POST  /delivery-statuses} : Create a new deliveryStatus.
     *
     * @param deliveryStatus the deliveryStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deliveryStatus, or with status {@code 400 (Bad Request)} if the deliveryStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delivery-statuses")
    public ResponseEntity<DeliveryStatus> createDeliveryStatus(@RequestBody DeliveryStatus deliveryStatus) throws URISyntaxException {
        log.debug("REST request to save DeliveryStatus : {}", deliveryStatus);
        if (deliveryStatus.getId() != null) {
            throw new BadRequestAlertException("A new deliveryStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeliveryStatus result = deliveryStatusRepository.save(deliveryStatus);
        deliveryStatusSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/delivery-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delivery-statuses} : Updates an existing deliveryStatus.
     *
     * @param deliveryStatus the deliveryStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deliveryStatus,
     * or with status {@code 400 (Bad Request)} if the deliveryStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deliveryStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delivery-statuses")
    public ResponseEntity<DeliveryStatus> updateDeliveryStatus(@RequestBody DeliveryStatus deliveryStatus) throws URISyntaxException {
        log.debug("REST request to update DeliveryStatus : {}", deliveryStatus);
        if (deliveryStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeliveryStatus result = deliveryStatusRepository.save(deliveryStatus);
        deliveryStatusSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deliveryStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /delivery-statuses} : get all the deliveryStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deliveryStatuses in body.
     */
    @GetMapping("/delivery-statuses")
    public List<DeliveryStatus> getAllDeliveryStatuses() {
        log.debug("REST request to get all DeliveryStatuses");
        return deliveryStatusRepository.findAll();
    }

    /**
     * {@code GET  /delivery-statuses/:id} : get the "id" deliveryStatus.
     *
     * @param id the id of the deliveryStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deliveryStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delivery-statuses/{id}")
    public ResponseEntity<DeliveryStatus> getDeliveryStatus(@PathVariable Long id) {
        log.debug("REST request to get DeliveryStatus : {}", id);
        Optional<DeliveryStatus> deliveryStatus = deliveryStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deliveryStatus);
    }

    /**
     * {@code DELETE  /delivery-statuses/:id} : delete the "id" deliveryStatus.
     *
     * @param id the id of the deliveryStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delivery-statuses/{id}")
    public ResponseEntity<Void> deleteDeliveryStatus(@PathVariable Long id) {
        log.debug("REST request to delete DeliveryStatus : {}", id);
        deliveryStatusRepository.deleteById(id);
        deliveryStatusSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/delivery-statuses?query=:query} : search for the deliveryStatus corresponding
     * to the query.
     *
     * @param query the query of the deliveryStatus search.
     * @return the result of the search.
     */
    @GetMapping("/_search/delivery-statuses")
    public List<DeliveryStatus> searchDeliveryStatuses(@RequestParam String query) {
        log.debug("REST request to search DeliveryStatuses for query {}", query);
        return StreamSupport
            .stream(deliveryStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
