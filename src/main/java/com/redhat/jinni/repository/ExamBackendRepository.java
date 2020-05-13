package com.redhat.jinni.repository;

import com.redhat.jinni.domain.ExamBackend;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExamBackend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamBackendRepository extends JpaRepository<ExamBackend, Long> {
}
