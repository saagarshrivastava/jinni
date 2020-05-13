package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.SubcategoryInstance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SubcategoryInstance} entity.
 */
public interface SubcategoryInstanceSearchRepository extends ElasticsearchRepository<SubcategoryInstance, Long> {
}
