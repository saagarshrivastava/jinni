package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.SessionBreaks;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SessionBreaks} entity.
 */
public interface SessionBreaksSearchRepository extends ElasticsearchRepository<SessionBreaks, Long> {
}
