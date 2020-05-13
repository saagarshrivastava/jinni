package com.redhat.jinni.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.jinni.web.rest.TestUtil;

public class SupportPersonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupportPerson.class);
        SupportPerson supportPerson1 = new SupportPerson();
        supportPerson1.setId(1L);
        SupportPerson supportPerson2 = new SupportPerson();
        supportPerson2.setId(supportPerson1.getId());
        assertThat(supportPerson1).isEqualTo(supportPerson2);
        supportPerson2.setId(2L);
        assertThat(supportPerson1).isNotEqualTo(supportPerson2);
        supportPerson1.setId(null);
        assertThat(supportPerson1).isNotEqualTo(supportPerson2);
    }
}
