package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.Subcategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Subcategory} entity.
 */
public interface SubcategorySearchRepository extends ElasticsearchRepository<Subcategory, Long> {
}
