package com.github.whitepin.server.api.repository;

import com.github.whitepin.server.api.entity.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<PartnerEntity, Long> {
}
