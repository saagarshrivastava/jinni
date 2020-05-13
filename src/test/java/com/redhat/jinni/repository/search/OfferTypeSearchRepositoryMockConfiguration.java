package com.redhat.jinni.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link OfferTypeSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class OfferTypeSearchRepositoryMockConfiguration {

    @MockBean
    private OfferTypeSearchRepository mockOfferTypeSearchRepository;

}
