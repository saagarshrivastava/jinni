package com.redhat.jinni.web.rest;

import com.redhat.jinni.domain.Candidate;
import com.redhat.jinni.repository.CandidateRepository;
import com.redhat.jinni.repository.search.CandidateSearchRepository;
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
 * REST controller for managing {@link com.redhat.jinni.domain.Candidate}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CandidateResource {

    private final Logger log = LoggerFactory.getLogger(CandidateResource.class);

    private static final String ENTITY_NAME = "candidate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CandidateRepository candidateRepository;

    private final CandidateSearchRepository candidateSearchRepository;

    public CandidateResource(CandidateRepository candidateRepository, CandidateSearchRepository candidateSearchRepository) {
        this.candidateRepository = candidateRepository;
        this.candidateSearchRepository = candidateSearchRepository;
    }

    /**
     * {@code POST  /candidates} : Create a new candidate.
     *
     * @param candidate the candidate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new candidate, or with status {@code 400 (Bad Request)} if the candidate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/candidates")
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) throws URISyntaxException {
        log.debug("REST request to save Candidate : {}", candidate);
        if (candidate.getId() != null) {
            throw new BadRequestAlertException("A new candidate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Candidate result = candidateRepository.save(candidate);
        candidateSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/candidates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /candidates} : Updates an existing candidate.
     *
     * @param candidate the candidate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidate,
     * or with status {@code 400 (Bad Request)} if the candidate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the candidate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/candidates")
    public ResponseEntity<Candidate> updateCandidate(@RequestBody Candidate candidate) throws URISyntaxException {
        log.debug("REST request to update Candidate : {}", candidate);
        if (candidate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Candidate result = candidateRepository.save(candidate);
        candidateSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /candidates} : get all the candidates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidates in body.
     */
    @GetMapping("/candidates")
    public List<Candidate> getAllCandidates() {
        log.debug("REST request to get all Candidates");
        return candidateRepository.findAll();
    }

    /**
     * {@code GET  /candidates/:id} : get the "id" candidate.
     *
     * @param id the id of the candidate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the candidate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/candidates/{id}")
    public ResponseEntity<Candidate> getCandidate(@PathVariable Long id) {
        log.debug("REST request to get Candidate : {}", id);
        Optional<Candidate> candidate = candidateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(candidate);
    }

    /**
     * {@code DELETE  /candidates/:id} : delete the "id" candidate.
     *
     * @param id the id of the candidate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/candidates/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        log.debug("REST request to delete Candidate : {}", id);
        candidateRepository.deleteById(id);
        candidateSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/candidates?query=:query} : search for the candidate corresponding
     * to the query.
     *
     * @param query the query of the candidate search.
     * @return the result of the search.
     */
    @GetMapping("/_search/candidates")
    public List<Candidate> searchCandidates(@RequestParam String query) {
        log.debug("REST request to search Candidates for query {}", query);
        return StreamSupport
            .stream(candidateSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
