package com.redhat.jinni.repository;

import com.redhat.jinni.domain.SupportPerson;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SupportPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupportPersonRepository extends JpaRepository<SupportPerson, Long> {
}
