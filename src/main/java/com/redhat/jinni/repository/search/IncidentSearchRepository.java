package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.Incident;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Incident} entity.
 */
public interface IncidentSearchRepository extends ElasticsearchRepository<Incident, Long> {
}
