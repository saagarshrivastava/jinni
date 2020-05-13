package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.MajorIncident;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MajorIncident} entity.
 */
public interface MajorIncidentSearchRepository extends ElasticsearchRepository<MajorIncident, Long> {
}
