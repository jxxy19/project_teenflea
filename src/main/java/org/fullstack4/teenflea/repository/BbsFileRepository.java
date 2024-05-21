package org.fullstack4.teenflea.repository;

import org.fullstack4.teenflea.domain.BbsEntity;
import org.fullstack4.teenflea.domain.BbsFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BbsFileRepository extends JpaRepository<BbsFileEntity, Integer> {

    List<BbsFileEntity> findAllByBbsIdx(int bbsIdx);
}
