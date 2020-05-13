package com.redhat.jinni.web.rest;

import com.redhat.jinni.domain.SessionBreaks;
import com.redhat.jinni.repository.SessionBreaksRepository;
import com.redhat.jinni.repository.search.SessionBreaksSearchRepository;
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
 * REST controller for managing {@link com.redhat.jinni.domain.SessionBreaks}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SessionBreaksResource {

    private final Logger log = LoggerFactory.getLogger(SessionBreaksResource.class);

    private static final String ENTITY_NAME = "sessionBreaks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SessionBreaksRepository sessionBreaksRepository;

    private final SessionBreaksSearchRepository sessionBreaksSearchRepository;

    public SessionBreaksResource(SessionBreaksRepository sessionBreaksRepository, SessionBreaksSearchRepository sessionBreaksSearchRepository) {
        this.sessionBreaksRepository = sessionBreaksRepository;
        this.sessionBreaksSearchRepository = sessionBreaksSearchRepository;
    }

    /**
     * {@code POST  /session-breaks} : Create a new sessionBreaks.
     *
     * @param sessionBreaks the sessionBreaks to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sessionBreaks, or with status {@code 400 (Bad Request)} if the sessionBreaks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/session-breaks")
    public ResponseEntity<SessionBreaks> createSessionBreaks(@RequestBody SessionBreaks sessionBreaks) throws URISyntaxException {
        log.debug("REST request to save SessionBreaks : {}", sessionBreaks);
        if (sessionBreaks.getId() != null) {
            throw new BadRequestAlertException("A new sessionBreaks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SessionBreaks result = sessionBreaksRepository.save(sessionBreaks);
        sessionBreaksSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/session-breaks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /session-breaks} : Updates an existing sessionBreaks.
     *
     * @param sessionBreaks the sessionBreaks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sessionBreaks,
     * or with status {@code 400 (Bad Request)} if the sessionBreaks is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sessionBreaks couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/session-breaks")
    public ResponseEntity<SessionBreaks> updateSessionBreaks(@RequestBody SessionBreaks sessionBreaks) throws URISyntaxException {
        log.debug("REST request to update SessionBreaks : {}", sessionBreaks);
        if (sessionBreaks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SessionBreaks result = sessionBreaksRepository.save(sessionBreaks);
        sessionBreaksSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sessionBreaks.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /session-breaks} : get all the sessionBreaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sessionBreaks in body.
     */
    @GetMapping("/session-breaks")
    public List<SessionBreaks> getAllSessionBreaks() {
        log.debug("REST request to get all SessionBreaks");
        return sessionBreaksRepository.findAll();
    }

    /**
     * {@code GET  /session-breaks/:id} : get the "id" sessionBreaks.
     *
     * @param id the id of the sessionBreaks to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sessionBreaks, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/session-breaks/{id}")
    public ResponseEntity<SessionBreaks> getSessionBreaks(@PathVariable Long id) {
        log.debug("REST request to get SessionBreaks : {}", id);
        Optional<SessionBreaks> sessionBreaks = sessionBreaksRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sessionBreaks);
    }

    /**
     * {@code DELETE  /session-breaks/:id} : delete the "id" sessionBreaks.
     *
     * @param id the id of the sessionBreaks to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/session-breaks/{id}")
    public ResponseEntity<Void> deleteSessionBreaks(@PathVariable Long id) {
        log.debug("REST request to delete SessionBreaks : {}", id);
        sessionBreaksRepository.deleteById(id);
        sessionBreaksSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/session-breaks?query=:query} : search for the sessionBreaks corresponding
     * to the query.
     *
     * @param query the query of the sessionBreaks search.
     * @return the result of the search.
     */
    @GetMapping("/_search/session-breaks")
    public List<SessionBreaks> searchSessionBreaks(@RequestParam String query) {
        log.debug("REST request to search SessionBreaks for query {}", query);
        return StreamSupport
            .stream(sessionBreaksSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
