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
@Table(name="flea_bbs")
public class BbsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private int bbsIdx;
    @Column(length=20,nullable=false)
    private String userId;
    @Column(length=100,nullable=false)
    private String title;
    @Column(nullable=false, columnDefinition="LONGTEXT")
    private String content;
    @Column(length=10,nullable = true)
    private int readCnt;
    @Column(length =5 ,nullable=false)
    private String category1;
    @Column(length =5 ,nullable=true)
    private String category2;

    public void modify(String title,String content,String category2){
        this.title=title;
        this.content=content;
        this.category2=category2;
        super.setModify_date(LocalDateTime.now());
    }

}
