package com.redhat.jinni.repository;

import com.redhat.jinni.domain.MajorIncidentSource;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MajorIncidentSource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MajorIncidentSourceRepository extends JpaRepository<MajorIncidentSource, Long> {
}
