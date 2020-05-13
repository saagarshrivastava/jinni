package com.redhat.jinni.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.jinni.web.rest.TestUtil;

public class MajorIncidentSourceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MajorIncidentSource.class);
        MajorIncidentSource majorIncidentSource1 = new MajorIncidentSource();
        majorIncidentSource1.setId(1L);
        MajorIncidentSource majorIncidentSource2 = new MajorIncidentSource();
        majorIncidentSource2.setId(majorIncidentSource1.getId());
        assertThat(majorIncidentSource1).isEqualTo(majorIncidentSource2);
        majorIncidentSource2.setId(2L);
        assertThat(majorIncidentSource1).isNotEqualTo(majorIncidentSource2);
        majorIncidentSource1.setId(null);
        assertThat(majorIncidentSource1).isNotEqualTo(majorIncidentSource2);
    }
}
