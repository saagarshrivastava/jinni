package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.CloudRegion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CloudRegion} entity.
 */
public interface CloudRegionSearchRepository extends ElasticsearchRepository<CloudRegion, Long> {
}
