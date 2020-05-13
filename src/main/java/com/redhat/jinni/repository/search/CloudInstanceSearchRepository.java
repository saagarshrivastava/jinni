package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.CloudInstance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CloudInstance} entity.
 */
public interface CloudInstanceSearchRepository extends ElasticsearchRepository<CloudInstance, Long> {
}
