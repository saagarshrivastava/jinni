package com.redhat.jinni.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.jinni.web.rest.TestUtil;

public class SubcategoryInstanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubcategoryInstance.class);
        SubcategoryInstance subcategoryInstance1 = new SubcategoryInstance();
        subcategoryInstance1.setId(1L);
        SubcategoryInstance subcategoryInstance2 = new SubcategoryInstance();
        subcategoryInstance2.setId(subcategoryInstance1.getId());
        assertThat(subcategoryInstance1).isEqualTo(subcategoryInstance2);
        subcategoryInstance2.setId(2L);
        assertThat(subcategoryInstance1).isNotEqualTo(subcategoryInstance2);
        subcategoryInstance1.setId(null);
        assertThat(subcategoryInstance1).isNotEqualTo(subcategoryInstance2);
    }
}
