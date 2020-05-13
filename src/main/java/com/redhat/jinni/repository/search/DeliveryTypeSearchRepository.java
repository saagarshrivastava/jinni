package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.DeliveryType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DeliveryType} entity.
 */
public interface DeliveryTypeSearchRepository extends ElasticsearchRepository<DeliveryType, Long> {
}
