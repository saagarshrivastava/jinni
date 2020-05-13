package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.ProctoringInstance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ProctoringInstance} entity.
 */
public interface ProctoringInstanceSearchRepository extends ElasticsearchRepository<ProctoringInstance, Long> {
}
