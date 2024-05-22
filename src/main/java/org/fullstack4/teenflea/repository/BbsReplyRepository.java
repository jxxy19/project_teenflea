package org.fullstack4.teenflea.repository;

import org.fullstack4.teenflea.domain.BbsEntity;
import org.fullstack4.teenflea.domain.BbsReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BbsReplyRepository extends JpaRepository<BbsReplyEntity, Integer> {
    List<BbsReplyEntity> findAllByBbsIdxOrderByReplyIdxDesc(int bbsIdx);
}
