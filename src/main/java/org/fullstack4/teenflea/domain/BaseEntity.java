package org.fullstack4.teenflea.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass // 공통으로 사용되는 칼럼을 지정, 해당 클래스를 상속하여 사용
@EntityListeners(value={AuditingEntityListener.class})
abstract class BaseEntity { //VO를 대체하고 table명하고 동일 (테이블 기본 명세)

    @CreatedDate
    @Column(name="reg_date", updatable = false)
    private LocalDateTime regDate;
    @LastModifiedDate
    @Column(name="modify_date",nullable = true,insertable = false,updatable = true)
    private LocalDateTime modifyDate;

    public void setModify_date(LocalDateTime modify_date){
        this.modifyDate=LocalDateTime.now();
    }


}
