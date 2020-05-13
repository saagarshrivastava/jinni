package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.ExamType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ExamType} entity.
 */
public interface ExamTypeSearchRepository extends ElasticsearchRepository<ExamType, Long> {
}
