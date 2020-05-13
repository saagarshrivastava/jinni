package com.redhat.jinni.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.jinni.web.rest.TestUtil;

public class SessionBreaksTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SessionBreaks.class);
        SessionBreaks sessionBreaks1 = new SessionBreaks();
        sessionBreaks1.setId(1L);
        SessionBreaks sessionBreaks2 = new SessionBreaks();
        sessionBreaks2.setId(sessionBreaks1.getId());
        assertThat(sessionBreaks1).isEqualTo(sessionBreaks2);
        sessionBreaks2.setId(2L);
        assertThat(sessionBreaks1).isNotEqualTo(sessionBreaks2);
        sessionBreaks1.setId(null);
        assertThat(sessionBreaks1).isNotEqualTo(sessionBreaks2);
    }
}
