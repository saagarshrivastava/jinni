package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.MajorIncidentSource;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MajorIncidentSource} entity.
 */
public interface MajorIncidentSourceSearchRepository extends ElasticsearchRepository<MajorIncidentSource, Long> {
}
