package com.redhat.jinni.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link DeliveryTypeSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DeliveryTypeSearchRepositoryMockConfiguration {

    @MockBean
    private DeliveryTypeSearchRepository mockDeliveryTypeSearchRepository;

}
