package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.Candidate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Candidate} entity.
 */
public interface CandidateSearchRepository extends ElasticsearchRepository<Candidate, Long> {
}
