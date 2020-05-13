package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.CategoryInstance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CategoryInstance} entity.
 */
public interface CategoryInstanceSearchRepository extends ElasticsearchRepository<CategoryInstance, Long> {
}
