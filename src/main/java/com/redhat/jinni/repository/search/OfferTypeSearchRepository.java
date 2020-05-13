package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.OfferType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link OfferType} entity.
 */
public interface OfferTypeSearchRepository extends ElasticsearchRepository<OfferType, Long> {
}
