package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.SupportInstance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SupportInstance} entity.
 */
public interface SupportInstanceSearchRepository extends ElasticsearchRepository<SupportInstance, Long> {
}
