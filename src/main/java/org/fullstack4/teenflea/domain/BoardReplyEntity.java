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
@Table(name="flea_board_reply")
public class BoardReplyEntity
{
    @Id // 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)

    private int replyIdx;
    @Column(nullable=false)
    private int bbsIdx;
    @Column(length=20,nullable=false)
    private String userId;
    @Column(length=100,nullable=false)
    private String title;
    @Column(length=2000,nullable=false)
    private String content;
    @Column(length=10,nullable=true)
    private String displayDate;

    @CreatedDate
    @Column(name="reg_date",updatable = false,columnDefinition = "DATETIME NULL DEFAULT NOW()")
    private LocalDateTime regDate;
    @LastModifiedDate
    @Column(name="modify_date",insertable = false ,updatable = false,nullable = true)
    private LocalDateTime modifyDate;

    public void modify(String title,String content,String displayDate){

        this.title=title;
        this.displayDate =displayDate;
        this.content=content;

    }


}
