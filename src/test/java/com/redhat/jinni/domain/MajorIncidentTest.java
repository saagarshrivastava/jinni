package com.redhat.jinni.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.jinni.web.rest.TestUtil;

public class MajorIncidentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MajorIncident.class);
        MajorIncident majorIncident1 = new MajorIncident();
        majorIncident1.setId(1L);
        MajorIncident majorIncident2 = new MajorIncident();
        majorIncident2.setId(majorIncident1.getId());
        assertThat(majorIncident1).isEqualTo(majorIncident2);
        majorIncident2.setId(2L);
        assertThat(majorIncident1).isNotEqualTo(majorIncident2);
        majorIncident1.setId(null);
        assertThat(majorIncident1).isNotEqualTo(majorIncident2);
    }
}
