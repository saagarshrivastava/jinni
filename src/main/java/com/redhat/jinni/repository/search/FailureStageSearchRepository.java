package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.FailureStage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FailureStage} entity.
 */
public interface FailureStageSearchRepository extends ElasticsearchRepository<FailureStage, Long> {
}
