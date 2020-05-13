package com.redhat.jinni.repository;

import com.redhat.jinni.domain.SubcategoryInstance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SubcategoryInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubcategoryInstanceRepository extends JpaRepository<SubcategoryInstance, Long> {
}
