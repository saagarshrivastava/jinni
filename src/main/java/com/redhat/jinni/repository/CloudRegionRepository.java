package com.redhat.jinni.repository;

import com.redhat.jinni.domain.CloudRegion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CloudRegion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CloudRegionRepository extends JpaRepository<CloudRegion, Long> {
}
