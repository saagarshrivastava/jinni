package com.redhat.jinni.repository;

import com.redhat.jinni.domain.FailureStage;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FailureStage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FailureStageRepository extends JpaRepository<FailureStage, Long> {
}
