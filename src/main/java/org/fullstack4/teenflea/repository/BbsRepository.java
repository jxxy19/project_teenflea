package org.fullstack4.teenflea.repository;

import org.fullstack4.teenflea.domain.BbsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BbsRepository extends JpaRepository<BbsEntity, Integer> {
}
