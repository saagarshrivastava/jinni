package com.redhat.jinni.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.jinni.web.rest.TestUtil;

public class CloudRegionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CloudRegion.class);
        CloudRegion cloudRegion1 = new CloudRegion();
        cloudRegion1.setId(1L);
        CloudRegion cloudRegion2 = new CloudRegion();
        cloudRegion2.setId(cloudRegion1.getId());
        assertThat(cloudRegion1).isEqualTo(cloudRegion2);
        cloudRegion2.setId(2L);
        assertThat(cloudRegion1).isNotEqualTo(cloudRegion2);
        cloudRegion1.setId(null);
        assertThat(cloudRegion1).isNotEqualTo(cloudRegion2);
    }
}
