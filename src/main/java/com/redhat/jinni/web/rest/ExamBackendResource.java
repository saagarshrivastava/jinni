package com.redhat.jinni.web.rest;

import com.redhat.jinni.domain.ExamBackend;
import com.redhat.jinni.repository.ExamBackendRepository;
import com.redhat.jinni.repository.search.ExamBackendSearchRepository;
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
 * REST controller for managing {@link com.redhat.jinni.domain.ExamBackend}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ExamBackendResource {

    private final Logger log = LoggerFactory.getLogger(ExamBackendResource.class);

    private static final String ENTITY_NAME = "examBackend";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamBackendRepository examBackendRepository;

    private final ExamBackendSearchRepository examBackendSearchRepository;

    public ExamBackendResource(ExamBackendRepository examBackendRepository, ExamBackendSearchRepository examBackendSearchRepository) {
        this.examBackendRepository = examBackendRepository;
        this.examBackendSearchRepository = examBackendSearchRepository;
    }

    /**
     * {@code POST  /exam-backends} : Create a new examBackend.
     *
     * @param examBackend the examBackend to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examBackend, or with status {@code 400 (Bad Request)} if the examBackend has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exam-backends")
    public ResponseEntity<ExamBackend> createExamBackend(@RequestBody ExamBackend examBackend) throws URISyntaxException {
        log.debug("REST request to save ExamBackend : {}", examBackend);
        if (examBackend.getId() != null) {
            throw new BadRequestAlertException("A new examBackend cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamBackend result = examBackendRepository.save(examBackend);
        examBackendSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/exam-backends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exam-backends} : Updates an existing examBackend.
     *
     * @param examBackend the examBackend to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examBackend,
     * or with status {@code 400 (Bad Request)} if the examBackend is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examBackend couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exam-backends")
    public ResponseEntity<ExamBackend> updateExamBackend(@RequestBody ExamBackend examBackend) throws URISyntaxException {
        log.debug("REST request to update ExamBackend : {}", examBackend);
        if (examBackend.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExamBackend result = examBackendRepository.save(examBackend);
        examBackendSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, examBackend.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /exam-backends} : get all the examBackends.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examBackends in body.
     */
    @GetMapping("/exam-backends")
    public List<ExamBackend> getAllExamBackends() {
        log.debug("REST request to get all ExamBackends");
        return examBackendRepository.findAll();
    }

    /**
     * {@code GET  /exam-backends/:id} : get the "id" examBackend.
     *
     * @param id the id of the examBackend to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examBackend, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exam-backends/{id}")
    public ResponseEntity<ExamBackend> getExamBackend(@PathVariable Long id) {
        log.debug("REST request to get ExamBackend : {}", id);
        Optional<ExamBackend> examBackend = examBackendRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(examBackend);
    }

    /**
     * {@code DELETE  /exam-backends/:id} : delete the "id" examBackend.
     *
     * @param id the id of the examBackend to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exam-backends/{id}")
    public ResponseEntity<Void> deleteExamBackend(@PathVariable Long id) {
        log.debug("REST request to delete ExamBackend : {}", id);
        examBackendRepository.deleteById(id);
        examBackendSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/exam-backends?query=:query} : search for the examBackend corresponding
     * to the query.
     *
     * @param query the query of the examBackend search.
     * @return the result of the search.
     */
    @GetMapping("/_search/exam-backends")
    public List<ExamBackend> searchExamBackends(@RequestParam String query) {
        log.debug("REST request to search ExamBackends for query {}", query);
        return StreamSupport
            .stream(examBackendSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
