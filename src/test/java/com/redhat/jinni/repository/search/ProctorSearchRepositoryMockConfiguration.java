package com.redhat.jinni.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ProctorSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ProctorSearchRepositoryMockConfiguration {

    @MockBean
    private ProctorSearchRepository mockProctorSearchRepository;

}
