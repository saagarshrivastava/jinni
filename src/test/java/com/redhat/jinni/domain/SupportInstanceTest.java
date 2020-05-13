package com.redhat.jinni.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.jinni.web.rest.TestUtil;

public class SupportInstanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupportInstance.class);
        SupportInstance supportInstance1 = new SupportInstance();
        supportInstance1.setId(1L);
        SupportInstance supportInstance2 = new SupportInstance();
        supportInstance2.setId(supportInstance1.getId());
        assertThat(supportInstance1).isEqualTo(supportInstance2);
        supportInstance2.setId(2L);
        assertThat(supportInstance1).isNotEqualTo(supportInstance2);
        supportInstance1.setId(null);
        assertThat(supportInstance1).isNotEqualTo(supportInstance2);
    }
}
