package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.ExamBackend;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ExamBackend} entity.
 */
public interface ExamBackendSearchRepository extends ElasticsearchRepository<ExamBackend, Long> {
}
