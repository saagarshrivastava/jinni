package com.redhat.jinni.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.jinni.web.rest.TestUtil;

public class DeliveryStatusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryStatus.class);
        DeliveryStatus deliveryStatus1 = new DeliveryStatus();
        deliveryStatus1.setId(1L);
        DeliveryStatus deliveryStatus2 = new DeliveryStatus();
        deliveryStatus2.setId(deliveryStatus1.getId());
        assertThat(deliveryStatus1).isEqualTo(deliveryStatus2);
        deliveryStatus2.setId(2L);
        assertThat(deliveryStatus1).isNotEqualTo(deliveryStatus2);
        deliveryStatus1.setId(null);
        assertThat(deliveryStatus1).isNotEqualTo(deliveryStatus2);
    }
}
