package org.fullstack4.teenflea.repository;

import org.fullstack4.teenflea.domain.BbsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BbsRepository extends JpaRepository<BbsEntity, Integer> {
    Page<BbsEntity> findAllByCategory1OrderByBbsIdxDesc(Pageable pageable, String category1);
    Page<BbsEntity> findAllByCategory1AndTitleContainsOrContentContainsAndCategory1ContainsOrUserIdContainsAndCategory1ContainsOrderByBbsIdxDesc(
            Pageable pageable,String category1, String title, String content,String category2, String userId, String category3);
    Page<BbsEntity> findAllByCategory2ContainsAndLocationContainsAndTitleContains(Pageable pageable,String category2, String Location,String title);

    int countByCategory2(String category2);
}
