package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.Proctor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Proctor} entity.
 */
public interface ProctorSearchRepository extends ElasticsearchRepository<Proctor, Long> {
}
