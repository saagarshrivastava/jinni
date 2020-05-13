package com.redhat.jinni.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.jinni.web.rest.TestUtil;

public class CloudInstanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CloudInstance.class);
        CloudInstance cloudInstance1 = new CloudInstance();
        cloudInstance1.setId(1L);
        CloudInstance cloudInstance2 = new CloudInstance();
        cloudInstance2.setId(cloudInstance1.getId());
        assertThat(cloudInstance1).isEqualTo(cloudInstance2);
        cloudInstance2.setId(2L);
        assertThat(cloudInstance1).isNotEqualTo(cloudInstance2);
        cloudInstance1.setId(null);
        assertThat(cloudInstance1).isNotEqualTo(cloudInstance2);
    }
}
