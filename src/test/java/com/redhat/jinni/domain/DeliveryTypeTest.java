package com.redhat.jinni.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.jinni.web.rest.TestUtil;

public class DeliveryTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryType.class);
        DeliveryType deliveryType1 = new DeliveryType();
        deliveryType1.setId(1L);
        DeliveryType deliveryType2 = new DeliveryType();
        deliveryType2.setId(deliveryType1.getId());
        assertThat(deliveryType1).isEqualTo(deliveryType2);
        deliveryType2.setId(2L);
        assertThat(deliveryType1).isNotEqualTo(deliveryType2);
        deliveryType1.setId(null);
        assertThat(deliveryType1).isNotEqualTo(deliveryType2);
    }
}
