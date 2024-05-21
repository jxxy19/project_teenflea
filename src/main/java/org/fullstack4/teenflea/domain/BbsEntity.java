package org.fullstack4.teenflea.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="flea_board")
public class BbsEntity extends BaseEntity
{
    @Id // 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)

    private int bbsIdx;
    @Column(length=20,nullable=false)
    private String userId;
    @Column(length=100,nullable=false)
    private String title;
    @Column(length=2000,nullable=false)
    private String content;
    @Column(length=10,nullable=true)
    private String displayDate;
    @Column(length=10,nullable = true) // 조회수 미정
    private int readCnt;
    @Column(length =5 ,nullable=false) //카테고리 1
    private String category1;
    @Column(length =5 ,nullable=true)  // 카테고리 2
    private String category2;
    @Column(length =100 ,nullable=false)
    private String img;

    public void modify(String title,String content,String display_date,int read_cnt,String category1,String category2,String img){

        this.title=title;
        this.content=content;
        this.displayDate=display_date;
        this.readCnt=read_cnt;
        this.category1=category1;
        this.category2=category2;
        this.img=img;
        super.setModify_date(LocalDateTime.now());
    }


}
