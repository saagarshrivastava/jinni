package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.Offer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Offer} entity.
 */
public interface OfferSearchRepository extends ElasticsearchRepository<Offer, Long> {
}
