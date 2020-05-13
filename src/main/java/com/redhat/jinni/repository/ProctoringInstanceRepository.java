package com.redhat.jinni.repository;

import com.redhat.jinni.domain.ProctoringInstance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProctoringInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProctoringInstanceRepository extends JpaRepository<ProctoringInstance, Long> {
}
