package com.redhat.jinni.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.jinni.web.rest.TestUtil;

public class ExamBackendTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamBackend.class);
        ExamBackend examBackend1 = new ExamBackend();
        examBackend1.setId(1L);
        ExamBackend examBackend2 = new ExamBackend();
        examBackend2.setId(examBackend1.getId());
        assertThat(examBackend1).isEqualTo(examBackend2);
        examBackend2.setId(2L);
        assertThat(examBackend1).isNotEqualTo(examBackend2);
        examBackend1.setId(null);
        assertThat(examBackend1).isNotEqualTo(examBackend2);
    }
}
