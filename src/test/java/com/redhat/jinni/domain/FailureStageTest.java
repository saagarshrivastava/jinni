package com.redhat.jinni.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.jinni.web.rest.TestUtil;

public class FailureStageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FailureStage.class);
        FailureStage failureStage1 = new FailureStage();
        failureStage1.setId(1L);
        FailureStage failureStage2 = new FailureStage();
        failureStage2.setId(failureStage1.getId());
        assertThat(failureStage1).isEqualTo(failureStage2);
        failureStage2.setId(2L);
        assertThat(failureStage1).isNotEqualTo(failureStage2);
        failureStage1.setId(null);
        assertThat(failureStage1).isNotEqualTo(failureStage2);
    }
}
