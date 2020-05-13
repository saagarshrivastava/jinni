package com.redhat.jinni.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.jinni.web.rest.TestUtil;

public class ProctoringInstanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProctoringInstance.class);
        ProctoringInstance proctoringInstance1 = new ProctoringInstance();
        proctoringInstance1.setId(1L);
        ProctoringInstance proctoringInstance2 = new ProctoringInstance();
        proctoringInstance2.setId(proctoringInstance1.getId());
        assertThat(proctoringInstance1).isEqualTo(proctoringInstance2);
        proctoringInstance2.setId(2L);
        assertThat(proctoringInstance1).isNotEqualTo(proctoringInstance2);
        proctoringInstance1.setId(null);
        assertThat(proctoringInstance1).isNotEqualTo(proctoringInstance2);
    }
}
