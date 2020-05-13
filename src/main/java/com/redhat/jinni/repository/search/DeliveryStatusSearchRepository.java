package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.DeliveryStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DeliveryStatus} entity.
 */
public interface DeliveryStatusSearchRepository extends ElasticsearchRepository<DeliveryStatus, Long> {
}
