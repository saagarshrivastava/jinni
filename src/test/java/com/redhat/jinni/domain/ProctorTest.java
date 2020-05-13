package com.redhat.jinni.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.jinni.web.rest.TestUtil;

public class ProctorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Proctor.class);
        Proctor proctor1 = new Proctor();
        proctor1.setId(1L);
        Proctor proctor2 = new Proctor();
        proctor2.setId(proctor1.getId());
        assertThat(proctor1).isEqualTo(proctor2);
        proctor2.setId(2L);
        assertThat(proctor1).isNotEqualTo(proctor2);
        proctor1.setId(null);
        assertThat(proctor1).isNotEqualTo(proctor2);
    }
}
