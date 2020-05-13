package com.redhat.jinni.repository;

import com.redhat.jinni.domain.CategoryInstance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CategoryInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryInstanceRepository extends JpaRepository<CategoryInstance, Long> {
}
