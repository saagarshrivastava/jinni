package com.redhat.jinni.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.jinni.web.rest.TestUtil;

public class CategoryInstanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryInstance.class);
        CategoryInstance categoryInstance1 = new CategoryInstance();
        categoryInstance1.setId(1L);
        CategoryInstance categoryInstance2 = new CategoryInstance();
        categoryInstance2.setId(categoryInstance1.getId());
        assertThat(categoryInstance1).isEqualTo(categoryInstance2);
        categoryInstance2.setId(2L);
        assertThat(categoryInstance1).isNotEqualTo(categoryInstance2);
        categoryInstance1.setId(null);
        assertThat(categoryInstance1).isNotEqualTo(categoryInstance2);
    }
}
