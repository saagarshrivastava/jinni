package com.redhat.jinni.repository.search;

import com.redhat.jinni.domain.SupportPerson;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SupportPerson} entity.
 */
public interface SupportPersonSearchRepository extends ElasticsearchRepository<SupportPerson, Long> {
}
