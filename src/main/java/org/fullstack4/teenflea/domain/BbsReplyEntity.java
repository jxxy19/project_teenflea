package org.fullstack4.teenflea.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="flea_bbs_reply")
public class BbsReplyEntity extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private int replyIdx;
    @Column(nullable=false)
    private int bbsIdx;
    @Column(length=20,nullable=false)
    private String userId;
    @Column(length=2000,nullable=false)
    private String content;

    public void modify(String content){
        this.content=content;
        super.setModify_date(LocalDateTime.now());
    }
}
