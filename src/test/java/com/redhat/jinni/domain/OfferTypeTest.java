package com.redhat.jinni.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.jinni.web.rest.TestUtil;

public class OfferTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfferType.class);
        OfferType offerType1 = new OfferType();
        offerType1.setId(1L);
        OfferType offerType2 = new OfferType();
        offerType2.setId(offerType1.getId());
        assertThat(offerType1).isEqualTo(offerType2);
        offerType2.setId(2L);
        assertThat(offerType1).isNotEqualTo(offerType2);
        offerType1.setId(null);
        assertThat(offerType1).isNotEqualTo(offerType2);
    }
}
